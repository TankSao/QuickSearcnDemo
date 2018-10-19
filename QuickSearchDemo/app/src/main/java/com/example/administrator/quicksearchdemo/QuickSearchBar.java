package com.example.administrator.quicksearchdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/10/19.
 */

@SuppressLint("AppCompatCustomView")
public class QuickSearchBar extends TextView {
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    /**
     * 滑动的Y
     */
    private float eventY = 0;
    private QuickSearchCallback callback;
    private int w;
    private int h;
    private int itemH;
    private Paint textPaint;
    /**
     * 普通情况下字体大小
     */
    float singleTextH;
    public QuickSearchBar(Context context) {
        super(context);
    }

    public QuickSearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnQuickSearchCallback(QuickSearchCallback callback) {
        this.callback = callback;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int currentSelectIndex = -1;
        if (eventY != 0) {
            for (int i = 0; i < letters.length; i++) {
                float currentItemY = itemH * i;
                float nextItemY = itemH * (i + 1);
                if (eventY >= currentItemY && eventY < nextItemY) {
                    currentSelectIndex = i;
                    if (callback != null) {
                        callback.onSelectStr(currentSelectIndex, letters[i]);
                    }
                }
            }
        }
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        itemH = h / letters.length;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        singleTextH = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < letters.length; i++) {
            canvas.drawText(letters[i], w - getPaddingRight(), singleTextH + itemH * i, textPaint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (callback != null) {
                    callback.onTouchDown();
                }
                if (event.getX() > (w - getPaddingRight() - singleTextH - 10)) {
                    eventY = event.getY();
                    invalidate();
                    return true;
                } else {
                    eventY = 0;
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_CANCEL:
                if (callback != null) {
                    callback.onTouchUp();
                }
                eventY = 0;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (callback != null) {
                    callback.onTouchUp();
                }
                if (event.getX() > (w - getPaddingRight() - singleTextH - 10)) {
                    eventY = 0;
                    invalidate();
                    return true;
                } else
                    break;
        }
        return super.onTouchEvent(event);
    }
}
