package com.test.weatherassignment.utils;

/**
 * Created by chaitanya on 28.02.2019
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return formatter.format(new Date(Long.parseLong(date) * 1000));
    }
    public static String formatDateString(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        try {
            @SuppressLint("SimpleDateFormat") Date dateO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(date);
            return formatter.format(dateO);
        } catch (ParseException e) {
            e.printStackTrace();
            return formatter.format(new Date());
        }

    }
    public static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}