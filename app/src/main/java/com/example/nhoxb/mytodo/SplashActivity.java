package com.example.nhoxb.mytodo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    DBHelper dbHelper;
    List<ItemModel> listItem;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadResource loadResource = new LoadResource();
        loadResource.execute("");
    }

    class LoadResource extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            //Load du lieu tu db
            dbHelper = DBHelper.getDBHelperInstance(SplashActivity.this); //Get dbhelper instance from Singleton
            listItem = dbHelper.dbInitialize();
            try {
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST_ITEM",(Serializable)listItem);
            //bundle.putSerializable("DB_HELPER",dbHelper);
            //put SQLiteDatabase qua activity ko dc???
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            finish();
        }
    }
}
