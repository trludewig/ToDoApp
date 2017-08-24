package com.codepath.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.codepath.simpletodo.activities.AddEditItemActivity;
import com.codepath.simpletodo.util.Constants;
import com.codepath.simpletodo.util.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tludewig on 8/22/17.
 */

public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = getArguments().getInt(Constants.YEAR);
        int month = getArguments().getInt(Constants.MONTH);
        int day = getArguments().getInt(Constants.DAY);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dateDialog =  new DatePickerDialog(getActivity(),
                (AddEditItemActivity) getActivity(), year, month, day);
        dateDialog.getDatePicker().setMinDate(Utils.getTomorrow().getTime());
        return dateDialog;
    }
}