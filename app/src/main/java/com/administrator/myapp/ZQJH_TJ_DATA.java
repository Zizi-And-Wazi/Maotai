package com.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.administrator.myapp.adapter.MyListViewAdapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike Li on 2017/10/6.
 */

public class ZQJH_TJ_DATA extends AppCompatActivity{
    private TextView itemName;
    private Integer selectIndex;
    private ListView  mListView2;
    private MyListViewAdapter2 adapter2;
    List<String> strList_1 = new ArrayList<String>();
    private Context context;
    private static final String[] mMenus = {"压缩机", "罗茨风机01", "罗茨风机02"};
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
            selectIndex = intent.getIntExtra("selectIndex",0);
            itemName.setText(mMenus[selectIndex]+":"+name+" 统计数据");
        }
        initView();
        context = this;
        //刷新右边列表
        DBHelper_Tj_DATA db = new DBHelper_Tj_DATA(context, mListView2, adapter2, 0);
        db.execute();
    }
    private void initView() {
        mListView2 = (ListView) findViewById(R.id.list_item_2);
        for(int i=0;i<100;i++){
            strList_1.add("");
        }
        //左边列表（初始化）
        adapter2 = new MyListViewAdapter2(this,strList_1,strList_1,strList_1,strList_1);
        mListView2.setAdapter(adapter2);
    }
}
