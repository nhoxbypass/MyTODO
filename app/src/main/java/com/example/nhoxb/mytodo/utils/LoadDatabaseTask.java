package com.example.nhoxb.mytodo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nhoxb.mytodo.MyTODOApp;
import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.model.Item;
import com.example.nhoxb.mytodo.ui.main.MainActivity;
import com.example.nhoxb.mytodo.ui.splash.SplashActivity;

import java.io.Serializable;
import java.util.List;

public class LoadDatabaseTask extends AsyncTask<Void, Integer, List<Item>> {
    Context appContext;

    public LoadDatabaseTask(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    protected List<Item> doInBackground(Void... v) {
        //Load du lieu tu db
        List<Item> listItem = MyTODOApp.getDataManager().getAllItem();
        try {
            Thread.sleep(SplashActivity.ADDITIONAL_SPLASH_DISPLAY_LENGTH);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return listItem;
    }

    @Override
    protected void onPostExecute(List<Item> listItem) {
        super.onPostExecute(listItem);

        if (listItem == null) {
            Toast.makeText(appContext, R.string.db_load_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(SplashActivity.KEY_LIST_ITEM, (Serializable) listItem);
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.putExtras(bundle);
        appContext.startActivity(intent);
    }
}