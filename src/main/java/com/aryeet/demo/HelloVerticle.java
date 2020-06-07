package com.aryeet.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

import java.util.List;

public class HelloVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("hello.vertx.addr", this::msgHandler);
        vertx.eventBus().consumer("hello.named.addr", this::msgNamedHandler);
      //  vertx.eventBus().consumer("hello.namedloc.addr", this::msgNamedLocHandler);
    }

    private <T> void msgHandler(Message<T> tMessage) {
        tMessage.reply("hello Vertex application ");
    }

    private <T> void msgNamedHandler(Message<T> tMessage) {
        String name = (String) tMessage.body();
        tMessage.reply(String.format("Hello %s! ", name));

    }

   /* private <T> void msgNamedLocHandler(Message<T> tMessage) {
        List<String> name = (List<String>) tMessage.body();

        String myName = name.get(0);
        String myLoc = name.get(1);

        tMessage.reply(String.format("Hello %s! and loc %s ", myName, myLoc));

    }*/

}
