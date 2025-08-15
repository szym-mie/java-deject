package com.szymmie.deject.test.inject;

import com.szymmie.deject.annotations.ByName;
import com.szymmie.deject.annotations.Inject;

public class BadByName {
    public final int a;
    public final int b;
    public final int c;

    @Inject
    public BadByName(@ByName("1") Integer a, @ByName("2") Integer b, @ByName("3") Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
