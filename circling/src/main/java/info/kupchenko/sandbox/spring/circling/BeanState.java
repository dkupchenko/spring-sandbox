package info.kupchenko.sandbox.spring.circling;

/**
 * The BeanState ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
public enum BeanState {
    BEFORE_CONSTRUCT,
    AFTER_CONSTRUCT,
    AFTER_POST_CONSTRUCT,
    AFTER_CONTEXT_REFRESHED,
    AFTER_CONTEXT_STARTED
}
