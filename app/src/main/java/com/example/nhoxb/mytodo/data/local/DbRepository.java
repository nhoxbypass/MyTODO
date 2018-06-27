package com.example.nhoxb.mytodo.data.local;

import com.example.nhoxb.mytodo.data.model.Item;

import java.util.List;

public class DbRepository implements DbDataSource {

    private DbOpenHelper dbOpenHelper;

    public DbRepository(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public List<Item> getAllItem() {
        return dbOpenHelper.getAllListItem();
    }

    @Override
    public void insertItem(Item item) {
        if (item.getId() == -1)
            item.setId(dbOpenHelper.getNextAvailableItemId());
        dbOpenHelper.insertItem(item);
    }

    @Override
    public void updateItem(int id, Item item) {
        dbOpenHelper.updateItem(id, item);
    }

    @Override
    public void deleteItem(int id) {
        dbOpenHelper.deleteItem(id);
    }

    @Override
    public long getItemCount() {
        return dbOpenHelper.getSize();
    }
}
