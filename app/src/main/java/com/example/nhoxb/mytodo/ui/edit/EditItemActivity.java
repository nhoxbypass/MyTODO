package com.example.nhoxb.mytodo.ui.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.model.Item;
import com.example.nhoxb.mytodo.ui.main.MainActivity;
import com.example.nhoxb.mytodo.utils.DataUtils;

public class EditItemActivity extends AppCompatActivity {

    public static final String KEY_RESULT_ITEM = "RESULT_ITEM";
    static final int ACTION_VIEW = 0;
    static final int ACTION_EDIT = 1;

    TextView tvTitle;
    Spinner categorySelector;
    EditText descriptionInput;
    NumberPicker picker1, picker2, picker3;
    RadioGroup radioGroup;
    RadioButton checkedButton;
    Button btnAction;
    ArrayAdapter categoryAdapter;
    Bundle bundle;
    int editItemIndex;
    int action; // 0: view, 1: edit
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.addAll(DataUtils.getDefaultCategoryList());

        setupUi();

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action == ACTION_VIEW) {
                    //Edit
                    action = ACTION_EDIT;

                    descriptionInput.setEnabled(true);
                    picker1.setEnabled(true);
                    picker2.setEnabled(true);
                    picker3.setEnabled(true);
                    categorySelector.setEnabled(true);
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(true);
                    }
                    btnAction.setText(R.string.save);
                } else if (action == ACTION_EDIT) {
                    //Save
                    item.mDescription = descriptionInput.getText().toString();
                    item.mCategory = categorySelector.getSelectedItem().toString();
                    checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    item.mPriorityLevel = radioGroup.indexOfChild(checkedButton);
                    item.mHour = picker1.getValue();
                    item.mDay = picker2.getValue();
                    item.mMonth = picker3.getValue();

                    btnAction.setText(R.string.edit);

                    bundle.putSerializable(KEY_RESULT_ITEM, item);
                    getIntent().putExtras(bundle);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // Unknown action
                }
            }
        });

    }

    void setupUi() {
        tvTitle = findViewById(R.id.title);
        descriptionInput = findViewById(R.id.edit_description_input);
        picker1 = findViewById(R.id.edit_hour_picker);
        picker2 = findViewById(R.id.edit_day_picker);
        picker3 = findViewById(R.id.edit_month_picker);
        categorySelector = findViewById(R.id.edit_category_selector);
        btnAction = findViewById(R.id.edit_save_btn);
        radioGroup = findViewById(R.id.edit_radioGroup);

        categorySelector.setAdapter(categoryAdapter);

        picker1.setMinValue(0);
        picker1.setMaxValue(23);

        picker2.setMinValue(1);
        picker2.setMaxValue(30);

        picker3.setMinValue(1);
        picker3.setMaxValue(12);

        // Get content info
        editItemIndex = getIntent().getIntExtra(MainActivity.KEY_EDIT_ITEM_INDEX, 0);
        bundle = getIntent().getExtras();
        item = (Item) bundle.getSerializable(MainActivity.KEY_EDIT_ITEM);

        // Update UI
        descriptionInput.setText(item.mDescription);
        tvTitle.setText(item.mName);
        picker1.setValue(item.mHour);
        picker2.setValue(item.mDay);
        picker3.setValue(item.mMonth);
        checkedButton = (RadioButton) radioGroup.getChildAt(item.mPriorityLevel);
        checkedButton.setChecked(true);
        categorySelector.setSelection(categoryAdapter.getPosition(item.mCategory));

        action = ACTION_VIEW;
        descriptionInput.setEnabled(false);
        picker1.setEnabled(false);
        picker2.setEnabled(false);
        picker3.setEnabled(false);
        categorySelector.setEnabled(false);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }
}
