package info.kupchenko.sandbox.spring.circling.family;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    ThreadPoolTaskExecutor executor;
    boolean running;

    SmithFamily(Husband husband, Wife wife, TaskExecutor executor) {
        running = false;
        if(executor instanceof ThreadPoolTaskExecutor) {
            this.executor = (ThreadPoolTaskExecutor) executor;
        }
        husband.setWife(wife);
        wife.setHusband(husband);
    }

    @Override
    public void start() {
        running = true;
        if(executor != null && executor.getThreadPoolExecutor().isShutdown()) {
            executor.initialize();
        }
        System.out.println(String.format("[T-%d] %s IS STARTED",
                Thread.currentThread().getId(), this.getClass().getSimpleName()));
    }

    @Override
    public void stop() {
        running = false;
        if(executor != null && !executor.getThreadPoolExecutor().isShutdown()) {
            executor.shutdown();
        }
        System.out.println(String.format("[T-%d] %s IS STOPPED",
                Thread.currentThread().getId(), this.getClass().getSimpleName()));
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return String.format("%s{running=%s}", this.getClass().getSimpleName(), running);
    }
}
