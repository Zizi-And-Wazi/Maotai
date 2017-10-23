package com.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.administrator.myapp.adapter.MyListViewAdapter1;
import com.administrator.myapp.adapter.MyListViewAdapter2_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ZQJH_Tj extends AppCompatActivity {


    private int selectIndex = 0;
    private static final String[] mMenus = {"压缩机", "罗茨风机01", "罗茨风机02"};
    private ListView mListView1, mListView2;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2_1 adapter4;
    private Context context;
    List<String> strList_1 = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datea_2);
        initView();
        context = this;
        //刷新右边列表
        DBHelper_Tj db = new DBHelper_Tj(context, mListView2, adapter4, 0);
        db.execute();
    }

    private void initView() {
        mListView1 = (ListView) findViewById(R.id.list_item_1);
        mListView2 = (ListView) findViewById(R.id.list_item_2);

        //左边列表（初始化）
        adapter1 = new MyListViewAdapter1(mMenus, this, selectIndex);
        mListView1.setAdapter(adapter1);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectIndex = position;
                //把下标传过去，然后刷新adapter
                adapter1.setIndex(position);
                adapter1.notifyDataSetChanged();
                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                mListView1.smoothScrollToPositionFromTop(position, 0);

                //刷新右边列表
                DBHelper_Tj db = new DBHelper_Tj(context, mListView2, adapter4, selectIndex);
                db.execute();
            }
        });

        //右边列表（初始化添加10条数据）

        for (int i = 0; i < 17; i++) {
            strList_1.add("");
        }
        adapter4 = new MyListViewAdapter2_1(this, strList_1);
        mListView2.setAdapter(adapter4);

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //list点击事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ZQJH_Tj.this, ZQJH_TJ_DATA.class);
                intent.putExtra("name",strList_1.get(position));
                intent.putExtra("selectIndex",selectIndex);
                intent.putExtra("position",position);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        strList_1.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }
}





