package com.test.bean.sections;
import java.util.Comparator;

public class SortBySequence  implements Comparator<SectionItems> {

    public int compare(SectionItems a, SectionItems b)
    {
        return a.getSequence() - b.getSequence();
    }
}
