package com.example.nhoxb.mytodo.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.DataManager;
import com.example.nhoxb.mytodo.data.model.Item;
import com.example.nhoxb.mytodo.ui.base.MyItemAdapter;

import java.util.List;

public class TitleInputDialog {
    Activity mActivity;
    EditText inputField;
    Button btnYes, btnNo;
    Dialog dialog;

    //
    DataManager dataManager;
    List<Item> mListItem;
    MyItemAdapter mListItemAdapter;

    //
    String mName;
    String mDescription;
    int mPriorityLevel;
    int mHour;
    int mDay;
    int mMonth;
    String mCategory;

    public TitleInputDialog(final Activity activity, final Dialog itemDialog, final DataManager dataManager, List<Item> listItem, MyItemAdapter listItemAdapter) {
        mActivity = activity;
        this.dataManager = dataManager;
        mListItem = listItem;
        mListItemAdapter = listItemAdapter;
        initDialog();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDialog.dismiss();
                mName = inputField.getText().toString();
                Item item = new Item(mName, mDescription, mPriorityLevel, mHour, mDay, mMonth, mCategory);

                dataManager.insertItem(item);
                mListItem.add(item);
                mListItemAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public void showDialog(String description, int priorityLevel, int hour, int day, int month, String category) {
        mDescription = description;
        mPriorityLevel = priorityLevel;
        mHour = hour;
        mDay = day;
        mMonth = month;
        mCategory = category;

        dialog.show();
    }

    private void initDialog() {
        dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.title_input_dialog);

        inputField = dialog.findViewById(R.id.input);
        btnYes = dialog.findViewById(R.id.btn_dialog_yes);
        btnNo = dialog.findViewById(R.id.btn_dialog_no);
    }
}