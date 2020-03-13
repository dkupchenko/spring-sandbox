package info.kupchenko.sandbox.spring.circling.family;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
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
    @Value("${family.auto-startup}")
    boolean autoStartup = false;
    boolean running = false;
    Husband husband;
    Wife wife;
    TaskExecutor executor;
    Thread familyThread;

    SmithFamily(Husband husband, Wife wife, TaskExecutor executor) {
        husband.setWife(wife);
        wife.setHusband(husband);
        this.executor = executor;
        this.husband = husband;
        this.wife = wife;
    }

    @Override
    public void start() {
        familyThread = Thread.currentThread();
        System.out.println(String.format("[T-%d] %s IS STARTED", Thread.currentThread().getId(), "SmithFamily"));
        running = true;
        if(autoStartup)
            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
    }

    @Override
    public void stop() {
        System.out.println(String.format("[T-%d] %s IS STOPPED", Thread.currentThread().getId(), "SmithFamily"));
        running = false;
        if(executor instanceof ExecutorConfigurationSupport)
            ((ExecutorConfigurationSupport) executor).shutdown();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return autoStartup;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    class ShutdownHook implements Runnable {
        @Override
        public void run() {
            husband.stop();
            wife.stop();
            stop();
        }
    }

    @Override
    public String toString() {
        return String.format("SmithFamily with husband '%s' and wife %s", husband, wife);
    }
}
