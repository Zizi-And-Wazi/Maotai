package com.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ZQJH_Nh extends AppCompatActivity {

    private Button button_1, button_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);


        button_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZQJH_Nh.this, ZQJH.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "正在进入沼气净化单元工艺参数...", Toast.LENGTH_SHORT).show();
            }
        });
        button_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZQJH_Nh.this, ZQJH_NHao.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "正在进入沼气净化单元能耗参数...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}