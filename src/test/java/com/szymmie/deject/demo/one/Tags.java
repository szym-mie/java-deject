package com.szymmie.deject.demo.one;

import java.util.Arrays;
import java.util.List;

public class Tags {
    private final List<String> tags;

    private Tags(List<String> tags) {
        this.tags = tags;
    }

    public String getTag(int index) {
        return this.tags.get(index);
    }

    public int getCount() {
        return this.tags.size();
    }

    public static Tags from(String... tags) {
        return new Tags(Arrays.asList(tags));
    }
}
