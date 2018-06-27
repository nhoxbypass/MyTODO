package com.example.nhoxb.mytodo.data.model;

import java.io.Serializable;

/**
 * Created by nhoxb on 9/28/2016.
 */
public class Item implements Serializable {
    public static final String ITEM_CATEGORY_STUDY = "Study";
    public static final String ITEM_CATEGORY_BUSINESS = "Business";
    public static final String ITEM_CATEGORY_WORKOUT = "Workout";
    public static final String ITEM_CATEGORY_RELAX = "Relax";


    public String mName;
    public String mDescription;
    public int mPriorityLevel;
    public int mHour;
    public int mDay;
    public int mMonth;
    public String mCategory;
    private int mId;

    //Constructor
    public Item() {
        mId = -1;
        mName = "Unnamed";
        mPriorityLevel = 1;
        mHour = 0;
        mDay = 1;
        mMonth = 1;
        mCategory = ITEM_CATEGORY_STUDY;
    }

    public Item(String name, String description, int priorityLevel, int hour, int day, int month, String category) {
        mName = name;
        mDescription = description;
        mPriorityLevel = priorityLevel;
        mHour = hour;
        mDay = day;
        mMonth = month;
        mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}


