package com.szymmie.deject.demo.one;

import com.szymmie.deject.Module;
import com.szymmie.deject.annotations.AsNamed;
import com.szymmie.deject.annotations.Provides;

public class DemoModule extends Module {
    @Provides
    @AsNamed("maxTags")
    public Integer maxTags() {
        return 3;
    }

    @Provides
    public Formatter tagFormatter() {
        return text -> "#" + text
                .replace(" ", "-")
                .replace("_", "-");
    }
}
