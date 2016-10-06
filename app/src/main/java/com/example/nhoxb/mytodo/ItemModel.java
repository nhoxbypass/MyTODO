package com.example.nhoxb.mytodo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nhoxb on 9/28/2016.
 */
public class ItemModel implements Serializable {
    public String mName;
    public String mDescription;
    public int mPriorityLevel;
    public int mHour;
    public int mDay;
    public int mMonth;
    public String mCategory;
    private int mId;

    //Constructor
    public ItemModel()
    {
        mId = 0;
        mName = "Demo name";
        mPriorityLevel = 1;
        mHour = 0;
        mDay = 1;
        mMonth = 1;
        mCategory = "Studying";
    }

    public ItemModel(String name, String description, int priorityLevel, int hour, int day, int month, String category)
    {
        mName = name;
        mDescription = description;
        mPriorityLevel = priorityLevel;
        mHour = hour;
        mDay = day;
        mMonth = month;
        mCategory = category;
    }

    //Method
    void addToDatabase(DBHelper dbHelper)
    {
        mId = dbHelper.getNextItemId();
        dbHelper.insertItem(mId, this);
    }

    void updateDatabase(DBHelper dbHelper)
    {
        dbHelper.updateItem(mId,this);
    }

    void setId(int id)
    {
        mId = id;
    }

    void deleteFromDatabase(DBHelper dbHelper)
    {
        dbHelper.deleteItem(mId);
    }
}


