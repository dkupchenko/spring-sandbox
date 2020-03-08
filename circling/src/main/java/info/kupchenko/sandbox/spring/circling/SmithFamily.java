package info.kupchenko.sandbox.spring.circling;

import org.springframework.stereotype.Component;

/**
 * The SmithFamily ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
@Component
@SuppressWarnings("unused")
public class SmithFamily implements Family {
    SmithFamily(Husband husband, Wife wife) {
        super();
        husband.setWife(wife);
        wife.setHusband(husband);
    }
}
