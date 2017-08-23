package com.codepath.simpletodo;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by tludewig on 8/22/17.
 */

public class Utils {

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static String dateToString(Date d) {
        return df.format(d);
    }

    public static Date stringToDate(String s) {
        try {
            return df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
