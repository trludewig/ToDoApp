package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etItem;
    private int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItem = (EditText) findViewById(R.id.etItem);
        itemPos = getIntent().getExtras().getInt(Constants.ITEM_POS);
        etItem.setText(getIntent().getStringExtra(Constants.ITEM_TEXT));
    }

    public void onSubmit(View v) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(Constants.ITEM_TEXT, etItem.getText().toString());
        data.putExtra(Constants.ITEM_POS, itemPos);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
