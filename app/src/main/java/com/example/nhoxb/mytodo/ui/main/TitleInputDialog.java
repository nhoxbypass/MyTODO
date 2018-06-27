package com.example.nhoxb.mytodo.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.DataManager;
import com.example.nhoxb.mytodo.data.model.Item;

import java.util.List;

public class TitleInputDialog extends Dialog {
    EditText inputField;
    Button btnYes, btnNo;

    //
    DataManager dataManager;
    List<Item> mListItem;
    MyItemAdapter mListItemAdapter;
    OnPositiveClickListener positiveClickListener;

    //
    String mName;
    String mDescription;
    int mPriorityLevel;
    int mHour;
    int mDay;
    int mMonth;
    String mCategory;

    public TitleInputDialog(@NonNull final Context context, final DataManager dataManager, List<Item> listItem, MyItemAdapter listItemAdapter) {
        super(context);
        this.dataManager = dataManager;
        mListItem = listItem;
        mListItemAdapter = listItemAdapter;

        setupUi();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positiveClickListener.onPositiveClick();
                mName = inputField.getText().toString();
                Item item = new Item(mName, mDescription, mPriorityLevel, mHour, mDay, mMonth, mCategory);

                dataManager.insertItem(item);
                mListItem.add(item);
                mListItemAdapter.notifyDataSetChanged();

                dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setOnPositiveClickListener(OnPositiveClickListener listener) {
        positiveClickListener = listener;
    }

    public void showDialog(String description, int priorityLevel, int hour, int day, int month, String category) {
        mDescription = description;
        mPriorityLevel = priorityLevel;
        mHour = hour;
        mDay = day;
        mMonth = month;
        mCategory = category;

        show();
    }

    private void setupUi() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.title_input_dialog);

        inputField = findViewById(R.id.input);
        btnYes = findViewById(R.id.btn_dialog_yes);
        btnNo = findViewById(R.id.btn_dialog_no);
    }

    public interface OnPositiveClickListener {
        void onPositiveClick();
    }
}