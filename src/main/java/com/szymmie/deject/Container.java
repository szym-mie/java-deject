package com.szymmie.deject;

import com.szymmie.deject.retrospect.BindPoint;
import com.szymmie.deject.retrospect.Provider;

import java.util.Optional;

public interface Container {
    Optional<Provider> provide(BindPoint bindPoint);
    <S> S create(Class<? super S> objectClass);
}
