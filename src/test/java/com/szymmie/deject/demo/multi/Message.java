package com.szymmie.deject.demo.multi;

public class Message implements Comparable<Message> {
    private final String channel;
    private final String subject;
    private final Priority priority;

    public Message(String channel, String subject, Priority priority) {
        this.channel = channel;
        this.subject = subject;
        this.priority = priority;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getSubject() {
        return this.subject;
    }

    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(Message o) {
        return o.getPriority().ordinal() - this.getPriority().ordinal();
    }
}
