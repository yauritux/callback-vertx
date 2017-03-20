package app;

import app.verticles.*;
import io.vertx.core.Vertx;

public class VertxStarter {

    public static void main(String... args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Service1());
        vertx.deployVerticle(new Service2());
        vertx.deployVerticle(new Service3());
        vertx.deployVerticle(new CallbacksWebVerticle());
    }
}