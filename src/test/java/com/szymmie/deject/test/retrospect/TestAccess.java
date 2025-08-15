package com.szymmie.deject.test.retrospect;

import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;
import com.szymmie.deject.annotations.Provides;
import com.szymmie.deject.retrospect.BindException;
import com.szymmie.deject.test.inject.OneParameter;
import org.junit.Assert;
import org.junit.Test;

public class TestAccess {
    private static final int INTEGER = 4242;

    public static class PrivateProviderModule extends Module {
        @Provides
        private Integer integer() {
            return TestAccess.INTEGER;
        }
    }

    private static class PrivateModuleModule extends Module {
        @Provides
        public Integer integer() {
            return TestAccess.INTEGER;
        }
    }

    @Test
    public void testPrivateModule() {
        Module module = new PrivateModuleModule();
        ModuleContainer container = new ModuleContainer(module);
        Assert.assertThrows(
                BindException.class,
                () -> container.create(OneParameter.class)
        );
    }

    @Test
    public void testPrivateProvider() {
        Module module = new PrivateProviderModule();
        ModuleContainer container = new ModuleContainer(module);
        Assert.assertThrows(
                BindException.class,
                () -> container.create(OneParameter.class)
        );
    }
}
