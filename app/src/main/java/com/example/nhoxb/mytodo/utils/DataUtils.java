package com.example.nhoxb.mytodo.utils;

import java.util.ArrayList;
import java.util.List;

public final class DataUtils {
    public static List<String> getDefaultCategoryList() {
        List<String> list = new ArrayList<>();

        list.add("Study");
        list.add("Business");
        list.add("Workout");
        list.add("Relax");

        return list;
    }
}
