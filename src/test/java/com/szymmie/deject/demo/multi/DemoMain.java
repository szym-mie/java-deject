package com.szymmie.deject.demo.multi;

import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;

public class DemoMain {
    public static void main(String[] args) {
        Module module = new DemoModule();
        ModuleContainer container = new ModuleContainer(module);

        // create objects
        Exchange exchange = container.create(Exchange.class);
        MessageFlow homeFlow = container.create(MessageFlow.class);
        MessageFlow javaFlow = container.create(MessageFlow.class);

        // attach flows
        exchange.attach("home", homeFlow);
        exchange.attach("java", javaFlow);

        // eager accept
        homeFlow.requestMany(4);

        // push messages
        // storage empty
        exchange.push(new Message("home", "hello", Priority.LOW));
        exchange.push(new Message("home", "msg #1", Priority.LOW));
        exchange.push(new Message("home", "msg #2", Priority.HIGH));
        exchange.push(new Message("java", "msg #3", Priority.HIGH));
        // storage save 4 messages
        exchange.push(new Message("java", "msg #4", Priority.LOW));
        exchange.push(new Message("home", "msg #5", Priority.LOW));
        // storage save 2 messages
        exchange.flush();

        // late accept
        javaFlow.requestOne();
        javaFlow.requestOne();
        exchange.broadcast();

        // cancel and detach
        homeFlow.cancel();
        javaFlow.cancel();
        exchange.detach("home");
        exchange.detach("java");
    }
}
