package com.szymmie.deject.demo.multi;

import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.demo.multi.flow.RequestFlow;
import com.szymmie.deject.demo.multi.log.Logger;

public class MessageFlow extends RequestFlow<Message> {
    private final Logger logger;

    @Inject
    public MessageFlow(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void accept(Message item) {
        String priority = item.getPriority() == Priority.HIGH ? "P!" : "P-";
        String channel = "#" + item.getChannel();
        String subject = "| " + item.getSubject() + " |";
        this.logger.log("M", priority, channel, subject);
    }
}
