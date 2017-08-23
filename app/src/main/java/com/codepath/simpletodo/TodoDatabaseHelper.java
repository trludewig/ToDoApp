package com.codepath.simpletodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by tludewig on 8/21/17.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static TodoDatabaseHelper sInstance;

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    static {
        // register our models
        cupboard().register(TodoItem.class);
    }

    public static synchronized TodoDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new TodoDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks in this method if you want

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(db).upgradeTables();
        // do migration work if you have an alteration to make to your schema here

    }

    public Cursor getTodoCursor() {
        SQLiteDatabase db = getWritableDatabase();
        return cupboard().withDatabase(db).query(TodoItem.class).getCursor();
    }



    public void addUpdate(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        cupboard().withDatabase(db).put(item);
    }

    public void delete(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        cupboard().withDatabase(db).delete(item);
    }

}