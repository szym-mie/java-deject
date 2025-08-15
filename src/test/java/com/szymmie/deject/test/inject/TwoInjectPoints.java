package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.Inject;

public class TwoInjectPoints {
    @Inject
    public TwoInjectPoints() {}

    @Inject
    public TwoInjectPoints(Object o) {}
}
