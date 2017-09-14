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

public class MyListViewAdapter2 extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> strList_1 = new ArrayList();
    private List<String> strList_2 = new ArrayList();
    private List<String> strList_3 = new ArrayList();
    private List<String> strList_4 = new ArrayList();

    public MyListViewAdapter2(Context context, List<String> list_1, List<String> list_2, List<String> list_3, List<String> list_4) {
        super();
        this.strList_1 = list_1;
        this.strList_2 = list_2;
        this.strList_3 = list_3;
        this.strList_4 = list_4;
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
        MyListViewAdapter2.ViewHolder vh;
        if(convertView==null){
            convertView= layoutInflater.inflate(R.layout.item_2,parent,false);
            vh=new MyListViewAdapter2.ViewHolder();
            vh.tv_1 = (TextView) convertView.findViewById(R.id.parameter1);
            vh.tv_2 = (TextView) convertView.findViewById(R.id.plan1);
            vh.tv_3 = (TextView) convertView.findViewById(R.id.real1);
            vh.tv_4 = (TextView) convertView.findViewById(R.id.notice1);
            convertView.setTag(vh);
        }else {
            vh= (MyListViewAdapter2.ViewHolder) convertView.getTag();
        }


        vh.tv_1.setText(strList_1.get(position));
        vh.tv_2.setText(strList_2.get(position));
        vh.tv_3.setText(strList_3.get(position));
        vh.tv_4.setText("●");
        if(strList_4.get(position).equals("true")){
            vh.tv_4.setTextColor(Color.rgb(0, 255, 128));
        }else if(strList_4.get(position).equals("false")){
            vh.tv_4.setTextColor(Color.rgb(240, 27, 45));
        }else{
            vh.tv_4.setTextColor(Color.rgb(255, 255, 255));
        }


        return convertView;
    }


    class ViewHolder{
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
    }


    /**
     * 更新列表数据的方法（逐条更新）
     * @param posi
     * @param listView
     */
    public void updataView(int posi, ListView listView, String dataValue_1,String dataValue_2,String dataValue_3,String dataValue_4) {
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.tv_1.setText(dataValue_3);
            strList_1.set(posi, dataValue_3);

            holder.tv_2.setText(dataValue_2);
            strList_2.set(posi, dataValue_2);

            holder.tv_3.setText(dataValue_1);
            strList_3.set(posi, dataValue_1);

            holder.tv_4.setText("●");
            strList_4.set(posi, dataValue_4);
            if(dataValue_4.equals("true")){
                holder.tv_4.setTextColor(Color.rgb(0, 255, 128));
            }else if(dataValue_4.equals("false")){
                holder.tv_4.setTextColor(Color.rgb(240, 27, 45));
            }else{
                holder.tv_4.setTextColor(Color.rgb(255, 255, 255));
            }
        } else {
            strList_1.set(posi, dataValue_3);
            strList_2.set(posi, dataValue_2);
            strList_3.set(posi, dataValue_1);
            strList_4.set(posi, "●");
        }

        //Log.d("dataValue","updateView:==============================================");
        Log.d("dataValue","updateView:  "+posi+"   "+dataValue_1+"   "+dataValue_2+"   "+dataValue_3+"   "+dataValue_4);
        //Log.d("dataValue","=========================================================");

    }

}
