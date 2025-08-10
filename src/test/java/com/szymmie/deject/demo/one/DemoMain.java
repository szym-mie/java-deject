package com.szymmie.deject.demo.one;

import com.szymmie.deject.Module;
import com.szymmie.deject.ModuleContainer;

public class DemoMain {
    public static void main(String[] args) {
        Module module = new DemoModule();
        ModuleContainer moduleContainer = new ModuleContainer(module);

        Feed feed = moduleContainer.create(Feed.class);
        Tags tinyTags = Tags.from("tiny");
        Tags longTags = Tags.from("hot", "java", "code", "jdk11", "reflect");

        feed.push(tinyTags);
        feed.push(longTags);
    }
}
