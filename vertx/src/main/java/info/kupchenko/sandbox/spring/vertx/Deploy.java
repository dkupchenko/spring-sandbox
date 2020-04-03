package info.kupchenko.sandbox.spring.vertx;

import info.kupchenko.summer.vertx.annotation.Deployment;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Deploy ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 03.04.2020
 * Last review on 03.04.2020
 */
@Component
@SuppressWarnings("unused")
public class Deploy {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(Deploy.class);

    @Autowired
    Vertx vertx;

    @Deployment(prefix = "test")
    void deploy() {
        log.debug("deployment is started");

        vertx.deployVerticle("test:logClient", result -> {
            if(result.succeeded())
                log.info(String.format("test:logClient is deployed with id [%s]", result.result()));
            else
                log.error("test:logClient isn't deployed");
        });

        vertx.deployVerticle("test:usdRatesRepository", result -> {
            if(result.succeeded())
                log.info(String.format("test:usdRatesRepository is deployed with id [%s]", result.result()));
            else
                log.error("test:usdRatesRepository isn't deployed");
        });

        vertx.deployVerticle("test:workerEurRatesRepository", new DeploymentOptions().setWorker(true), result -> {
            if(result.succeeded())
                log.info(String.format("test:workerEurRatesRepository is deployed with id [%s]", result.result()));
            else
                log.error("test:workerEurRatesRepository isn't deployed");
        });

        vertx.deployVerticle("test:rubRatesRepository", new DeploymentOptions().setInstances(2), result -> {
            if(result.succeeded())
                log.info(String.format("test:rubRatesRepository is deployed with id [%s]", result.result()));
            else
                log.error("test:rubRatesRepository isn't deployed");
        });
    }
}
