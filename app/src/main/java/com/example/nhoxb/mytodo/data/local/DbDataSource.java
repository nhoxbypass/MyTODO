package com.example.nhoxb.mytodo.data.local;

import com.example.nhoxb.mytodo.data.model.Item;

import java.util.List;

public interface DbDataSource {
    List<Item> getAllItem();

    void insertItem(Item item);

    void updateItem(int id, Item item);

    void deleteItem(int id);

    long getItemCount();
}
