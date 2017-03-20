package app.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class Service1 extends AbstractVerticle {

    public static final String ADDRESS = "service1";
    private static final Logger LOG = LoggerFactory.getLogger(Service1.class);

    @Override
    public void start() {
        LOG.info("Starting Service1...");
        vertx.eventBus().consumer(ADDRESS, message -> {
            LOG.info("Receiving message: " + message.body());
            message.reply("[reply-from-service1]");
        });
    }
}