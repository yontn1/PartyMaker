package com.example.partymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class InventoryDatabase extends Observable {

    private static InventoryDatabase singleton;

    private SQLiteDatabase database;

    private Context context;

    public static InventoryDatabase get(Context context) {
        if (singleton == null) {
            singleton = new InventoryDatabase(context);
        }

        return singleton;
    }

    public InventoryDatabase(Context context) {
        this.context = context;
        this.database = new DatabaseHelper(context).getWritableDatabase();
    }

    public boolean makeInventory(int userId) {
        for (int i = 1; i < 4; i++) { // 3 drinks.
            insertDrink(userId, i, 0);
        }

        return true;
    }

    private void insertDrink(int userId, int drinkId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.InventoryTable.Columns.USER_ID, userId);
        values.put(DatabaseSchema.InventoryTable.Columns.DRINK_ID, drinkId);
        values.put(DatabaseSchema.InventoryTable.Columns.QUANTITY, quantity);

        database.insert(DatabaseSchema.InventoryTable.TABLE_NAME, null, values);
    }

    public List<UserDrink> getDrinksForUser(int userId) {
        CustomCursorWrapper cursorWrapper = queryGetDrinksForUser(userId);

        List<UserDrink> drinks = new ArrayList<>();
        try {
            cursorWrapper.moveToFirst();
            do {
                UserDrink drink = cursorWrapper.getUserDrink();
                drinks.add(drink);
            } while (cursorWrapper.moveToNext());
        } finally {
            cursorWrapper.close();
        }

        return drinks;
    }

    private CustomCursorWrapper queryGetDrinksForUser(int userId) {
        String sql = "SELECT * FROM "
                + DatabaseSchema.InventoryTable.TABLE_NAME + " INNER JOIN "
                + DatabaseSchema.DrinksTable.TABLE_NAME + " ON "
                + DatabaseSchema.InventoryTable.TABLE_NAME + "." + DatabaseSchema.InventoryTable.Columns.DRINK_ID + " = "
                + DatabaseSchema.DrinksTable.TABLE_NAME + " . " + DatabaseSchema.DrinksTable.Columns.ID + " WHERE "
                + DatabaseSchema.InventoryTable.Columns.USER_ID + " = ?;";

        Cursor cursor = database.rawQuery(sql, new String[]{userId + ""});

        return new CustomCursorWrapper(cursor);
    }

    public String changeDrinkQuantity(int userId, int drinkId, double quantity) {
        queryChangeDrinkQuantity(userId, drinkId, quantity);

        this.setChanged();
        this.notifyObservers();

        return Math.abs(quantity) + " drinks " + (quantity > 0 ? "added" : "removed") + " (id: " + drinkId + ")";
    }

    private void queryChangeDrinkQuantity(int userId, int drinkId, double quantity) {
        String sql = "UPDATE "
                + DatabaseSchema.InventoryTable.TABLE_NAME + " SET "
                + DatabaseSchema.InventoryTable.Columns.QUANTITY + " = "
                + DatabaseSchema.InventoryTable.Columns.QUANTITY + " + ( ? ) WHERE "
                + DatabaseSchema.InventoryTable.Columns.USER_ID + " = ? AND "
                + DatabaseSchema.InventoryTable.Columns.DRINK_ID + " = ?;";

        Cursor cursor = database.rawQuery(sql, new String[]{quantity + "", userId + "", drinkId + ""});

        cursor.moveToFirst();
        cursor.close();
    }
}
