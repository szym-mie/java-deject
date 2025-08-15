package com.szymmie.deject.test.retrospect;

import com.szymmie.deject.Container;
import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;
import com.szymmie.deject.retrospect.InjectException;
import com.szymmie.deject.retrospect.Injector;
import com.szymmie.deject.test.inject.AbstractInjectPoint;
import com.szymmie.deject.test.inject.PrivateInjectPoint;
import com.szymmie.deject.test.inject.TwoInjectPoints;
import com.szymmie.deject.test.inject.NoInjectPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestInjector {
    private Container container;

    @Before
    public void before() {
        Module module = new Module() {};
        this.container = new ModuleContainer(module);
    }

    @Test
    public void testNoInjectPoint() {
        Assert.assertThrows(
                InjectException.class,
                () -> new Injector<>(this.container, NoInjectPoint.class)
        );
    }

    @Test
    public void testMultipleInjectPoints() {
        Assert.assertThrows(
                InjectException.class,
                () -> new Injector<>(this.container, TwoInjectPoints.class)
        );
    }

    @Test
    public void testPrivateInjectPoint() {
        Assert.assertThrows(
                InjectException.class,
                () -> new Injector<>(this.container, PrivateInjectPoint.class)
        );
    }

    @Test
    public void testAbstractInjectPoint() {
        Assert.assertThrows(
                InjectException.class,
                () -> this.container.create(AbstractInjectPoint.class)
        );
    }
}
