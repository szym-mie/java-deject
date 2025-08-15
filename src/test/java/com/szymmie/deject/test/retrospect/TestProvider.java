package com.szymmie.deject.test.retrospect;


import com.szymmie.deject.Container;
import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;
import com.szymmie.deject.annotations.AsNamed;
import com.szymmie.deject.annotations.Provides;
import com.szymmie.deject.retrospect.BindException;
import com.szymmie.deject.test.inject.BadByName;
import com.szymmie.deject.test.inject.GoodByName;
import com.szymmie.deject.test.inject.OneParameter;
import com.szymmie.deject.test.inject.TwoParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestProvider {
    private Container container;
    private static final int INTEGER = 4242;

    public static class TestModule extends Module {
        @Provides
        public Integer integer() {
            return TestProvider.INTEGER;
        }

        @Provides
        @AsNamed("1")
        public Integer i1() {
            return 1;
        }

        @Provides
        @AsNamed("2")
        public Integer i2() {
            return 2;
        }
    }

    @Before
    public void before() {
        Module module = new TestModule();
        this.container = new ModuleContainer(module);
    }

    @Test
    public void testOneParameterInject() {
        OneParameter instance = this.container.create(OneParameter.class);
        Assert.assertEquals(OneParameter.class, instance.getClass());
        Assert.assertEquals(TestProvider.INTEGER, instance.n);
    }

    @Test
    public void testTwoParameterInject() {
        TwoParameters instance = this.container.create(TwoParameters.class);
        Assert.assertEquals(TwoParameters.class, instance.getClass());
        Assert.assertEquals(TestProvider.INTEGER, instance.n);
        Assert.assertEquals(TestProvider.INTEGER, instance.m);
    }

    @Test
    public void testByName() {
        GoodByName instance = this.container.create(GoodByName.class);
        Assert.assertEquals(GoodByName.class, instance.getClass());
        Assert.assertEquals(1, instance.a);
        Assert.assertEquals(2, instance.b);
    }

    @Test
    public void testNotFoundByName() {
        Assert.assertThrows(
                BindException.class,
                () -> this.container.create(BadByName.class)
        );
    }
}
