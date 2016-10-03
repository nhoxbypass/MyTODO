package com.example.nhoxb.mytodo;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    Spinner categorySelector;
    EditText nameInputField;
    NumberPicker picker1, picker2, picker3;
    RadioGroup radioGroup;
    RadioButton checkedButton;
    Button btnAction;
    ArrayAdapter categoryAdapter;
    Bundle bundle;
    Intent intent;
    int editItemIndex;
    int action; // 0: view, 1: edit
    ItemModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        initContent();


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (action == 0)
                {
                    //Edit
                    action = 1;
                    Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_SHORT).show();
                    nameInputField.setEnabled(true);
                    picker1.setEnabled(true);
                    picker2.setEnabled(true);
                    picker3.setEnabled(true);
                    categorySelector.setEnabled(true);
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(true);
                    }
                    btnAction.setText("SAVE");
                }
                else if(action == 1)
                {
                    //Save
                    item.mName = nameInputField.getText().toString();
                    item.mCategory = categorySelector.getSelectedItem().toString();
                    checkedButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                    item.mPriorityLevel = radioGroup.indexOfChild(checkedButton);
                    item.mHour = picker1.getValue();
                    item.mDay = picker2.getValue();
                    item.mMonth  = picker3.getValue();
                    bundle.putSerializable("RESULT_ITEM",item);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    btnAction.setText("EDIT");
                    finish();
                }
                else
                {

                }
            }
        });

    }

    void initContent()
    {
        nameInputField = (EditText)findViewById(R.id.edit_name_input);
        picker1 = (NumberPicker)findViewById(R.id.edit_hour_picker);
        picker2  = (NumberPicker)findViewById(R.id.edit_day_picker);
        picker3  = (NumberPicker)findViewById(R.id.edit_month_picker);
        categorySelector = (Spinner)findViewById(R.id.edit_category_selector);
        btnAction = (Button)findViewById(R.id.edit_save_btn);
        radioGroup = (RadioGroup)findViewById(R.id.edit_radioGroup);

        categorySelector.setAdapter(MainActivity.categoryAdapter);

        picker1.setMinValue(0);
        picker1.setMaxValue(23);

        picker2.setMinValue(1);
        picker2.setMaxValue(30);

        picker3.setMinValue(1);
        picker3.setMaxValue(12);

        //Set content information
        intent = getIntent();
        editItemIndex = intent.getIntExtra("EDIT_ITEM_INDEX",0);
        bundle = intent.getExtras();
        item = (ItemModel) bundle.getSerializable("EDIT_ITEM");
        nameInputField.setText(item.mName);
        picker1.setValue(item.mHour);
        picker2.setValue(item.mDay);
        picker3.setValue(item.mMonth);
        checkedButton = (RadioButton)radioGroup.getChildAt(item.mPriorityLevel);
        checkedButton.setChecked(true);
        categorySelector.setSelection(MainActivity.categoryAdapter.getPosition(item.mCategory));


        action = 0;
        nameInputField.setEnabled(false);
        picker1.setEnabled(false);
        picker2.setEnabled(false);
        picker3.setEnabled(false);
        categorySelector.setEnabled(false);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }
}
