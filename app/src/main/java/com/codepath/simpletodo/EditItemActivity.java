package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.codepath.simpletodo.R.id.etItem;

public class EditItemActivity extends AppCompatActivity {

    private TodoItem todoItem;
    private EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        todoItem = getIntent().getParcelableExtra(Constants.TODO_ITEM);

        etBody = (EditText) findViewById(R.id.etItem);
        etBody.setText(todoItem.getBody());
    }

    public void onSubmit(View v) {

        todoItem.setBody(etBody.getText().toString());
        Intent i = new Intent();
        i.putExtra(Constants.TODO_ITEM, todoItem);
        setResult(RESULT_OK, i);
        finish();
    }
}
