package com.aryeet.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

    public void start() {
        DeploymentOptions opts = new DeploymentOptions()
                .setWorker(true)
                .setInstances(4);

        vertx.deployVerticle("com.aryeet.demo.HelloVerticle", opts);
        //  vertx.deployVerticle(new HelloVerticle(), opts);

        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloNamedVertx);
        //   router.get("/api/v1/hello/:name/:loc").handler(this::helloNamedlocVertx);


        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(9111, "127.0.0.1");

    }

   /* private void helloNamedlocVertx(RoutingContext ctx) {
        String name = ctx.request().getParam("name");
        String loc = ctx.request().getParam("loc");

        List eventsMsgs = new ArrayList<String>();
        eventsMsgs.add(name);
        eventsMsgs.add(loc);

        vertx.eventBus().request("hello.namedloc.addr", eventsMsgs, reply -> {
            ctx.request().response().end((String) reply.result().body());
        });

    }*/

    private void helloNamedVertx(RoutingContext ctx) {

        String name = ctx.request().getParam("name");
        vertx.eventBus().request("hello.named.addr", name, reply -> {
            ctx.request().response().end((String) reply.result().body());
        });
    }

    private void helloVertx(RoutingContext ctx) {
        vertx.eventBus().request("hello.vertx.addr", "", reply -> {
            ctx.request().response().end((String) reply.result().body());
        });
    }

}



