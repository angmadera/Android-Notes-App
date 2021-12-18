package com.example.christopher.recycler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//For formatting purposes

    //formats date
    public class NoteUtils {
        public static String dateFromLong(long time) {
            DateFormat format = new SimpleDateFormat("EEE MMM dd, hh:mm aaa", Locale.US);
            return format.format(new Date(time));
    }
    //formats RecyclerViews note description
    public static String descriptionFormat(String notes) {
        if (notes.length() <= 79) {
            return notes;
        } else {
            return notes.substring(0, 79) + "...";
        }
    }
}
