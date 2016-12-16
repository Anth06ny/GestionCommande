package com.example.anthony.gestionstock;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Anthony on 16/12/2016.
 */
public class Utils {

    public static void vibration(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }

    /**
     * Converti
     *
     * @param number
     * @return
     */
    public static String formatToMoney(double number) {
        double epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%.0f", number); // sdb
        }
        else {
            return String.format("%.2f", number); // dj_segfault
        }
    }
}
