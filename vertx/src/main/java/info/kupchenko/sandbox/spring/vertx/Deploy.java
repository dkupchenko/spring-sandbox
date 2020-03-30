package info.kupchenko.sandbox.spring.vertx;

import info.kupchenko.sandbox.spring.vertx.client.LogUsdClient;
import info.kupchenko.sandbox.spring.vertx.repository.UsdRatesRepository;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;

/**
 * Класс Deploy отвечает за deploy вертикалей после обновления контекста Spring'а
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
@Service
@SuppressWarnings("unused")
public class Deploy implements Lifecycle {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(Deploy.class);

    /**
     * for LifeCycle implementation
     */
    private boolean running = false;

    /**
     * Vert.x instance
     */
    private final Vertx vertx;

    /**
     * LogUsdClient instance
     */
    private final LogUsdClient clientVerticle;

    /**
     * UsdRatesRepository instance
     */
    private final UsdRatesRepository repositoryVerticle;

    /**
     * Инжекция зависимостей в конструкторе Spring'ом
     *  @param vertx бин Vertx
     * @param clientVerticle бин клиента
     * @param repositoryVerticle бин продьюсера котировок
     */
    public Deploy(Vertx vertx, LogUsdClient clientVerticle, UsdRatesRepository repositoryVerticle) {
        this.vertx = vertx;
        this.clientVerticle = clientVerticle;
        this.repositoryVerticle = repositoryVerticle;
    }

    @Override
    public void start() {
        if(running) return;
        running = true;
        // деплой всех вертикалей
        log.debug("Before deploying verticles");
        vertx.deployVerticle(clientVerticle);
        vertx.deployVerticle(repositoryVerticle);
        log.debug("After deploying verticles");
    }

    @Override
    public void stop() {
        if(!running) return;
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
