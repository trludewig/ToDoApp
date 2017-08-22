package com.codepath.simpletodo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static java.util.Collections.addAll;

// Question for instructors - Why do we have to notifyDataSetChanged on remove, but not on add?
public class ToDoActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private static final int EDIT_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListeners();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById((R.id.etNewItem));
        String itemText = etNewItem.getText().toString();
        //items.add(itemText);
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long kd) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra(Constants.ITEM_TEXT, items.get(pos));
                i.putExtra(Constants.ITEM_POS, pos);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String itemText = data.getExtras().getString(Constants.ITEM_TEXT);
            int itemPos = data.getExtras().getInt(Constants.ITEM_POS, 0);
            Toast.makeText(this, itemText, Toast.LENGTH_SHORT).show();

            items.set(itemPos, itemText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
