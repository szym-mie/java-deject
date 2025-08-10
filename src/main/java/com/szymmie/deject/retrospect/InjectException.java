package com.szymmie.deject.retrospect;

public class InjectException extends RuntimeException {
    public InjectException(Class<?> type, String reason) {
        super(String.format("%s -> %s", Types.getTypeName(type), reason));
    }
}
