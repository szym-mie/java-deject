package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.ByType;
import com.szymmie.deject.annotations.Inject;

public class OneParameter {
    public final int n;

    @Inject
    public OneParameter(@ByType Integer n) {
        this.n = n;
    }
}
