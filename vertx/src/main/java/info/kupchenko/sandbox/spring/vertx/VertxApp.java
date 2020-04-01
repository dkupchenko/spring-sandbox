package info.kupchenko.sandbox.spring.vertx;

import info.kupchenko.sandbox.spring.vertx.repository.RubRatesRepository;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Класс VertxApp является точкой входа в приложение, используя в качестве конфигурации контекста
 * Java-аннотированную конфигурацию
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 30.03.2020
 */
public class VertxApp {

    private static final String VERTX_BEAN = "vertx";
    private static final String RUB_REPO_BEAN = "rubRatesRepository";
    private static final Log log = LogFactory.getLog(VertxApp.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
/*
        Vertx vertx = (Vertx) context.getBean(VERTX_BEAN);
        RubRatesRepository rubRatesRepository = (RubRatesRepository) context.getBean(RUB_REPO_BEAN);
        log.debug(String.format("deploying verticle %s", RUB_REPO_BEAN));
        vertx.deployVerticle(rubRatesRepository, new DeploymentOptions().setWorker(true), result -> {
            if(result.succeeded()) {
                rubRatesRepository.deploySuccess(result.result());
            } else {
                rubRatesRepository.deployError();
            }
        });
*/
    }
}