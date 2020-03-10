package info.kupchenko.sandbox.spring.circling;

import javax.annotation.PostConstruct;

/**
 * The StatedBean ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
abstract public class StatedBean {
    protected final long DEFAULT_MAX_DELAY = 500L;
    protected BeanState state;

    public StatedBean() {
        state = BeanState.AFTER_CONSTRUCT;
    }

    abstract public void onPostConstruct();

    @PostConstruct
    @SuppressWarnings("unused")
    public void init(){
        state = BeanState.AFTER_POST_CONSTRUCT;
        onPostConstruct();
    }

    @SuppressWarnings("unused")
    public BeanState getState() {
        return state;
    }

    @Override
    public String toString() {
        return String.format("state is %s", state);
    }
}
