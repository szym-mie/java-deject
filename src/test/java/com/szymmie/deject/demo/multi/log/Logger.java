package com.szymmie.deject.demo.multi.log;

public interface Logger {
    void log(String tag, Object... objects);
    void log(Class<?> type, Object... objects);
}
