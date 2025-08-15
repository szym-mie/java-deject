package com.szymmie.deject.retrospect;

import com.szymmie.deject.Module;
import com.szymmie.deject.annotations.AsNamed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Provider {
    private final Module module;
    private final Method method;
    private final BindPoint bindPoint;

    public Provider(Module module, Method method) {
        this.module = module;
        this.method = method;
        Class<?> type = method.getReturnType();
        AsNamed asNamed = method.getAnnotation(AsNamed.class);
        String name = asNamed != null ? asNamed.value() : null;
        this.bindPoint = new BindPoint(type, name, BindSource.PROVIDER);
    }

    public Object create() {
        Class<?> type = this.bindPoint.getType();
        try {
            return type.cast(this.method.invoke(this.module));
        } catch (IllegalAccessException e) {
            throw new BindException(this.bindPoint, "provider or module is not public");
        } catch (InvocationTargetException e) {
            throw new BindException(this.bindPoint, "provider methods cannot have parameters");
        } catch (ClassCastException e) {
            throw new BindException(this.bindPoint, "provided produces incompatible type");
        }
    }

    public Class<?> getType() {
        return this.bindPoint.getType();
    }

    public String getName() {
        return this.bindPoint.getName();
    }

    @Override
    public String toString() {
        String moduleName = Types.getTypeName(this.module.getClass());
        return String.format("Provider(Module %s, %s)", moduleName, this.bindPoint);
    }
}
