package com.szymmie.deject.retrospect;

public class BindPoint {
    private final Class<?> type;
    private final String name;
    private final BindSource source;

    public BindPoint(Class<?> type, String name, BindSource source) {
        this.type = type;
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    public boolean isNamed() {
        return this.name != null;
    }

    public Class<?> getType() {
        return this.type;
    }

    public BindSource getSource() {
        return this.source;
    }

    @Override
    public String toString() {
        String type = Types.getTypeName(this.type);
        switch (this.source) {
            case PROVIDER:
                String maybeName = this.isNamed() ? String.format("'%s'", this.name) : "<anon>";
                return String.format("BindPoint(provide %s & %s)", type, maybeName);
            case INJECTOR:
                return String.format("BindPoint(inject %s)", type);
        }
        return "BindPoint(unknown)";
    }

}
