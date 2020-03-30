package info.kupchenko.sandbox.spring.vertx.entities;

/**
 * Перечисление CurrencyType содержит список обслуживаемых валют
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 30.03.2020
 */
public enum Currency {
    /**
     * enum field USD
     */
    USD(840,"USD","US Dollar","Доллар США");

    /**
     * private field цифоровой код ИСО для валюты
     */
    private final int isoCode;
    /**
     * private field буквенный код ИСО для валюты
     */
    private final String isoName;
    /**
     * private field название валюты (английский)
     */
    private final String engName;
    /**
     * private field название валюты (русский)
     */
    private final String ruName;

    /**
     * Приватный конструктор валюты
     *
     * @param isoCode цифоровой код ИСО для валюты
     * @param isoName буквенный код ИСО для валюты
     * @param engName название валюты (английский)
     * @param ruName  название валюты (русский)
     */
    Currency(int isoCode, String isoName, String engName, String ruName) {
        this.isoCode = isoCode;
        this.isoName = isoName;
        this.engName = engName;
        this.ruName = ruName;
    }

    /**
     * возвращает цифоровой код ИСО для валюты
     *
     * @return цифоровой код ИСО для валюты
     */
    public int getIsoCode() {
        return isoCode;
    }

    /**
     * возвращает буквенный код ИСО для валюты
     *
     * @return буквенный код ИСО для валюты
     */
    public String getIsoName() {
        return isoName;
    }

    /**
     * возвращает название валюты (английский)
     *
     * @return название валюты (английский)
     */
    public String getEngName() {
        return engName;
    }

    /**
     * возвращает название валюты (русский)
     *
     * @return название валюты (русский)
     */
    public String getRuName() {
        return ruName;
    }

    /**
     * возвращает строковое представление экземпляра класса
     * <b>ВАЖНО: формат строкового представления может быть изменён в следующих релизах</b>
     *
     * @return строковое представление экземпляра класса в виде "%s{%d: %s(%s)}"
     */
    @Override
    public String toString() {
        return String.format("%s{%d: %s(%s)}", isoName, isoCode, engName, ruName);
    }
}