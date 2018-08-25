package com.example.admin.collegenoti.logic;

import com.example.admin.collegenoti.models.User;

import java.util.Random;

/**
 * Created by Admin on 1/7/2018.
 */

public class U {
    public static User userDetails;
    public static final int SIGNATURE = 1;
    public static final int CAMERA = 2;
    public static final int GALLERY = 3;
    public static  final int IMAGE_SIZE=300;
    public static int STROKES = 0;
    public static int X_AXIS = 0;
    public static int Y_AXIS = 0;

    private static int getRandomNumberInRange() {
        int min=100; int max=200;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int getXAxisValue() {
        int min=100; int max=200;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;

    }

    public static int getYAxisValue() {
        int min=100; int max=200;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;

    }

    public static String getImgeType(int position) {
        switch (position) {
            case 0:return "IA_MARKS";
            case 1:return "ATTENDENCE";
            case 2:return "TIME_TABLE";
            case 3:return "ATTENDENCE_SHORTAGE";
            case 4:return "FEE";
            case 5:return "EVENTS";
            case 6:return "PICTURES";
            case 7:return "HOLIDAY";
        }
        return "";
    }

}
