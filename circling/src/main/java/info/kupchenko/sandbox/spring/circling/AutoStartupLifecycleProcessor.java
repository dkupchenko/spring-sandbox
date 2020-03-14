package info.kupchenko.sandbox.spring.circling;

import org.springframework.context.support.DefaultLifecycleProcessor;

/**
 * The AutoStartLifecycleProcessor extends DefaultLifecycleProcessor
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 14.03.2020
 * Last review on 14.03.2020
 */
public class AutoStartupLifecycleProcessor extends DefaultLifecycleProcessor {
    @Override
    public void onRefresh() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
        start();
    }

    @Override
    public String toString() {
        return "AutoStartupLifecycleProcessor";
    }

    class ShutdownHook implements Runnable {
        @Override
        public void run() {
            onClose();
        }
    }
}
