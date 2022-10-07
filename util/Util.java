package com.wachiramartin.todoapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String stringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");

        return formatter.format(date);
    }
}
