package com.codepath.simpletodo.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.simpletodo.fragments.ErrorDialogFragment;
import com.codepath.simpletodo.util.Constants;
import com.codepath.simpletodo.fragments.DatePickerFragment;
import com.codepath.simpletodo.R;
import com.codepath.simpletodo.model.TodoItem;
import com.codepath.simpletodo.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codepath.simpletodo.util.Constants.EDIT;

public class AddEditItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String DATEPICKER = "dp";
    private static final String ERROR = "err";
    private static final String CLICK = "Click";
    private TodoItem todoItem;
    private EditText etBody;
    private Spinner sPriority;
    private TextView tvDuedate;
    int mode;
    private DatePicker dpDuedate;
    DatePickerFragment dfDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mode = getIntent().getExtras().getInt(Constants.MODE);
        etBody = (EditText) findViewById(R.id.etItem);
        sPriority = (Spinner) findViewById(R.id.sPriority);
        tvDuedate = (TextView) findViewById(R.id.tvDuedate);

        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sPriority.setAdapter(adapter);

        if (mode == EDIT) {
            todoItem = getIntent().getParcelableExtra(Constants.TODO_ITEM);
            etBody.setText(todoItem.getBody());
            etBody.setSelection(etBody.getText().length());
            sPriority.setSelection(todoItem.getPriority() - 1);
            tvDuedate.setText(todoItem.getDuedate());
        } else {
            todoItem = new TodoItem();
        }
    }

    public boolean handleError() {
        String errorMessage = "";
        if (etBody.getText() == null || etBody.getText().toString().isEmpty()) {
            errorMessage = Constants.BODY_ERROR;
        };
        if (tvDuedate.getText().toString().startsWith(CLICK)) {
            errorMessage += Constants.DATE_ERROR;
        }
        if (!errorMessage.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ERROR_MESSAGE, errorMessage);
            ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), ERROR);
            return true;
        }
        return false;
    }

    public void onSubmit(View v) {
        if (handleError()) {
            return;
        }

        todoItem.setBody(etBody.getText().toString());
        todoItem.setPriority(Integer.valueOf(sPriority.getSelectedItem().toString()));
        // Date is already set?

        Intent i = new Intent();
        i.putExtra(Constants.TODO_ITEM, todoItem);
        setResult(RESULT_OK, i);
        finish();
    }

    public void showDatePickerDialog(View v) {

        Bundle bundle = new Bundle();
        int year, month, day;
        if (mode == EDIT) {
            String[] arr = todoItem.getDuedate().split("/");
            year = Integer.valueOf(arr[2]);
            month = Integer.valueOf(arr[0]) - 1;
            day = Integer.valueOf(arr[1]);
        } else {
            // Set the default date to tomorrow.
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH) + 1;
        }
        bundle.putInt(Constants.YEAR, year);
        bundle.putInt(Constants.MONTH, month);
        bundle.putInt(Constants.DAY, day);

        dfDatePicker = new DatePickerFragment();
        dfDatePicker.setArguments(bundle);
        dfDatePicker.show(getSupportFragmentManager(), DATEPICKER);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Date date = new GregorianCalendar(year, month, day).getTime();
        String sDate = Utils.dateToString(date);
        System.out.println(sDate);
        System.out.println(Utils.stringToDate(sDate));
        tvDuedate.setText(sDate);
        todoItem.setDuedate(sDate);

    }
}
