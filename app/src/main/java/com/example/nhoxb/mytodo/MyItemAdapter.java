package com.example.nhoxb.mytodo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nhoxb on 9/28/2016.
 */

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.ViewHolder>
{
    // ... constructor and member variables
    private Context mContext;
    // Usually involves inflating a layout from XML and returning the holder
    // Store a member variable for the contacts
    private List<ItemModel> mListItem;

    // Pass in the contact array into the constructor
    public MyItemAdapter(Context context, List<ItemModel> listItem) {
        mListItem = listItem;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext().getApplicationContext();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        //Inflate custom layout
        View view = layoutInflater.inflate(R.layout.custom_item_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModel itemModel = mListItem.get(position);

        TextView nameTV = holder.nameTextView;
        nameTV.setText(itemModel.mName);
        TextView dateTV = holder.dateTextView;
        dateTV.setText(itemModel.mDay + "/" + itemModel.mMonth);

        ImageView imageView = holder.imageView;

        switch (itemModel.mCategory)
        {
            case "Study":
                imageView.setImageResource(R.drawable.ic_study);
                break;

            case "Business":
                imageView.setImageResource(R.drawable.ic_business);
                break;

            case "Workout":
                imageView.setImageResource(R.drawable.ic_workout);
                break;

            case "Relax":
                imageView.setImageResource(R.drawable.ic_relax);
                break;
        }

        //Set color
        int colorId = R.color.colorPriorityL1;
        if (itemModel.mPriorityLevel == 0)
        {
            colorId = R.color.colorPriorityL1;
        }
        else if (itemModel.mPriorityLevel == 1)
        {
            colorId = R.color.colorPriorityL2;
        }
        else if (itemModel.mPriorityLevel == 2)
        {
            colorId = R.color.colorPriorityL3;
        }
        dateTV.setTextColor(mContext.getResources().getColor(colorId));
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView dateTextView;
        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.item_name);
            dateTextView = (TextView) itemView.findViewById(R.id.item_date);
            imageView = (ImageView)itemView.findViewById(R.id.imgView);
        }
    }
}

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
{
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener)
    {
        mContext = context;
        mListener = listener;


        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //Toast.makeText(mContext,"onSingleTapUp",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //super.onLongPress(e);
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null && mListener != null)
                {
                    mListener.onLongItemClick(view,recyclerView.getChildAdapterPosition(view));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(),e.getY()); //Lay view o toa do
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
        {
            mListener.onItemClick(childView,rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}