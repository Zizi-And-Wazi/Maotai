package com.administrator.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class edit extends AppCompatActivity {
    private  Button back_title,exit_title;
    private FrameLayout mContentLayout;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();   //加载 activity_title 布局 ，并获取标题及两侧按钮
    }


    private void setupViews() {
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        back_title = (Button) findViewById(R.id.back_title);
        exit_title = (Button) findViewById(R.id.exit_title);
    }

    /**
     * 是否显示返回按钮
     * @param backwardResid  文字
     * @param show  true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (back_title != null) {
            if (show) {
                back_title.setText(backwardResid);
                back_title.setVisibility(View.VISIBLE);
            } else {
                back_title.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
    protected void showForwardView(int forwardResId, boolean show) {
        if (exit_title != null) {
            if (show) {
                exit_title.setVisibility(View.VISIBLE);
                exit_title.setText(forwardResId);
            } else {
                exit_title.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */



    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */


}