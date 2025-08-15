package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.ByName;
import com.szymmie.deject.annotations.Inject;

public class GoodByName {
    public final int a;
    public final int b;

    @Inject
    public GoodByName(@ByName("1") Integer a, @ByName("2") Integer b) {
        this.a = a;
        this.b = b;
    }
}
