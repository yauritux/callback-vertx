package app.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class Service2 extends AbstractVerticle {

    public static final String ADDRESS = "service2";
    private static final Logger LOG = LoggerFactory.getLogger(Service2.class);

    @Override
    public void start() {
        LOG.info("Starting service2...");
        vertx.eventBus().consumer(ADDRESS, message -> {
            LOG.info("Receiving message: " + message.body());
            message.reply("[reply-from-service2]");
        });
    }
}