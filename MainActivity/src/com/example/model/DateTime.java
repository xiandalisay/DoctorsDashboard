/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/20/14
 * @Description
 * 	gets the current date and time
 */

package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTime {
	
	public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        System.out.println(""+dateFormat.format(date));
        return dateFormat.format(date);
	}
}
