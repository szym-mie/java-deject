package com.szymmie.deject.retrospect;

public class BindException extends RuntimeException {
    public BindException(BindPoint bindPoint, String reason) {
        super(String.format("%s -> %s", bindPoint.toString(), reason));
    }
}
