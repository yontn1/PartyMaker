package com.example.partymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersDatabase {

    private static UsersDatabase singleton;

    private SQLiteDatabase database;

    private Context context;

    public static UsersDatabase get(Context context) {
        if (singleton == null) {
            singleton = new UsersDatabase(context);
        }

        return singleton;
    }

    public UsersDatabase(Context context) {
        this.context = context;
        this.database = new DatabaseHelper(context).getWritableDatabase();
    }

    /**
     * Checks the loggin. If correct, returns the userId, if not, -1.
     */
    public int matchPassword(String username, String password) {
        User user = getUserByUsername(username);

        if (user == null) {
            return -1;
        }

        if (! password.equals(user.getPassword())) {
            return -1;
        }

        return user.getId();
    }

    private User getUserByUsername(String username) {
        CustomCursorWrapper cursorWrapper = queryGetPasswordByUsername(username);

        try {
            cursorWrapper.moveToFirst();
            User user = cursorWrapper.getUser();
            return user;
        } finally {
            cursorWrapper.close();
        }
    }

    private CustomCursorWrapper queryGetPasswordByUsername(String username) {
        Cursor cursor = database.query(
                DatabaseSchema.UsersTable.TABLE_NAME,
                null,
                DatabaseSchema.UsersTable.Columns.USERNAME + " = ?",
                new String[] {username},
                null,
                null,
                null
        );

        return new CustomCursorWrapper(cursor);
    }

    public boolean createUser(String username, String password) {
        User user = getUserByUsername(username);

        if (user != null) { // Username already exists.
            return false;
        }

        long userId = queryCreateUser(username, password);

        return InventoryDatabase.get(context).makeInventory((int) userId);
    }

    private long queryCreateUser(String username, String password) {
        ContentValues contentValues = getUserContentValues(username, password);

        return database.insert(
                DatabaseSchema.UsersTable.TABLE_NAME,
                null,
                contentValues
        );
    }

    private ContentValues getUserContentValues(String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.UsersTable.Columns.USERNAME, username);
        contentValues.put(DatabaseSchema.UsersTable.Columns.PASSWORD, password);

        return contentValues;
    }
}
