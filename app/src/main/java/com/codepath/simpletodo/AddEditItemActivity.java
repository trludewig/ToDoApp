package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codepath.simpletodo.R.id.etItem;

public class AddEditItemActivity extends AppCompatActivity {

    private TodoItem todoItem;
    private EditText etBody;
    private Spinner sPriority;
    int mode;
    private DatePicker dpDuedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mode = getIntent().getExtras().getInt(Constants.MODE);
        etBody = (EditText) findViewById(R.id.etItem);
        sPriority = (Spinner) findViewById(R.id.sPriority);
        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sPriority.setAdapter(adapter);

        dpDuedate = (DatePicker) findViewById(R.id.dpDuedate);

        if (mode == Constants.ADD) {
            todoItem = new TodoItem();

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            dpDuedate.init(year, month, day, null);
        }
        else {
            // Edit mode
            todoItem = getIntent().getParcelableExtra(Constants.TODO_ITEM);
            etBody.setText(todoItem.getBody());
            etBody.setSelection(etBody.getText().length());
            sPriority.setSelection(todoItem.getPriority() - 1);
            String sDate = todoItem.getDuedate();
            String[] arr = sDate.split("/");
            System.out.println(arr);
            dpDuedate.init(Integer.valueOf(arr[2]), Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), null);
        }

    }

    public void onSubmit(View v) {

        todoItem.setBody(etBody.getText().toString());
        todoItem.setPriority(Integer.valueOf(sPriority.getSelectedItem().toString()));
        int day = dpDuedate.getDayOfMonth();
        int month = dpDuedate.getMonth() + 1; // for display, we increment
        int year = dpDuedate.getYear();

        Date date = new GregorianCalendar(year, month - 1, day).getTime();
        String temp = Utils.dateToString(date);
        System.out.println(temp);
        System.out.println(Utils.stringToDate(temp));

        todoItem.setDuedate(temp);
        Intent i = new Intent();
        i.putExtra(Constants.TODO_ITEM, todoItem);
        setResult(RESULT_OK, i);
        finish();
    }
}
