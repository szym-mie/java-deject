package com.szymmie.deject.retrospect;

import com.szymmie.deject.Container;
import com.szymmie.deject.annotations.ByInject;
import com.szymmie.deject.annotations.ByName;
import com.szymmie.deject.annotations.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class Injector<S> {
    private final Container container;
    private final Constructor<? extends S> constructor;
    private final List<BindPoint> inputs;

    public Injector(Container container, Class<? extends S> classType) {
        this.container = container;
        this.constructor = Injector.findInjectPoint(classType);
        this.inputs = new LinkedList<>();
        for (Parameter parameter : constructor.getParameters()) {
            Class<?> type = parameter.getType();
            ByName byName = parameter.getAnnotation(ByName.class);
            String name = byName != null ? byName.value() : null;
            ByInject byInject = parameter.getAnnotation(ByInject.class);
            BindSource bindSource = byInject == null ? BindSource.PROVIDER : BindSource.INJECTOR;
            BindPoint bindPoint = new BindPoint(type, name, bindSource);
            this.inputs.add(bindPoint);
        }
    }

    public S create() {
        Object[] args = new Object[this.inputs.size()];
        int i = 0;
        for (BindPoint input : this.inputs) {
            switch (input.getSource()) {
                case INJECTOR:
                    args[i] = this.getInnerInjector(input).create();
                    break;
                case PROVIDER:
                    args[i] = this.container.provide(input)
                            .map(Provider::create)
                            .orElseThrow(() -> new BindException(input, "no provider found"));
                    break;
            }
            i++;
        }
        Class<?> type = this.constructor.getDeclaringClass();
        try {
            //noinspection unchecked
            return (S) type.cast(this.constructor.newInstance(args));
        } catch (IllegalAccessException e) {
            throw new InjectException(type, "constructor is not public");
        } catch (InvocationTargetException e) {
            throw new InjectException(type, "constructor failed with: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new InjectException(type, "constructor produces incompatible type");
        } catch (InstantiationException e) {
            throw new InjectException(type, "abstract class or interface cannot be created");
        }
    }

    private Injector<Object> getInnerInjector(BindPoint bindPoint) {
        return new Injector<>(this.container, bindPoint.getType());
    }

    private static <S> Constructor<S> findInjectPoint(Class<S> type) {
        Constructor<S> injectPoint = null;
        for (Constructor<?> constructor : type.getConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (injectPoint != null)
                    throw new InjectException(type, "multiple inject points");
                //noinspection unchecked
                injectPoint = (Constructor<S>) constructor;
            }
        }
        if (injectPoint == null)
            throw new InjectException(type, "type is not injectable");
        return injectPoint;
    }
}
