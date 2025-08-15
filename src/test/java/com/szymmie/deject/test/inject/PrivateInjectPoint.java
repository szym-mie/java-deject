package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.Inject;

public class PrivateInjectPoint {
    @Inject
    private PrivateInjectPoint(Object o) {}
}
