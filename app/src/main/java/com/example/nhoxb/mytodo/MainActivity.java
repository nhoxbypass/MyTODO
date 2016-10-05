package com.example.nhoxb.mytodo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAction, fab1, fab2;
    Animation fabClose, fabOpen, rotateClockwise, rotateAntiClockwise;

    Button btnAdd;

    RecyclerView recyclerView;
    List<ItemModel> listItem;
    public static ArrayAdapter categoryAdapter;
    MyItemAdapter listItemAdapter;
    static final int EDIT_ITEM_REQUEST = 1;
    DBHelper dbHelper;
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
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        rotateAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);



        fabAction = (FloatingActionButton)findViewById(R.id.fab_Action);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        //btnAdd = (Button)findViewById(R.id.btnAdd);
        //inputField = (EditText) findViewById(R.id.inputField);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);


        categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.add("Study");
        categoryAdapter.add("Business");
        categoryAdapter.add("Workout");
        categoryAdapter.add("Relax");


        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(MainActivity.this);
        recyclerView.addItemDecoration(itemDecoration);

        //Lay du lieu tu intent
        Bundle extras = getIntent().getExtras();
        listItem = (List<ItemModel>)extras.getSerializable("LIST_ITEM");
        dbHelper = DBHelper.getDBHelperInstance(MainActivity.this);
        //dbHelper = new DBHelper(getApplicationContext());
        //listItem = dbHelper.dbInitialize();
        listItemAdapter = new MyItemAdapter(this,listItem);
        recyclerView.setAdapter(listItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*
                Intent intent = new Intent(getApplicationContext(),EditItemActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("EDIT_ITEM", listItem.get(position));
                intent.putExtras(bundle);
                intent.putExtra("EDIT_ITEM_INDEX",position);
                startActivityForResult(intent,EDIT_ITEM_REQUEST);
                editItemIndex = position;
                */
            }

            @Override
            public void onLongItemClick(View view, final int position) {
            /*
                final ItemModel itemModel = listItem.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete" + itemModel.mName + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                itemModel.deleteFromDatabase(dbHelper);
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
                        */
            }

        }));

        fabAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen)
                {
                    //Already opened, now close
                    closeFAB();
                }
                else
                {
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

        if (resultCode == RESULT_OK)
        {
            if (requestCode == EDIT_ITEM_REQUEST)
            {
                ItemModel itemModel = (ItemModel) data.getExtras().getSerializable("RESULT_ITEM");
                listItem.set(editItemIndex,itemModel);
                itemModel.updateDatabase(dbHelper);
                listItemAdapter.notifyDataSetChanged();
            }
        }
    }

    void openFAB()
    {
        fabAction.startAnimation(rotateClockwise);
        fab1.startAnimation(fabOpen);
        fab2.startAnimation(fabOpen);
        fab1.setClickable(true);
        fab2.setClickable(true);
        isFabOpen = true;
    }

    void closeFAB()
    {
        fabAction.startAnimation(rotateAntiClockwise);
        fab1.startAnimation(fabClose);
        fab2.startAnimation(fabClose);
        fab1.setClickable(false);
        fab2.setClickable(false);
        isFabOpen = false;
    }


    public class ViewDialog {

        String mName;
        int mPriorityLevel;
        int mHour;
        int mDay;
        int mMonth;
        String mCategory;
        Button dialogButton;
        Spinner categorySelector;
        EditText nameInputField;
        NumberPicker picker1, picker2, picker3;
        RadioGroup radioGroup;
        RadioButton checkedButton;

        public void showDialog(Activity activity){
            final Dialog dialog = new Dialog(activity);

            dialogContentInit(dialog);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mName = nameInputField.getText().toString();
                    checkedButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                    mPriorityLevel = radioGroup.indexOfChild(checkedButton);
                    mHour = picker1.getValue();
                    mDay = picker2.getValue();
                    mMonth = picker3.getValue();
                    mCategory = categorySelector.getSelectedItem().toString();

                    ItemModel item = new ItemModel(mName,mPriorityLevel,mHour,mDay,mMonth, mCategory);


                    item.addToDatabase(dbHelper);
                    listItem.add(item);
                    listItemAdapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(),dbHelper.getSize() + " " + listItemAdapter.getItemCount() + " " + listItem.size(),Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        private void dialogContentInit(Dialog dialog)
        {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_todo_dialog);

            nameInputField = (EditText)dialog.findViewById(R.id.jobNameInput);
            picker1 = (NumberPicker)dialog.findViewById(R.id.number_picker_1);
            picker2  = (NumberPicker)dialog.findViewById(R.id.number_picker_2);
            picker3  = (NumberPicker)dialog.findViewById(R.id.number_picker_3);
            categorySelector = (Spinner)dialog.findViewById(R.id.category_selector);
            dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            radioGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup);

            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);

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

