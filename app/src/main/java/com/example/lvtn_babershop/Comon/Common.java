package com.example.lvtn_babershop.Comon;

import com.example.lvtn_babershop.Model.Baber;
import com.example.lvtn_babershop.Model.Salon;
import com.example.lvtn_babershop.Model.TimeSlot;
import com.example.lvtn_babershop.Model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BABER_LOAD_DONE = "BABER_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_BABER_SELECTED = "BABER_SELECTED";
    public static final int TIME_SLOT_TOTAL = 20;
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static final String IS_LOGIN = "isLOGIN";
    public static User currentUser;
    public static Salon currentSalon;
    public static int step = 0; //init first step is 0
    public static String city ="";
    public static Baber currentBaber;
    public static int currentTimeSlot = 1;
    public static Calendar currentDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "9:00 - 9:30";
            case 1:
                return "9:30 - 10:00";
            case 2:
                return "10:00 - 10:30";
            case 3:
                return "10:30 - 11:00";
            case 4:
                return "11:00 - 11:30";
            case 5:
                return "11:30 - 12:00";
            case 6:
                return "12:00 - 12:30";
            case 7:
                return "12:30 - 13:00";
            case 8:
                return "13:00 - 13:30";
            case 9:
                return "13:30 - 14:00";
            case 10:
                return "14:00 - 14:30";
            case 11:
                return "14:30 - 15:00";
            case 12:
                return "15:00 - 15:30";
            case 13:
                return "15:30 - 16:00";
            case 14:
                return "16:00 - 16:30";
            case 15:
                return "16:30 - 17:00";
            case 16:
                return "17:00 - 17:30";
            case 17:
                return "17:30 - 18:00";
            case 18:
                return "18:00 - 18:30";
            case 19:
                return "18:30 - 19:00";
            default:
                return "Closed";

        }
    }

    public static String formartShoppingItemName(String name) {
        return name.length() > 20 ? new StringBuilder(name.substring(0,20)).append("...").toString():name;
    }
}
