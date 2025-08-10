package com.szymmie.deject.demo.multi;

import com.szymmie.deject.Module;
import com.szymmie.deject.annotations.AsNamed;
import com.szymmie.deject.annotations.Provides;
import com.szymmie.deject.demo.multi.log.Logger;
import com.szymmie.deject.demo.multi.log.PlainLogger;

public class DemoModule extends Module {
    @Provides
    public Logger logger() {
        return new PlainLogger();
    }

    @Provides
    @AsNamed("messageQueueSize")
    public Integer messageQueueSize() {
        return 4;
    }
}
