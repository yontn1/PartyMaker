package com.example.partymaker;

import android.database.Cursor;
import android.database.CursorWrapper;

public class CustomCursorWrapper extends CursorWrapper {

    public CustomCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        if (getCount() == 0) { // No results.
            return null;
        }

        int id = getInt(getColumnIndex(DatabaseSchema.UsersTable.Columns.ID));
        String username = getString(getColumnIndex(DatabaseSchema.UsersTable.Columns.USERNAME));
        String password = getString(getColumnIndex(DatabaseSchema.UsersTable.Columns.PASSWORD));

        return new User(id, username, password);
    }

    public UserDrink getUserDrink() {
        if (getCount() == 0) { // No results.
            return null;
        }

        int userId = getInt(getColumnIndex(DatabaseSchema.InventoryTable.Columns.USER_ID));
        int drinkId = getInt(getColumnIndex(DatabaseSchema.InventoryTable.Columns.DRINK_ID));
        String drinkName = getString(getColumnIndex(DatabaseSchema.DrinksTable.Columns.NAME));
        double quantity = getDouble(getColumnIndex(DatabaseSchema.InventoryTable.Columns.QUANTITY));

        return new UserDrink(userId, drinkId, drinkName, quantity);
    }
}
