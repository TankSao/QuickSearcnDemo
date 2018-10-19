package com.example.administrator.quicksearchdemo;

/**
 * Created by Administrator on 2018/10/19.
 */

public interface QuickSearchCallback {
    void onTouchDown();
    void onTouchUp();
    void onSelectStr(int index, String selectStr);
}
