package com.example.partymaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "plm1.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        // Users table.
        sql = "CREATE TABLE " + DatabaseSchema.UsersTable.TABLE_NAME + " ( "
                + DatabaseSchema.UsersTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseSchema.UsersTable.Columns.USERNAME + ", "
                + DatabaseSchema.UsersTable.Columns.PASSWORD + ");";

        db.execSQL(sql);

        // Drinks table.
        sql = "CREATE TABLE " + DatabaseSchema.DrinksTable.TABLE_NAME + " ( "
                + DatabaseSchema.DrinksTable.Columns.ID + " INTEGER PRIMARY KEY, " // No AUTOINCREMENT, since this table will be hardcoded.
                + DatabaseSchema.DrinksTable.Columns.NAME + ");";

        db.execSQL(sql);

        // Inventory table.
        sql = "CREATE TABLE " + DatabaseSchema.InventoryTable.TABLE_NAME + " ( "
                + DatabaseSchema.InventoryTable.Columns.USER_ID + " INTEGER, "
                + DatabaseSchema.InventoryTable.Columns.DRINK_ID + " INTEGER, "
                + DatabaseSchema.InventoryTable.Columns.QUANTITY + " DECIMAL(7,2), "
                + " FOREIGN KEY( " + DatabaseSchema.InventoryTable.Columns.USER_ID +
                ") REFERENCES " + DatabaseSchema.UsersTable.TABLE_NAME + "( " + DatabaseSchema.UsersTable.Columns.ID + "), "
                + " FOREIGN KEY( " + DatabaseSchema.InventoryTable.Columns.DRINK_ID +
                ") REFERENCES " + DatabaseSchema.DrinksTable.TABLE_NAME + "( " + DatabaseSchema.DrinksTable.Columns.ID + ")); ";

        db.execSQL(sql);

        // Friends table.
        sql = "CREATE TABLE " + DatabaseSchema.FriendsTable.TABLE_NAME + " ( "
                + DatabaseSchema.FriendsTable.Columns.USER1_ID + " INTEGER, "
                + DatabaseSchema.FriendsTable.Columns.USER2_ID + " INTEGER, "
                + " FOREIGN KEY( " + DatabaseSchema.FriendsTable.Columns.USER1_ID +
                ") REFERENCES " + DatabaseSchema.UsersTable.TABLE_NAME + "( " + DatabaseSchema.UsersTable.Columns.ID + "), "
                + " FOREIGN KEY( " + DatabaseSchema.FriendsTable.Columns.USER2_ID +
                ") REFERENCES " + DatabaseSchema.UsersTable.TABLE_NAME + "( " + DatabaseSchema.UsersTable.Columns.ID + ")); ";

        db.execSQL(sql);

        // Parties table.
        sql = "CREATE TABLE " + DatabaseSchema.PartiesTable.TABLE_NAME + " ( "
                + DatabaseSchema.PartiesTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseSchema.PartiesTable.Columns.NAME + ", "
                + DatabaseSchema.PartiesTable.Columns.TIME + " DATETIME);";

        db.execSQL(sql);

        // PartyUsers table.
        sql = "CREATE TABLE " + DatabaseSchema.PartyUsersTable.TABLE_NAME + " ( "
                + DatabaseSchema.PartyUsersTable.Columns.PARTY_ID + " INTEGER , "
                + DatabaseSchema.PartyUsersTable.Columns.USER_ID + " INTEGER, "
                + DatabaseSchema.PartyUsersTable.Columns.STATUS + ", "
                + " FOREIGN KEY( " + DatabaseSchema.PartyUsersTable.Columns.PARTY_ID +
                ") REFERENCES " + DatabaseSchema.PartiesTable.TABLE_NAME + "( " + DatabaseSchema.PartiesTable.Columns.ID + "), "
                + " FOREIGN KEY( " + DatabaseSchema.PartyUsersTable.Columns.USER_ID +
                ") REFERENCES " + DatabaseSchema.UsersTable.TABLE_NAME + "( " + DatabaseSchema.UsersTable.Columns.ID + ")); ";

        db.execSQL(sql);

        // The "Drinks" table is at the moment hardcoded...

        // Entry 1.
        sql = "INSERT INTO " + DatabaseSchema.DrinksTable.TABLE_NAME + " ( "
                + DatabaseSchema.DrinksTable.Columns.ID + " ,"
                + DatabaseSchema.DrinksTable.Columns.NAME + ") VALUES (1, 'BEER'); ";

        db.execSQL(sql);

        // Entry 2.
        sql = "INSERT INTO " + DatabaseSchema.DrinksTable.TABLE_NAME + " ( "
                + DatabaseSchema.DrinksTable.Columns.ID + " ,"
                + DatabaseSchema.DrinksTable.Columns.NAME + ") VALUES (2, 'VODKA'); ";

        db.execSQL(sql);

        // Entry 3.
        sql = "INSERT INTO " + DatabaseSchema.DrinksTable.TABLE_NAME + " ( "
                + DatabaseSchema.DrinksTable.Columns.ID + " ,"
                + DatabaseSchema.DrinksTable.Columns.NAME + ") VALUES (3, 'RUM'); ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
