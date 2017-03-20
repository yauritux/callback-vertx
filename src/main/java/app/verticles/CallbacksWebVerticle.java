package app.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class CallbacksWebVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(CallbacksWebVerticle.class);

    @Override
    public void start() {
        LOG.info("Starting CallbacksWebVerticle...");
        vertx.createHttpServer()
            .requestHandler(new RequestHandler())
            .listen(9000);
    }

    private class RequestHandler implements Handler<HttpServerRequest> {

        @Override
        public void handle(HttpServerRequest request) {
            // send a message to service1
            vertx.eventBus().send(Service1.ADDRESS, "message-to-service1", reply1 -> {
                String replyFromService1 = (String) reply1.result().body();

                // send a message to service2
                vertx.eventBus().send(Service2.ADDRESS, "message-to-service2", reply2 -> {
                    String replyFromService2 = (String) reply2.result().body();

                    // send the results from both to service3
                    String combineResult = replyFromService1 + "-" + replyFromService2;
                    vertx.eventBus().send(Service3.ADDRESS, combineResult, reply3 -> {

                        // send an http response with the result of service3
                        request.response().end("CallbacksWebVerticle - response from Service3: " + reply3.result().body());
                    });
                });
            });
        }
    }
}