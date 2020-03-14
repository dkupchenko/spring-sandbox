# Circling - проблема циклической зависимости

Задача возникла при просмотре видео 
[Евгений Борисов — Spring – Глубоко и не очень](https://youtu.be/nGfeSo52_8A). Данный проект - это
некий playground для того, чтобы руками пощупать, что/как/где может(не может) использоваться. 

## Stage 1. Spring DI без циклических зависимостей

Бин `Husband` с двумя зависимыми бинами `Pet` и `Car`. Рассмотрены DI через конструктор и через аннотацию `@Autowired`.
Закомментирован вызов `Husband` -> `Car` в конструкторе, приводящий к `NullPointerException`. 

## Stage 2. Spring DI с циклическими зависимостями

### Stage 2.1 DI в конструкторах

Если инжектить циклически зависимые бины через конструктор, то получаем `UnsatisfiedDependencyException`. 
Это вполне ожидаемое поведение.

### Stage 2.2 @Autowired

DI циклически зависимых бинов через `@Autowired`. Бины выполняют только "быстрые" взаимодействия, так как
вызовы "медленных" методов будут выполняться в общем потоке Spring'а и тормозить старт приложения. 

### Stage 2.3 Вызов медленных сервисных методов

"Тяжёлую" бизнес-логику сервисов имеет смысл использовать при получении `ContextStartedEvent`. Событие генерируется при
вызове `context.start()` в `main()`. Проблема в том, что всё это работает в одном потоке, и пока бизнес-логика по
`ContextStartedEvent` не завершит свою работу, код в `main()` не продолжит выполняться. 

## Stage 3. Spring Integration

### Stage 3.1 Пробуем @Async
 
Добавляем:
* Spring Integration и `@Async` в наш проект
* новый неиспользуемый `@Async`-метод в бине `John`

При запуске приложения получаем `BeanCurrentlyInCreationException`. Это происходит в методе 
`doCreateBean` класса `AbstractAutowireCapableBeanFactory` на этапе пост-обработки бина `John`
после всех `BeanPostProcessor`'ов (проверьте дебаггером начиная с последней инструкции 
`John.onPostConstruct()`).     

### Stage 3.2 Избавляемся от @Autowired в циклических зависимостях

Пробуем реализовать workaround для `@Autowired` в циклических зависимостях. Дополнительная сущность `Family` будет
инжектить их самостоятельно используя DI в конструкторе. При запуске появляется еще одно исключение о том, что 
медоды с аннотацией `@EventListener` класса `StatedBean` не могут быть обёрнуты в прокси у наследников. К счастью,
детальную работу этих методов показали прошлые этапы, поэтому они были исключены из дальнейших реализаций. Пробуем
вызывать "медленную" бизнесс-логику из `Family`. Всё работает. Наконец-то можно переходить к заключительным этапам. 

### Stage 3.3 @Async is working !!!

Все медленные методы помечены `@Async`. Управление worker-сервисами отдано на откуп Spring'у имплементацией интерфейса
 `Lifecycle` у всех `Essence`. В этом случае не нужно слушать event'ы `ContextStartedEvent` & `ContextStoppedEvent`, 
 нужно уметь реагировать на команды `Lifecycle`.
 
## Stage 4. Mixed mode Spring configuration

Смешанный вариант конфигурации контекста. [spring-context.xml](src/main/resources/spring-context.xml) ссылается на 
конфигурацию через аннотации [Config.java](src/main/java/info/kupchenko/sandbox/spring/circling/Config.java).

## Stage 5. Конфигурируемый автостарт и SmartLifecycle 

Далеко не во всех кейсах можно позволить себе вызовы `context.start()` и `context.stop()` из `main()`. Данный этап
посвещён добавлению опции автостарта для worker-бинов. Реализация автостарта возложена на нативные реализации 
Spring'а.

Бин [SmithFamily](src/main/java/info/kupchenko/sandbox/spring/circling/family/SmithFamily.java) имплементирует
интерфейс [SmartLifecycle](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/context/SmartLifecycle.java)
и с помощью [SmartLifecycle.isAutoStartup()](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/SmartLifecycle.html#isAutoStartup--)
позволяет указать фреймворку, что данный бин должен запускаться сразу после создания контекста.   

В то же время [DefaultLifecycleProcessor](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/support/DefaultLifecycleProcessor.html),
пробегая по всем бинам с интерфейсами `SmartLifecycle` при `onRefresh()` в поисках бинов с `isAutoStartup() == true`, 
стартует не только данные бины, но и все бины, от которых они зависят. Можно просто просмотреть цепочку вызовов
`onRefresh() -> startBeans(true) -> doStart()`, чтобы убедиться в этом. Более того, данное поведение - это часть
контракта класса `DefaultLifecycleProcessor`

```
...
/**
 * Default implementation of the {@link LifecycleProcessor} strategy.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0
 */
public class DefaultLifecycleProcessor implements LifecycleProcessor, BeanFactoryAware {
...
/**
 * Start the specified bean as part of the given set of Lifecycle beans,
 * making sure that any beans that it depends on are started first.
 * @param lifecycleBeans Map with bean name as key and Lifecycle instance as value
 * @param beanName the name of the bean to start
 */
private void doStart(Map<String, ? extends Lifecycle> lifecycleBeans, String beanName) {
...
}
```

Таким образом, бин `SmithFamily`, зависящий от бинов `John` и `Mary`, при автостарте косвенно, посредством фреймворка,
будет "виновен" в автостарте бинов `John` и `Mary`. Если бы в проекте были еще worker-бины без циклических зависимостей, 
для их автостарта можно было бы рассмотреть следующие вариатны:
* имплементация интерфейса SmartLifecycle
* создание зависимости от данного бина у другого бина, реализующего интерфейс SmartLifecycle
* изменения поведения LifecycleProcessor

`SmartLifecycle` и `DefaultLifecycleProcessor` существуют как минимум с третьей версии Spring (09.12.2009) в описанной
выше реализации. Можете убедиться, пробежавшись по коммитам Spring'а: 
```
git clone https://github.com/spring-projects/spring-framework.git
git show dc1b500430
git checkout dc1b500430
cat spring-framework/org.springframework.context/src/main/java/org/springframework/context/support/DefaultLifecycleProcessor.java
cat spring-framework/org.springframework.context/src/main/java/org/springframework/context/SmartLifecycle.java
```

## Stage 6. Свой LifecycleProcessor

Если задача стоит именно в автостарте всех бинов, реализующих интерфейс `Lifecycle`, но нет необходимости 
перегружать код имплементацией `SmartLifecycle`, то более оптимальным решением будет собственная реализация 
`LifecycleProcessor`, который в отличие от `DefaultLifecycleProcessor` будет стартовать все бины с интерфейсом
`Lifecycle`.

В данной реализации бин `SmithFamily` ответственен за:
* инжекцию циклической зависимости между `Husband` и `Wife`
* init/shutdown `ThreadPoolTaskExecutor`

Бин `AutoStartupLifecycleProcessor` является наследником `DefaultLifecycleProcessor` и корректирует его поведение
для автостарта. Кроме того, теперь он ответственен за хук в JVM для реагрирования на закртытие приложения. Бин должен  
называться `lifecycleProcessor`, чтобы `AbstractApplicationContext` использовал именно его.
