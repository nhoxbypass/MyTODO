package com.example.nhoxb.mytodo.data;

import com.example.nhoxb.mytodo.data.local.DbDataSource;
import com.example.nhoxb.mytodo.data.model.Item;

import java.util.List;

public class AppDataManager implements DataManager {

    DbDataSource dbDataSource;

    public AppDataManager(DbDataSource dbDataSource) {
        this.dbDataSource = dbDataSource;
    }

    @Override
    public List<Item> getAllItem() {
        return dbDataSource.getAllItem();
    }

    @Override
    public void insertItem(Item item) {
        dbDataSource.insertItem(item);
    }

    @Override
    public void updateItem(int id, Item item) {
        dbDataSource.updateItem(id, item);
    }

    @Override
    public void deleteItem(int id) {
        dbDataSource.deleteItem(id);
    }

    @Override
    public long getItemCount() {
        return dbDataSource.getItemCount();
    }
}
