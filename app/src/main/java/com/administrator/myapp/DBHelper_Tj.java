package com.administrator.myapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.administrator.myapp.adapter.MyListViewAdapter2;
import com.administrator.myapp.adapter.MyListViewAdapter2_1;
import com.administrator.myapp.entity.DataResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class DBHelper_Tj extends AsyncTask<Void,Void,List<DataResult>> {


    //类参数
    private Context context;                    //数据界面
    private ListView mListView2;            //界面右边的ListView控件
    private MyListViewAdapter2_1 adapter4;   //界面右边的ListView控件的数据
    private int position;       //左侧点击列表的位置

    //构造方法(赋值)
    public DBHelper_Tj(Context context, ListView mListView2, MyListViewAdapter2_1 adapter4, int position){
        this.context = context;
        this.mListView2 = mListView2;
        this.adapter4 = adapter4;
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
        if(drList.size()==0){
            return;
        }
        List<String> list = drList.get(0).getStrList();
        //循环遍历查询的数据
        for(int i=0;i<17;i++){
            //更新数据
            if(i<drList.size()){
                adapter4.updataView(i, mListView2, drList.get(i).getStrList().get(0));
            }else{
                adapter4.updataView(i, mListView2, "");
            }
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
            Log.d("DataBase","开始连接数据库MT");      //第一个参数是标签，第二个参数是信息
            String url = "jdbc:oracle:thin:@120.76.212.185:1521:Ptecorcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
            String user = "MT";// 用户名,系统默认的账户名
            String password = "MT";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            Log.d("DataBase","连接数据库MT成功");
            String sql="";
            if(position==0){
                sql = "SELECT * FROM MT_PLCSBDESIGN  where plcsbdesign_bimid='jhplcysj'";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==1){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_bimid = 'jhplclcfj01'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==2){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_bimid = 'jhplclcfj02'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            try {
                pre = con.prepareStatement(sql);
                result = pre.executeQuery();
            }catch (Exception e){
                DataResult dr = new DataResult();
                List<String> list = dr.getStrList();
                dr.setStrList(list);
                drList.add(dr);
                return drList;
            }
            //遍历
            while (result.next()){
                //遍历数据
                Log.d("DataBase","正在遍历数据");
                //数据结果对象
                //DataResult dr = new DataResult();
                //参数赋值
                DataResult dr = new DataResult();
                if(position==0){
                    List<String> list = dr.getStrList();
                    Log.d("DataBase",result.getString("PLCSBDESIGN_NAME"));
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    dr.setStrList(list);
                }else if(position==1){
                    List<String> list = dr.getStrList();
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    dr.setStrList(list);
                }
                else if(position==2){
                    List<String> list = dr.getStrList();
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    dr.setStrList(list);
                }
                //添加到结果列表
                drList.add(dr);
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

