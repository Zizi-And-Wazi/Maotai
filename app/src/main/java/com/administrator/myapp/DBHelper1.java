package com.administrator.myapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.administrator.myapp.adapter.MyListViewAdapter3;
import com.administrator.myapp.entity.DataResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/30.
 */

public class DBHelper1 extends AsyncTask<Void,Void,List<DataResult>> {


    //类参数
    private Context context;                    //数据界面
    private ListView mListView2;            //界面右边的ListView控件
    private MyListViewAdapter3 adapter3;   //界面右边的ListView控件的数据
    private int position;       //左侧点击列表的位置

    //构造方法(赋值)
    public DBHelper1(Context context, ListView mListView2, MyListViewAdapter3 adapter3, int position){
        this.context = context;
        this.mListView2 = mListView2;
        this.adapter3 = adapter3;
        this.position = position;
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
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    /**
     * 可以在这里处理doInBackground得到的数据，能够对UI进行操作，属于UI主线程
     * @param drList
     */
    @Override
    protected void onPostExecute(List<DataResult> drList) {
        List<String> list = drList.get(0).getStrList();
        //循环遍历查询的数据
        for(int i=0;i<list.size()/2;i++){
            //更新数据
            adapter3.updataView(i,mListView2,list.get(i),list.get(i+list.size()/2));
        }
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
            String sql = "";

            sql = "select * from (select * from MT_ELECREALTIMEDATA order by ELECREALTIMEDATA_time desc) where elecrealtimedata_sbid = '28'and rownum=1";    //查询表名为“MT_PLCSB_JHYSJ”的所有内容

            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            //遍历
            while (result.next()) {
                //遍历数据
                Log.d("DataBase", "正在遍历数据");
                //数据结果对象
                DataResult dr = new DataResult();
                //参数赋值

                List<String> list = dr.getStrList();
                list.add("A相电压");
                list.add("B相电压");
                list.add("C相电压");
                list.add("A相电流");
                list.add("B相电流");
                list.add("C相电流");
                list.add("A相功率");
                list.add("B相功率");
                list.add("C相功率");
                list.add("A相功率因数");
                list.add("B相功率因数");
                list.add("C相功率因数");
                list.add("A相累计电能值");
                list.add("B相累计电能值");
                list.add("C相累计电能值");
                list.add("总累计电能值");
                dr.setStrList(list);

                //添加到结果列表
                drList.add(dr);
            }




            if(position==0){
                sql = "select * from (select * from MT_ELECREALTIMEDATA order by ELECREALTIMEDATA_time desc) where elecrealtimedata_sbid = '28'and rownum=1";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==1){
                sql = "select * from (select * from MT_ELECREALTIMEDATA order by ELECREALTIMEDATA_time desc) where elecrealtimedata_sbid = '34'and rownum=1";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            pre = con.prepareStatement(sql);
            result = pre.executeQuery();
            //遍历
            while (result.next()){
                //遍历数据
                Log.d("DataBase","正在遍历数据");
                //数据结果对象
                //DataResult dr = new DataResult();
                //参数赋值
                if(position==0){
                    List<String> list = drList.get(0).getStrList();
                    list.add(result.getString("ELECREALTIMEDATA_UA"));
                    list.add(result.getString("ELECREALTIMEDATA_UB"));
                    list.add(result.getString("ELECREALTIMEDATA_UC"));
                    list.add(result.getString("ELECREALTIMEDATA_IA"));
                    list.add(result.getString("ELECREALTIMEDATA_IB"));
                    list.add(result.getString("ELECREALTIMEDATA_IC"));
                    list.add(result.getString("ELECREALTIMEDATA_PA"));
                    list.add(result.getString("ELECREALTIMEDATA_PB"));
                    list.add(result.getString("ELECREALTIMEDATA_PC"));
                    list.add(result.getString("ELECREALTIMEDATA_FA"));
                    list.add(result.getString("ELECREALTIMEDATA_FB"));
                    list.add(result.getString("ELECREALTIMEDATA_FC"));
                    list.add(result.getString("ELECREALTIMEDATA_EA"));
                    list.add(result.getString("ELECREALTIMEDATA_EB"));
                    list.add(result.getString("ELECREALTIMEDATA_EC"));
                    list.add(result.getString("ELECREALTIMEDATA_EALL"));
                    drList.get(0).setStrList(list);
                }else if(position==1){
                    List<String> list = drList.get(0).getStrList();
                    list.add(result.getString("ELECREALTIMEDATA_UA"));
                    list.add(result.getString("ELECREALTIMEDATA_UB"));
                    list.add(result.getString("ELECREALTIMEDATA_UC"));
                    list.add(result.getString("ELECREALTIMEDATA_IA"));
                    list.add(result.getString("ELECREALTIMEDATA_IB"));
                    list.add(result.getString("ELECREALTIMEDATA_IC"));
                    list.add(result.getString("ELECREALTIMEDATA_PA"));
                    list.add(result.getString("ELECREALTIMEDATA_PB"));
                    list.add(result.getString("ELECREALTIMEDATA_PC"));
                    list.add(result.getString("ELECREALTIMEDATA_FA"));
                    list.add(result.getString("ELECREALTIMEDATA_FB"));
                    list.add(result.getString("ELECREALTIMEDATA_FC"));
                    list.add(result.getString("ELECREALTIMEDATA_EA"));
                    list.add(result.getString("ELECREALTIMEDATA_EB"));
                    list.add(result.getString("ELECREALTIMEDATA_EC"));
                    list.add(result.getString("ELECREALTIMEDATA_EALL"));
                    drList.get(0).setStrList(list);
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                Log.d("DataBase","数据库已关闭");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return drList;
    }

}
