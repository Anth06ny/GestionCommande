package com.example.anthony.gestionstock.vue.custom_composant;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by Anthony on 21/12/2016.
 */
public class MyNumberPicker extends NumberPicker {

    private int color, overColor, overValue;

    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setNumberPickerColor() {

        if (color != 0 && overColor != 0) {

            Field selectorWheelPaintField;
            int nbChild = getChildCount();

            for (int i = 0; i < nbChild; i++) {
                View child = getChildAt(i);

                if (child instanceof EditText) {
                    try {
                        selectorWheelPaintField = getClass().getDeclaredField("mSelectorWheelPaint");
                        selectorWheelPaintField.setAccessible(true);
                        ((Paint) selectorWheelPaintField.get(this)).setColor(color);
                        ((EditText) child).setTextColor(Color.BLACK);
                        invalidate();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setColor(@ColorInt int color, @ColorInt int overColor) {
        this.color = color;
        this.overColor = overColor;
    }

    public void setOverValue(int overValue) {
        this.overValue = overValue;
    }

    @Override
    public void setMaxValue(int maxValue) {
        super.setMaxValue(maxValue);
        setNumberPickerColor();
    }
}
