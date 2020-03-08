package info.kupchenko.sandbox.spring.circling;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
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
public class SmithFamily extends StatedBean implements Family {
    Husband husband;
    Wife wife;

    SmithFamily(Husband husband, Wife wife) {
        super();
        this.husband = husband;
        this.wife = wife;
        husband.setWife(wife);
        wife.setHusband(husband);
        System.out.println(toString());
    }

    @Override
    public void onPostConstruct() {
        System.out.println(toString());
    }

    @EventListener
    public void handleContextRefresh(ContextStartedEvent event){
        state = BeanState.AFTER_CONTEXT_STARTED;
        System.out.println(toString());
        try {
            husband.rest();
            wife.rest();
        } catch (InterruptedException e) {
            System.out.println("event = " + event);;
        }
    }

    @Override
    public String toString() {
        return String.format("Smith family with [%s] and [%s], %s", wife, husband, super.toString());
    }
}
