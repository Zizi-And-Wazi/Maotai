package com.administrator.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    private Button login_btn_login;
    private Button login_btn_exit;
    private EditText login_edit_account;
    private EditText login_edit_pwd;
    private CheckBox login_remeber;
    /**
     * android系统下用于数据存储的一个方便的API
     */
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        // 完成sp的初始化。
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        init();}
    public void init()
    {
        login_edit_account = (EditText) findViewById(R.id.login_edit_account);
        login_edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_btn_exit = (Button) findViewById(R.id.login_btn_exit);
        login_remeber = (CheckBox) findViewById(R.id.login_remember);
        if (sp.getBoolean("checkboxBoolean", false))
        {
            //获取sp里面存储的数据

            login_remeber.setChecked(true);
            String savedUsername = sp.getString("username", "");
            String savedPwd = sp.getString("password", "");
            login_edit_account.setText(savedUsername);
            login_edit_pwd.setText(savedPwd);}
        //登录
        login_btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断密码和用户名
                String username = login_edit_account.getText().toString();
                String password = login_edit_pwd.getText().toString();
                if (username.equals("ZQJH") && password.equals("ZQJH")) {
                    //跳转到ZQJH
                    Intent intent = new Intent(MainActivity.this, ZQJH_Nh.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                            "登录成功，正在进入沼气净化单元...", Toast.LENGTH_SHORT).show();
                }
               else if (username.equals("YCLYY") && password.equals("YCLYY")) {
                    //跳转到YCLYY
                    Intent intent = new Intent(MainActivity.this, YCLYY.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                            "登录成功，正在进入预处理厌氧单元...", Toast.LENGTH_SHORT).show();}
                else if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "对不起，用户名" + "或者密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "对不起，用户名" + "或者密码错误", Toast.LENGTH_SHORT).show();
                }

                // 检查用户是否勾选了 记住密码的选项。
                if (login_remeber.isChecked()) {
                    // 说明勾选框被选中了。把用户名和密码给记录下来
                    // 获取到一个参数文件的编辑器。
                    Editor editor = sp.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                } else {
                    Editor editor = sp.edit();
                    editor.putString("username", null);
                    editor.putString("password", null);
                    editor.commit();


                }
            }
        });
        login_btn_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login_edit_account.setText("");
                login_edit_pwd.setText("");
            }
        });
    }
}



