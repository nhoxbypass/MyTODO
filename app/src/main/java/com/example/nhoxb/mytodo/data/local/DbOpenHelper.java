package com.example.nhoxb.mytodo.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nhoxb.mytodo.data.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhoxb on 9/29/2016.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyTODODatabase.db";
    private static final String TABLE_NAME = "items";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_DAY = "day";
    private static final String KEY_MONTH = "month";

    //For upgrate database when structure change
    public static int DATA_VERSION = 1;
    //Singleton pattern
    private static DbOpenHelper sHelperInstance;
    private SQLiteDatabase db;

    private DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static synchronized DbOpenHelper getDBHelperInstance(Context context) {
        if (sHelperInstance == null) {
            sHelperInstance = new DbOpenHelper(context.getApplicationContext());
        }

        return sHelperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " INTEGER,"
                + KEY_NAME + " VARCHAR, "
                + KEY_DESCRIPTION + " VARCHAR, "
                + KEY_CATEGORY + " VARCHAR,"
                + KEY_PRIORITY + " INTEGER,"
                + KEY_HOUR + " INTEGER,"
                + KEY_DAY + " INTEGER,"
                + KEY_MONTH + " INTEGER);");
    }

    public Cursor query(String sql) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        closeDatabase();
        return cursor;
    }

    public long getSize() {
        db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        closeDatabase();
        return count;
    }

    public int getNextAvailableItemId() {
        Cursor c = query("SELECT * FROM " + TABLE_NAME
                + " WHERE " + KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM " + TABLE_NAME + ");");

        int id;

        if (c.getCount() == 0) {
            id = 0;
        } else {
            id = c.getInt(0) + 1;
        }

        return id;
    }

    public boolean insertItem(int id, String name, String description, String category, int priority, int hour, int day, int month) {
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_CATEGORY, category);
        contentValues.put(KEY_PRIORITY, priority);
        contentValues.put(KEY_HOUR, hour);
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MONTH, month);
        db.insert(TABLE_NAME, null, contentValues);

        closeDatabase();
        return true;
    }

    public boolean insertItem(Item item) {
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, item.getId());
        contentValues.put(KEY_NAME, item.mName);
        contentValues.put(KEY_DESCRIPTION, item.mDescription);
        contentValues.put(KEY_CATEGORY, item.mCategory);
        contentValues.put(KEY_PRIORITY, item.mPriorityLevel);
        contentValues.put(KEY_HOUR, item.mHour);
        contentValues.put(KEY_DAY, item.mDay);
        contentValues.put(KEY_MONTH, item.mMonth);
        db.insert(TABLE_NAME, null, contentValues);

        closeDatabase();

        return true;
    }


    /**
     * delete id row of table
     */
    public boolean deleteItem(int id) {
        db = this.getWritableDatabase();

        int index = db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        closeDatabase();
        return index > 0;
    }

    public boolean updateItem(int keyId, Item item) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, keyId);
        contentValues.put(KEY_NAME, item.mName);
        contentValues.put(KEY_DESCRIPTION, item.mDescription);
        contentValues.put(KEY_CATEGORY, item.mCategory);
        contentValues.put(KEY_PRIORITY, item.mPriorityLevel);
        contentValues.put(KEY_HOUR, item.mHour);
        contentValues.put(KEY_DAY, item.mDay);
        contentValues.put(KEY_MONTH, item.mMonth);

        int index = db.update(TABLE_NAME, contentValues, KEY_ID + "='" + keyId + "'", null);
        closeDatabase();
        return index > 0;
    }

    public List<Item> getAllListItem() {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        List<Item> itemList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            //To get all data
            do {
                itemList.add(parseItem(cursor));
            }
            while (cursor.moveToNext());
        } else {
            return new ArrayList<>();
        }

        closeDatabase();
        return itemList;
    }

    private Item parseItem(Cursor c) {
        if (c != null && c.getCount() != 0) {
            Item item = new Item();

            item.setId(c.getInt(0));
            item.mName = c.getString(1);
            item.mDescription = c.getString(2);
            item.mCategory = c.getString(3);
            item.mPriorityLevel = c.getInt(4);
            item.mHour = c.getInt(5);
            item.mDay = c.getInt(6);
            item.mMonth = c.getInt(7);

            return item;
        }

        return null;
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVerison) {
        Log.i("SQLite", "Upgrading DB");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        DATA_VERSION = newVerison;
        onCreate(db);
    }
}