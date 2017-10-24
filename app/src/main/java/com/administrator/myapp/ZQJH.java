package com.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.myapp.adapter.MyListViewAdapter1;
import com.administrator.myapp.adapter.MyListViewAdapter2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;


public class ZQJH extends AppCompatActivity {

    private int selectIndex = 0;
    private static final String[] mMenus = {"压缩机","罗茨风机01","罗茨风机02"};
    private ListView mListView1, mListView2;
    private TextView time;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2 adapter2;
    private Context context;
    private Button button6,button7;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datea);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "刷新数据中...", Toast.LENGTH_SHORT).show();
                new TimeThread().start(); //启动新的线程
                initView();
                //刷新右边列表
                DBHelper db = new DBHelper(context, mListView2, adapter2,selectIndex);
                db.execute();
            }
        });
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZQJH.this, ZQJH_Tj.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "正在进入沼气净化单元统计数据", Toast.LENGTH_SHORT).show();
            }
            });
        new TimeThread().start(); //启动新的线程
        initView();
        context = this;
        //刷新右边列表
        DBHelper db = new DBHelper(context, mListView2, adapter2,0);
        db.execute();
    }

    private void initView() {
        mListView1 = (ListView) findViewById(R.id.list_item_1);
        mListView2 = (ListView) findViewById(R.id.list_item_2);
        time=(TextView)findViewById(R.id.time) ;
        //左边列表（初始化）
        adapter1 = new MyListViewAdapter1(mMenus, this, selectIndex);
        mListView1.setAdapter(adapter1);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectIndex=position;
                //把下标传过去，然后刷新adapter
                adapter1.setIndex(position);
                adapter1.notifyDataSetChanged();
                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                mListView1.smoothScrollToPositionFromTop(position,0);

                //刷新右边列表
                DBHelper db = new DBHelper(context, mListView2, adapter2,selectIndex);
                db.execute();
            }
        });

        //右边列表（初始化添加10条数据）
        List<String> strList_1 = new ArrayList<String>();
        List<String> strList_2 = new ArrayList<String>();
        List<String> strList_3 = new ArrayList<String>();
        List<String> strList_4 = new ArrayList<String>();
        for(int i=0;i<17;i++){
            strList_1.add("");
            strList_2.add("");
            strList_3.add("");
            strList_4.add("");
        }
        adapter2 = new MyListViewAdapter2(this,strList_1,strList_2,strList_3,strList_4);
        mListView2.setAdapter(adapter2);
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();
                    Date date = new Date(sysTime);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time.setText(format.format(date));//更新时间
                    break;
                default:
                    break;

            }
        }
    };
}



