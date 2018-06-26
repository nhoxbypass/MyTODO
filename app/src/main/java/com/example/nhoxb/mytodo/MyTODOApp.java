package com.example.nhoxb.mytodo;

import android.app.Application;

import com.example.nhoxb.mytodo.data.AppDataManager;
import com.example.nhoxb.mytodo.data.DataManager;
import com.example.nhoxb.mytodo.data.local.DbOpenHelper;
import com.example.nhoxb.mytodo.data.local.DbRepository;

public class MyTODOApp extends Application {

    static DataManager dataManager;

    public static DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dataManager = new AppDataManager(new DbRepository(DbOpenHelper.getDBHelperInstance(this)));
    }
}
