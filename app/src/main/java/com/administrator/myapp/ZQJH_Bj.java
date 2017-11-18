package com.administrator.myapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.administrator.myapp.view.SpinerPopWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ZQJH_Bj extends AppCompatActivity {

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> list;
    private TextView tvValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datea_3);
        initData();
        tvValue = (TextView) findViewById(R.id.tv_value);
        tvValue.setOnClickListener(clickListener);
        mSpinerPopWindow = new SpinerPopWindow<String>(this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
    }

    /**
     * ����popupwindowȡ��
     */
    private OnDismissListener  dismissListener=new OnDismissListener() {
        @Override
        public void onDismiss() {
            setTextImage(R.mipmap.icon_down);
        }
    };

    /**
     * popupwindow��ʾ��ListView��item�����¼�
     */
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            tvValue.setText(list.get(position));
        }
    };

    /**
     * ��ʾPopupWindow
     */
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_value:
                    mSpinerPopWindow.setWidth(tvValue.getWidth());
                    mSpinerPopWindow.showAsDropDown(tvValue);
                    setTextImage(R.mipmap.icon_up);
                    break;
            }
        }
    };

    /**
     * ��ʼ������
     */
    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("test:" + i);
        }
    }

    /**
     * ��TextView�ұ�����ͼƬ
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// ��������ͼƬ��С����������ʾ
        tvValue.setCompoundDrawables(null, null, drawable, null);
    }
}

