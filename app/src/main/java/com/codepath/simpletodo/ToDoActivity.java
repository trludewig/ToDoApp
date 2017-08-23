package com.codepath.simpletodo;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.priority;
import static com.codepath.simpletodo.R.id.etNewItem;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TodoActivity extends AppCompatActivity {

    TodoDatabaseHelper dbHelper;
    private Cursor todoCursor;
    private TodoCursorAdapter todoAdapter;

    ListView lvItems;
    private static final int EDIT_REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        
        dbHelper = TodoDatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        todoCursor = cupboard().withDatabase(db).query(TodoItem.class).getCursor();
        todoAdapter = new TodoCursorAdapter(this, todoCursor);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        setupListViewListeners();
    }

    public void addEditItem(TodoItem item) {
        dbHelper.addUpdate(item);
        todoCursor = dbHelper.getTodoCursor();
        todoAdapter.changeCursor(todoCursor);
    }

    public void removeItem(Cursor cursor) {
        TodoItem item = cupboard().withCursor(cursor).get(TodoItem.class);
        dbHelper.delete(item);
        todoCursor = dbHelper.getTodoCursor();
        todoAdapter.changeCursor(todoCursor);
    }

    public void onAddItem(View v) {
        EditText etItem = (EditText) findViewById((R.id.etNewItem));
        int priority = 3;
        String itemText = etItem.getText().toString();
        if (!etItem.getText().toString().isEmpty()) {
            TodoItem item = new TodoItem(itemText, priority, Utils.dateToLong(new Date()));
            addEditItem(item);
            etItem.setText("");
        }
        else {
            displayError();
        }
    }

    public void displayError() {
        Toast.makeText(this, "Todo cannot be blank.", Toast.LENGTH_SHORT).show();

    }

    private void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                try {
                    Cursor cursor = (Cursor) todoAdapter.getItem(pos);
                    removeItem(cursor);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long kd) {
                Cursor cursor = (Cursor) todoAdapter.getItem(pos);
                TodoItem todoItem = cupboard().withCursor(cursor).get(TodoItem.class);

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);

                i.putExtra(Constants.TODO_ITEM, todoItem);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            TodoItem todoItem = data.getExtras().getParcelable(Constants.TODO_ITEM);
            addEditItem(todoItem);
        }
    }
}
