package com.szymmie.deject.demo.multi;

import com.szymmie.deject.annotations.ByName;
import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.demo.multi.log.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Storage {
    private final Queue<Message> messageQueue;
    private final Map<String, Queue<Message>> channelQueueMap;
    private final Logger logger;

    @Inject
    public Storage(@ByName("messageQueueSize") Integer messageQueueSize, Logger logger) {
        this.messageQueue = new ArrayBlockingQueue<>(messageQueueSize);
        this.channelQueueMap = new TreeMap<>();
        this.logger = logger;
    }

    public void write(Message message) {
        if (!this.messageQueue.offer(message)) {
            this.logger.log(Storage.class, "inbound queue full");
            this.flush();
            this.messageQueue.offer(message);
        }
        String channel = message.getChannel();
        this.logger.log(Storage.class, "push message #" + channel);
    }

    public void flush() {
        this.logger.log(Storage.class, "flush queue to channels");
        this.messageQueue.forEach(this::commit);
        this.messageQueue.clear();
    }

    public Optional<Message> pop(String channel) {
        this.logger.log(Storage.class, "try poll #" + channel);
        Queue<Message> channelQueue = this.channelQueueMap.get(channel);
        return Optional.ofNullable(channelQueue).map(Queue::poll);
    }

    private void commit(Message message) {
        String channel = message.getChannel();
        this.logger.log(Storage.class, "save message #" + channel);
        if (!this.channelQueueMap.containsKey(channel)) {
            this.logger.log(Storage.class, "create #" + channel);
            Queue<Message> queue = new PriorityBlockingQueue<>();
            this.channelQueueMap.put(channel, queue);
        }
        this.channelQueueMap.get(channel).offer(message);
    }
}
