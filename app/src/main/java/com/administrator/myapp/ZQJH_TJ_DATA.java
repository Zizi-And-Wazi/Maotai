package com.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.administrator.myapp.adapter.StaticAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mike Li on 2017/10/6.
 */

public class ZQJH_TJ_DATA extends AppCompatActivity {
    private TextView itemName;
    private Integer selectIndex;
    private int p;
    private ListView mListView2;
    private StaticAdapter adapter2;
    private TextView nt;
    List<String> strList_1 = new ArrayList<String>();
    List<String> strList_2 = new ArrayList<String>();
    List<String> strList_3 = new ArrayList<String>();
    List<String> strList_4 = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();//创建一个String类型的数组列表。
    private List<String> list2 = new ArrayList<String>();//创建一个String类型的数组列表。
    private List<String> list3 = new ArrayList<String>();//创建一个String类型的数组列表。
    private Spinner mySpinner;
    private Spinner mySpinner2;
    private Spinner mySpinner3;
    private ArrayAdapter<String> adapter;//创建一个数组适配器
    private ArrayAdapter<String> adapter21;//创建一个数组适配器
    private ArrayAdapter<String> adapter3;//创建一个数组适配器
    private Context context;
    private static final String[] mMenus = {"压缩机", "罗茨风机01", "罗茨风机02"};
    private String year;
    private String month;
    private String day;
    private Button button7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datea_3);
        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            itemName = (TextView) findViewById(R.id.itemname);
            String name = intent.getStringExtra("name");
            selectIndex = intent.getIntExtra("selectIndex", 0);
            p = intent.getIntExtra("position", 0);
            itemName.setText(mMenus[selectIndex] + " " + name + " 统计数据");
        }
        // 添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项，即数据源
        list1.add("2017");
        list1.add("2018");
        for (int i = 1; i < 13; i++) {
            list2.add(String.valueOf(i));
        }
        for (int i = 1; i < 32; i++) {
            list3.add(String.valueOf(i));
        }
        Calendar now = Calendar.getInstance();
        mySpinner = (Spinner) findViewById(R.id.tv_value);
        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        mySpinner.setAdapter(adapter);
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                year = adapter.getItem(arg2);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        mySpinner2 = (Spinner) findViewById(R.id.tv_value2);
        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
        adapter21 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter21.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        mySpinner2.setAdapter(adapter21);
        mySpinner2.setSelection(now.get(Calendar.MONTH));
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        mySpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                month = adapter21.getItem(arg2);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        mySpinner3 = (Spinner) findViewById(R.id.tv_value3);
        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        mySpinner3.setAdapter(adapter3);
        mySpinner3.setSelection(now.get(Calendar.DAY_OF_MONTH) - 1);
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                day = adapter3.getItem(arg2);
                Log.d("day", day);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        initView();
        context = this;
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper_Tj_DATA db = new DBHelper_Tj_DATA(context, mListView2,nt, adapter2, p, year, month, day, selectIndex);
                db.execute();
            }
        });
    }

    private void initView() {
        mListView2 = (ListView) findViewById(R.id.list_item_21);
        nt=(TextView)findViewById(R.id.nt);
        for (int i = 0; i < 300; i++) {
            strList_1.add("");
            strList_2.add("");
            strList_3.add("");
            strList_4.add("");
        }
        //左边列表（初始化）
        adapter2 = new StaticAdapter(this, strList_1, strList_2, strList_3, strList_4);
        mListView2.setAdapter(adapter2);
    }
}
