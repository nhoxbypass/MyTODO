package com.example.nhoxb.mytodo.ui.splash;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.utils.LoadDatabaseTask;

public class SplashActivity extends AppCompatActivity {

    public static final int ADDITIONAL_SPLASH_DISPLAY_LENGTH = 1000; // 1 sec
    public static final String KEY_LIST_ITEM = "list_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_splash);
        LoadDatabaseTask loadResource = new LoadDatabaseTask(this.getApplicationContext());
        loadResource.execute();
    }
}
