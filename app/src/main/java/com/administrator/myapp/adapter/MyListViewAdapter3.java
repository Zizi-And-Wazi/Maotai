package com.administrator.myapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.administrator.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter3 extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> strList_1 = new ArrayList();
    private List<String> strList_3 = new ArrayList();

    public MyListViewAdapter3(Context context, List<String> list_1, List<String> list_3) {
        super();
        this.strList_1 = list_1;
        this.strList_3 = list_3;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strList_1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyListViewAdapter3.ViewHolder vh;
        if(convertView==null){
            convertView= layoutInflater.inflate(R.layout.item_3,parent,false);
            vh=new MyListViewAdapter3.ViewHolder();
            vh.tv_1 = (TextView) convertView.findViewById(R.id.parameter1);
            vh.tv_3 = (TextView) convertView.findViewById(R.id.real1);
            convertView.setTag(vh);
        }else {
            vh= (MyListViewAdapter3.ViewHolder) convertView.getTag();
        }


        vh.tv_1.setText(strList_1.get(position));
        vh.tv_3.setText(strList_3.get(position));



        return convertView;
    }


    class ViewHolder{
        TextView tv_1;
        TextView tv_3;
    }


    /**
     * 更新列表数据的方法（逐条更新）
     * @param posi
     * @param listView
     */
    public void updataView(int posi, ListView listView, String dataValue_1,String dataValue_3) {
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.tv_1.setText(dataValue_1);
            strList_1.set(posi, dataValue_1);


            holder.tv_3.setText(dataValue_3);
            strList_3.set(posi, dataValue_3);
        } else {
            strList_1.set(posi, dataValue_1);
            strList_3.set(posi, dataValue_3);
        }

        //Log.d("dataValue","updateView:==============================================");
        Log.d("dataValue","updateView:  "+posi+"   "+dataValue_1+"   "+dataValue_3);
        //Log.d("dataValue","=========================================================");

    }

}