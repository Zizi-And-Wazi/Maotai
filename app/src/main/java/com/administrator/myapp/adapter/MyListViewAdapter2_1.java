package com.administrator.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.administrator.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter2_1 extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> strList_1 = new ArrayList();


    public MyListViewAdapter2_1(Context context, List<String> list_1) {
        super();
        this.strList_1 = list_1;
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
        MyListViewAdapter2_1.ViewHolder vh;
        if(convertView==null){
            convertView= layoutInflater.inflate(R.layout.item_1,parent,false);
            vh=new MyListViewAdapter2_1.ViewHolder();
            vh.tv_1 = (TextView) convertView.findViewById(R.id.category1);
            convertView.setTag(vh);
        }else {
            vh= (MyListViewAdapter2_1.ViewHolder) convertView.getTag();
        }
        vh.tv_1.setText(strList_1.get(position));
        return convertView;
    }


    class ViewHolder{
        TextView tv_1;

    }


    /**
     * 更新列表数据的方法（逐条更新）
     * @param posi
     * @param listView
     */
    public void updataView(int posi, ListView listView, String dataValue_1) {
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.tv_1.setText(dataValue_1);
            strList_1.set(posi, dataValue_1);
        } else {
            strList_1.set(posi, dataValue_1);
        }


    }

}
