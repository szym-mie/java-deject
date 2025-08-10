package com.szymmie.deject.demo.multi;

import com.szymmie.deject.annotations.ByInject;
import com.szymmie.deject.annotations.ByType;
import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.demo.multi.flow.Flow;
import com.szymmie.deject.demo.multi.log.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Exchange {
    private final Storage storage;
    private final Map<String, Flow<Message>> messageFlowMap;
    private final Logger logger;

    @Inject
    public Exchange(@ByInject Storage storage, @ByType Logger logger) {
        this.storage = storage;
        this.messageFlowMap = new HashMap<>();
        this.logger = logger;
    }

    public void push(Message message) {
        this.storage.write(message);
        String channel = message.getChannel();
        Flow<Message> flow = this.messageFlowMap.get(channel);
        if (flow != null) {
            this.logger.log(Exchange.class, "broadcast to flow " + channel);
            this.unicast(channel, flow);
        }
    }

    public void flush() {
        this.storage.flush();
    }

    public void broadcast() {
        this.messageFlowMap.forEach(this::unicast);
    }

    public void unicast(String channel, Flow<Message> flow) {
        for (int acceptedCount = 0;; acceptedCount++) {
            this.logger.log(Exchange.class, "unicast at #" + channel);
            if (flow.isFull()) {
                this.logger.log(Exchange.class, "flow is full, accepted " + acceptedCount);
                break;
            }
            Optional<Message> message = this.storage.pop(channel);
            if (!message.isPresent()) {
                this.logger.log(Exchange.class, "channel box empty, accepted " + acceptedCount);
                break;
            }

            flow.accept(message.get());
        }
    }

    public void attach(String channel, Flow<Message> flow) {
        this.messageFlowMap.put(channel, flow);
    }

    public void detach(String channel) {
        this.messageFlowMap.remove(channel);
    }
}
