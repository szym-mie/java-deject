package com.szymmie.deject.demo.one;

import com.szymmie.deject.annotations.ByType;
import com.szymmie.deject.annotations.Inject;
import com.szymmie.deject.annotations.ByName;

public class Feed {
    private final Formatter formatter;
    private final int maxTags;

    @Inject
    public Feed(@ByType Formatter formatter, @ByName("maxTags") Integer maxTags) {
        this.formatter = formatter;
        this.maxTags = maxTags;
    }

    public void push(Tags tags) {
        int tagsShownCount = Math.min(tags.getCount(), this.maxTags);
        int tagsHiddenCount = tags.getCount() - this.maxTags;
        System.out.print("[i] ");
        for (int i = 0; i < tagsShownCount; i++) {
            System.out.print(this.formatter.format(tags.getTag(i)));
            System.out.print(" ");
        }
        if (tagsHiddenCount > 0) {
            System.out.print("(+");
            System.out.print(tagsHiddenCount);
            System.out.print(")");
        }
        System.out.print("\n");
    }
}
