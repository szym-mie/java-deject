package com.szymmie.deject.demo.multi.flow;

public abstract class RequestFlow<T> implements Flow<T> {
    private int requestedItemCount;

    public RequestFlow() {
        this.requestedItemCount = 0;
    }

    public  void requestOne() {
        this.requestedItemCount++;
    }

    public void requestMany(int itemCount) {
        this.requestedItemCount += itemCount;
    }

    public void cancel() {
        this.requestedItemCount = 0;
    }

    public boolean isFull() {
        return this.requestedItemCount == 0;
    }
}
