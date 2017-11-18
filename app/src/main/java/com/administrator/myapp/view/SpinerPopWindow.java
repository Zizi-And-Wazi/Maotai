package com.administrator.myapp.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.administrator.myapp.R;
import android.view.ViewGroup.LayoutParams;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */


public class SpinerPopWindow<T> extends PopupWindow {
    private LayoutInflater inflater;
    private ListView mListView;
    private List<T> list;
    private MyAdapter  mAdapter;

    public SpinerPopWindow(Context context, List<T> list, OnItemClickListener clickListener) {
        super(context);
        inflater=LayoutInflater.from(context);
        this.list=list;
        init(clickListener);
    }

    private void init(OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter=new MyAdapter());
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.item_layout, null);
                holder.tvName=(TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(getItem(position).toString());
            return convertView;
        }
    }

    private class ViewHolder{
        private TextView tvName;
    }
}

