package com.example.nhoxb.mytodo.ui.base;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhoxb.mytodo.R;
import com.example.nhoxb.mytodo.data.model.Item;
import com.example.nhoxb.mytodo.utils.ui.UiUtils;

import java.util.List;

/**
 * Created by nhoxb on 9/28/2016.
 */

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.ViewHolder> {
    // ... constructor and member variables
    private List<Item> mListItem;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    // Pass in the item array into the constructor
    public MyItemAdapter(List<Item> listItem) {
        mListItem = listItem;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Inflate custom layout
        View view = layoutInflater.inflate(R.layout.custom_item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mListItem.get(position);

        holder.nameTextView.setText(item.mName);
        holder.dateTextView.setText(item.mDay + "/" + item.mMonth);
        holder.imageView.setImageResource(UiUtils.getCategoryIcon(item.mCategory));
        holder.dateTextView.setTextColor(ContextCompat.getColor(holder.dateTextView.getContext(), UiUtils.getPriorityColor(item.mPriorityLevel)));
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView nameTextView;
        TextView dateTextView;
        ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            dateTextView = itemView.findViewById(R.id.item_date);
            imageView = itemView.findViewById(R.id.imgView);

            // Add event listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return itemLongClickListener.onItemLongClick(view, getAdapterPosition());
                }
            });
        }
    }
}