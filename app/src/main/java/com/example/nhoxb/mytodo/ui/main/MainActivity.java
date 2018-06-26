package com.example.nhoxb.mytodo.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.nhoxb.mytodo.MyTODOApp;
import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.DataManager;
import com.example.nhoxb.mytodo.data.model.Item;
import com.example.nhoxb.mytodo.ui.base.MyItemAdapter;
import com.example.nhoxb.mytodo.ui.edit.EditItemActivity;
import com.example.nhoxb.mytodo.ui.splash.SplashActivity;
import com.example.nhoxb.mytodo.utils.DataUtils;
import com.example.nhoxb.mytodo.utils.ui.DividerItemDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_EDIT_ITEM = "edit_item";
    public static final String KEY_EDIT_ITEM_INDEX = "edit_item_index";
    static final int EDIT_ITEM_REQUEST = 1;
    public ArrayAdapter categoryAdapter;
    FloatingActionButton fabAction, fab1, fab2;
    Animation fabClose, fabOpen, rotateClockwise, rotateAntiClockwise;
    RecyclerView recyclerView;
    DataManager dataManager;
    List<Item> listItem;
    MyItemAdapter listItemAdapter;
    int editItemIndex;
    boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if (Build.VERSION.SDK_INT < 16) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
        {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
            if (actionBar!= null) {
                actionBar.hide();
            }
        }
        */
        setContentView(R.layout.activity_main);

        //Load animator
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        rotateAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fabAction = findViewById(R.id.fab_Action);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        recyclerView = findViewById(R.id.list_item);

        dataManager = MyTODOApp.getDataManager();

        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.addAll(DataUtils.getDefaultCategoryList());

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(MainActivity.this);
        recyclerView.addItemDecoration(itemDecoration);

        // Get extra data from intent
        Bundle extras = getIntent().getExtras();
        listItem = (List<Item>) extras.getSerializable(SplashActivity.KEY_LIST_ITEM);
        listItemAdapter = new MyItemAdapter(listItem);
        recyclerView.setAdapter(listItemAdapter);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);

        listItemAdapter.setOnItemClickListener(new MyItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_EDIT_ITEM, listItem.get(position));
                intent.putExtras(bundle);
                intent.putExtra(KEY_EDIT_ITEM_INDEX, position);
                startActivityForResult(intent, EDIT_ITEM_REQUEST);
                editItemIndex = position;
            }
        });

        listItemAdapter.setOnItemLongClickListener(new MyItemAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                final Item item = listItem.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete '" + item.mName + "'?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dataManager.deleteItem(item.getId());
                                listItem.remove(position);
                                listItemAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;
            }
        });

        fabAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen) {
                    //Already opened, now close
                    closeFAB();
                } else {
                    openFAB();
                }
            }
        });

        //Add item
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialog dialog = new ViewDialog();
                dialog.showDialog(MainActivity.this);
                closeFAB();
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_ITEM_REQUEST) {
                Item item = (Item) data.getExtras().getSerializable(EditItemActivity.KEY_RESULT_ITEM);
                listItem.set(editItemIndex, item);
                dataManager.updateItem(item.getId(), item);
                listItemAdapter.notifyDataSetChanged();
            }
        }
    }

    void openFAB() {
        fabAction.startAnimation(rotateClockwise);
        fab1.startAnimation(fabOpen);
        fab2.startAnimation(fabOpen);
        fab1.setClickable(true);
        fab2.setClickable(true);
        isFabOpen = true;
    }

    void closeFAB() {
        fabAction.startAnimation(rotateAntiClockwise);
        fab1.startAnimation(fabClose);
        fab2.startAnimation(fabClose);
        fab1.setClickable(false);
        fab2.setClickable(false);
        isFabOpen = false;
    }

    public class ViewDialog {

        String mName;
        String mDescription;
        int mPriorityLevel;
        int mHour;
        int mDay;
        int mMonth;
        String mCategory;
        Button dialogButton;
        Spinner categorySelector;
        EditText descriptionInput;
        NumberPicker picker1, picker2, picker3;
        RadioGroup radioGroup;
        RadioButton checkedButton;

        public void showDialog(final Activity activity) {
            final Dialog dialog = new Dialog(activity);

            dialogContentInit(dialog);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDescription = descriptionInput.getText().toString();
                    checkedButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                    mPriorityLevel = radioGroup.indexOfChild(checkedButton);
                    mHour = picker1.getValue();
                    mDay = picker2.getValue();
                    mMonth = picker3.getValue();
                    mCategory = categorySelector.getSelectedItem().toString();

                    TitleInputDialog titleDialog = new TitleInputDialog(activity, dialog, dataManager, listItem, listItemAdapter);
                    titleDialog.showDialog(mDescription, mPriorityLevel, mHour, mDay, mMonth, mCategory);
                }
            });

            dialog.show();

        }

        private void dialogContentInit(Dialog dialog) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_todo_dialog);

            descriptionInput = dialog.findViewById(R.id.description_input);
            picker1 = dialog.findViewById(R.id.number_picker_1);
            picker2 = dialog.findViewById(R.id.number_picker_2);
            picker3 = dialog.findViewById(R.id.number_picker_3);
            categorySelector = dialog.findViewById(R.id.category_selector);
            dialogButton = dialog.findViewById(R.id.btn_dialog);
            radioGroup = dialog.findViewById(R.id.radioGroup);

            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

            categorySelector.setAdapter(categoryAdapter);

            picker1.setMinValue(0);
            picker1.setMaxValue(23);

            picker2.setMinValue(1);
            picker2.setMaxValue(30);

            picker3.setMinValue(1);
            picker3.setMaxValue(12);
        }
    }
}





