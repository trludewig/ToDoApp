package com.codepath.simpletodo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tludewig on 8/21/17.
 */

public class TodoItem implements Parcelable {
    private Long _id; // for cupboard
    private String body;
    private int priority;
    private long duedate;

    public TodoItem(String body, int priority, long duedate) {
        this.body = body;
        this.priority = priority;
        this.duedate = duedate;
    }

    public TodoItem() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getDuedate() {
        return duedate;
    }

    public void setDuedate(long duedate) {
        this.duedate = duedate;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(_id);
        out.writeString(body);
        out.writeInt(priority);
        out.writeLong(duedate);
    }

    public static final Parcelable.Creator<TodoItem> CREATOR
            = new Parcelable.Creator<TodoItem>() {
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };

    private TodoItem(Parcel in) {
        _id = in.readLong();
        body = in.readString();
        priority = in.readInt();
        duedate = in.readLong();
    }
}
