package com.example.nhoxb.mytodo.utils.ui;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.model.Item;

public final class UiUtils {
    public static @DrawableRes
    int getCategoryIcon(String category) {
        switch (category) {
            case Item.ITEM_CATEGORY_STUDY:
                return R.drawable.ic_study;

            case Item.ITEM_CATEGORY_BUSINESS:
                return R.drawable.ic_business;

            case Item.ITEM_CATEGORY_WORKOUT:
                return R.drawable.ic_workout;

            case Item.ITEM_CATEGORY_RELAX:
                return R.drawable.ic_relax;

            default:
                return R.drawable.ic_study;
        }
    }

    public static @ColorRes
    int getPriorityColor(int priorityLevel) {
        int colorId = R.color.colorPriorityL1;
        if (priorityLevel == 0) {
            colorId = R.color.colorPriorityL1;
        } else if (priorityLevel == 1) {
            colorId = R.color.colorPriorityL2;
        } else if (priorityLevel == 2) {
            colorId = R.color.colorPriorityL3;
        }

        return colorId;
    }
}
