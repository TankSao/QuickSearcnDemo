package com.example.administrator.quicksearchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView lv;
    @BindView(R.id.bar)
    QuickSearchBar bar;
    @BindView(R.id.tv)
    TextView tv;
    private List<String> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListView();
        initSearchBar();
    }

    private void initData() {
        //初始化数据
        users.add("上海");
        users.add("北京");
        users.add("深圳");
        users.add("广州");
        users.add("成都");
        users.add("杭州");
        users.add("重庆");
        users.add("武汉");
        users.add("苏州");
        users.add("西安");
        users.add("天津");
        users.add("南京");
        users.add("郑州");
        users.add("长沙");
        users.add("沈阳");
        users.add("青岛");
        users.add("宁波");
        users.add("东莞");
        users.add("无锡");
        users.add("昆明");
        users.add("大连");
        users.add("厦门");
        users.add("合肥");
        users.add("佛山");
        users.add("福州");
        users.add("哈尔滨");
        users.add("济南");
        users.add("温州");
        users.add("长春");
        users.add("石家庄");
        users.add("常州");
        users.add("泉州");
        users.add("南宁");
        users.add("贵阳");
        users.add("乌鲁木齐");
        users.add("潍坊");
        users.add("烟台");
        users.add("太原");
        users.add("厦门");
        users.add("惠州");
        users.add("保定");
        users.add("台州");
        users.add("呼和浩特");
        users.add("连云港");
        users.add("驻马店");
        users.add("宿迁");
        users.add("秦皇岛");
        users.add("赣州");
        users.add("鄂尔多斯");
        users.add("常德");
        Comparator comparator= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(users,comparator);
    }

    private void initListView() {
        //初始化ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(  MainActivity.this, android.R.layout.simple_list_item_1, users);
        lv.setAdapter(adapter);
    }
    private void initSearchBar() {
        //初始化QuickSearchBar
        bar.setOnQuickSearchCallback(new QuickSearchCallback() {
            @Override
            public void onTouchDown() {
                tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTouchUp() {
                tv.setVisibility(View.GONE);
            }

            @Override
            public void onSelectStr(int index, String selectStr) {
                    tv.setText(selectStr);
                for (int i = 0; i < users.size(); i++) {
                    if (selectStr.equalsIgnoreCase(getFirst(users.get(i)))) {
                        lv.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }

    //获取首字母
    private String getFirst(String s) {
        String pinyin = Cn2Spell.getPinYin(s);
        String firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
        return  firstLetter;
    }
}
