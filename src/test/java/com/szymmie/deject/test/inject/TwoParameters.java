package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.ByType;
import com.szymmie.deject.annotations.Inject;

public class TwoParameters {
    public final int n;
    public final int m;

    @Inject
    public TwoParameters(@ByType Integer n, @ByType Integer m) {
        this.n = n;
        this.m = m;
    }
}
