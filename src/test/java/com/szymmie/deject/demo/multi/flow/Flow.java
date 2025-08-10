package com.szymmie.deject.demo.multi.flow;

public interface Flow<T> {
    boolean isFull();
    void accept(T item);
}
