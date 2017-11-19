package com.administrator.myapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.myapp.adapter.StaticAdapter;
import com.administrator.myapp.entity.DataResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/9/14.
 */

public class DBHelper_Tj_DATA extends AsyncTask<Void, Void, List<DataResult>> {


    //类参数
    private Context context;                    //数据界面
    private ListView mListView2;            //界面右边的ListView控件
    private StaticAdapter adapter4;   //界面右边的ListView控件的数据
    private int position;       //左侧点击列表的位置
    private TextView nt;
    private String year;
    private String month;
    private String day;
    private int c = 0;
    private int selectIndex;

    //构造方法(赋值)
    public DBHelper_Tj_DATA(Context context, ListView mListView2,TextView nt, StaticAdapter adapter4, int position, String year, String month, String day,int selectIndex) {
        this.context = context;
        this.mListView2 = mListView2;
        this.adapter4 = adapter4;
        this.position = position;
        this.year = year;
        this.month = month;
        this.day = day;
        this.selectIndex=selectIndex;
        this.nt=nt;
    }

    /**
     * 这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    /**
     * 可以使用进度条增加用户体验度。 此方法在主线程执行，用于显示任务执行的进度。
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    /**
     * 可以在这里处理doInBackground得到的数据，能够对UI进行操作，属于UI主线程
     *
     * @param drList
     */
    @Override
    protected void onPostExecute(List<DataResult> drList) {
        String temp = "";
        String[] t = new String[2];
        if(drList.size()==0){
            nt.setText("查询日期数据为空");
            return;
        }
        int count=0;
        //循环遍历查询的数据
        for (int i = 0; i < 300; i++) {
            //更新数据
            if (i < drList.size() / 8) {
                List<String> list = drList.get(i * 8).getStrList();
                for (int n = 0; n < list.get(1).length(); n++) {
                    if (!Character.isDigit(list.get(1).charAt(n)) && list.get(1).charAt(n) != '～') {
                        temp = list.get(1).substring(0, n);
                        t = temp.split("～");
                        break;
                    }
                }
                if (Float.parseFloat(t[1]) >= Float.parseFloat(list.get(0)) && Float.parseFloat(t[0]) <= Float.parseFloat(list.get(0))) {
                    temp = "true";
                } else {
                    temp = "false";
                    if(Float.parseFloat(t[1]) < Float.parseFloat(list.get(0))){
                        count++;
                    }
                }
                adapter4.updataView(i, mListView2, drList.get(i * 8).getStrList().get(0), drList.get(i * 8).getStrList().get(1), drList.get(i * 8).getStrList().get(2), temp);
            } else {
                adapter4.updataView(i, mListView2, " " , " ", " ", " ");
            }
        }
        nt.setText("报警次数："+count);
    }

    @Override
    protected List<DataResult> doInBackground(Void... voids) {
        //返回参数
        List<DataResult> drList = new ArrayList<DataResult>();
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        //数据库操作
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            Log.d("DataBase", "开始连接数据库MT");      //第一个参数是标签，第二个参数是信息
            String url = "jdbc:oracle:thin:@120.76.75.95:1521:orcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
            String user = "MT";// 用户名,系统默认的账户名
            String password = "MT";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            Log.d("DataBase", "连接数据库MT成功");
            String sql = "";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            if(selectIndex==0){
                sql = "SELECT * FROM MT_PLCSBDESIGN  where plcsbdesign_bimid='jhplcysj'";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(selectIndex==1){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_bimid = 'jhplclcfj01'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(selectIndex==2){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_bimid = 'jhplclcfj02'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            //遍历
            int i = 0;
            String temp = "";
            while (result.next()) {
                //遍历数据
                Log.d("DataBase", "正在遍历数据");
                //数据结果对象
                DataResult dr = new DataResult();
                //参数赋值
                List<String> list = dr.getStrList();
                list.add(result.getString("PLCSBDESIGN_LOWLIMIT") + "～" + result.getString("PLCSBDESIGN_HIGHLIMIT") + result.getString("PLCSBDESIGN_UNIT"));
                if (i == position) {
                    temp = list.get(0);
                }
                i++;
            }

            if(selectIndex==0){
                sql = "select * from MT_PLCSB_JHYSJ t where jhysj_time between to_date('" + year + "-" + month + "-" + day + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + year + "-" + month + "-" + day + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') order by jhysj_time asc";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(selectIndex==1){
                sql = "select * from mt_plcsb_JHLCFJ01 t where JHLCFJ01_time between to_date('" + year + "-" + month + "-" + day + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + year + "-" + month + "-" + day + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') order by JHLCFJ01_time asc";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(selectIndex==2){
                sql = "select * from mt_plcsb_JHLCFJ02 t where JHLCFJ02_time between to_date('" + year + "-" + month + "-" + day + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + year + "-" + month + "-" + day + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') order by JHLCFJ02_time asc";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            //遍历
            while (result.next()) {
                //遍历数据
                Log.d("DataBase", "正在遍历数据");
                //数据结果对象
                //DataResult dr = new DataResult();
                //参数赋值
                DataResult dr = new DataResult();
                if(selectIndex==0){
                    if (position == 0) {
                        Log.d("DataBase", result.getString("JHYSJ_GYWD"));
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_GYWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 1) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_PQYL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 2) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_XQYL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 3) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_GYYL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 4) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_ZJDL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 5) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_NLZW"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 6) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_YYC"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 7) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_PQWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 8) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_YFWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 9) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_XQWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 10) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_GLQYC"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 1) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_JLYC"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 12) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_AWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 13) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_BWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 14) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_CWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 15) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_ZSDWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 16) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHYSJ_FZSDWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    }
                    List<String> list = dr.getStrList();
                    list.add(result.getString("jhysj_time"));
                    dr.setStrList(list);
                    //添加到结果列表
                    drList.add(dr);
                }else if(selectIndex==1){
                    if (position == 0) {
                        Log.d("DataBase", result.getString("JHLCFJ01_LCFJZS"));
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ01_LCFJZS"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 1) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ01_LCFJJQYL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 2) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ01_LCFJCKWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    }
                    List<String> list = dr.getStrList();
                    list.add(result.getString("JHLCFJ01_time"));
                    dr.setStrList(list);
                    //添加到结果列表
                    drList.add(dr);
                }else if(selectIndex==2){
                    if (position == 0) {
                        Log.d("DataBase", result.getString("JHLCFJ02_LCFJZS"));
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ02_LCFJZS"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 1) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ02_LCFJJQYL"));
                        list.add(temp);
                        dr.setStrList(list);
                    } else if (position == 2) {
                        List<String> list = dr.getStrList();
                        list.add(result.getString("JHLCFJ02_LCFJCKWD"));
                        list.add(temp);
                        dr.setStrList(list);
                    }
                    List<String> list = dr.getStrList();
                    list.add(result.getString("JHLCFJ02_time"));
                    dr.setStrList(list);
                    //添加到结果列表
                    drList.add(dr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                Log.d("DataBase", "数据库已关闭");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return drList;
    }

}

