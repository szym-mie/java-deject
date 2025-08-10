package com.szymmie.deject.demo.multi.log;

public class PlainLogger implements Logger {
    public PlainLogger() {}

    @Override
    public void log(String tag, Object... objects) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(tag);
        buffer.append("]");
        for (Object object : objects) {
            buffer.append(" ");
            buffer.append(object.toString());
        }
        System.out.println(buffer);
    }

    @Override
    public void log(Class<?> type, Object... objects) {
        this.log(type.getCanonicalName(), objects);
    }
}
