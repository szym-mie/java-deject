package com.szymmie.deject;

import com.szymmie.deject.retrospect.BindPoint;
import com.szymmie.deject.retrospect.Injector;
import com.szymmie.deject.retrospect.Provider;
import com.szymmie.deject.solver.TypeNameResolver;

import java.util.Optional;

public class ModuleContainer implements Container {
    private final TypeNameResolver resolver;

    public ModuleContainer(Module module) {
        this.resolver = new TypeNameResolver(module);
    }

    @Override
    public Optional<Provider> provide(BindPoint bindPoint) {
        return this.resolver.resolve(bindPoint);
    }

    @Override
    public <S> S create(Class<? super S> objectClass) {
        Injector<? super S> injector = new Injector<>(this, objectClass);
        //noinspection unchecked
        return (S) injector.create();
    }
}
