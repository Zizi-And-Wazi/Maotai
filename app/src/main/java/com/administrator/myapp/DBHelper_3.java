package com.administrator.myapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.administrator.myapp.adapter.MyListViewAdapter2;
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

public class DBHelper_3 extends AsyncTask<Void,Void,List<DataResult>> {


    //类参数
    private Context context;                    //数据界面
    private ListView mListView2;            //界面右边的ListView控件
    private MyListViewAdapter2 adapter2;   //界面右边的ListView控件的数据
    private int position;       //左侧点击列表的位置
    private Handler mHandler;

    //构造方法(赋值)
    public DBHelper_3(Context context, ListView mListView2, MyListViewAdapter2 adapter2, int position, Handler mHandler) {
        this.context = context;
        this.mListView2 = mListView2;
        this.adapter2 = adapter2;
        this.position = position;
        this.mHandler = mHandler;
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

        String temp="";
        String[] t=new String[2];
        //循环遍历查询的数据
        for(int i=0;i<16;i++){
            if(i>list.size()/3-1){
                adapter2.updataView(i,mListView2," "," "," "," ");
            }else {
                int count=0;
                for(int ia=0;ia<list.get(i * 2 + list.size() / 3).length();ia++){
                    if(list.get(i * 2 + list.size() / 3).charAt(ia)=='.'){
                        count++;
                    }
                }
                int countT=count;
                for (int n = 0; n < list.get(i * 2 + list.size() / 3).length(); n++) {
                    if (!Character.isDigit(list.get(i * 2 + list.size() / 3).charAt(n)) && list.get(i * 2 + list.size() / 3).charAt(n) != '～') {
                        int a = i * 2 + list.size() / 3;
                        if(countT!=0){
                            countT--;
                        }else{
                            if (n == 0) {
                                temp = "0";
                            }else {
                                temp = list.get(a).substring(0, n+count);
                                t = temp.split("～");
                                break;
                            }
                        }
                    }
                }
                if(count!=0){
                    temp = "false";
                }else if (Float.parseFloat(t[1]) >= Float.parseFloat(list.get(i)) && Float.parseFloat(t[0]) <= Float.parseFloat(list.get(i))) {
                    temp = "true";
                } else {
                    temp = "false";
                }
                //更新数据
                adapter2.updataView(i, mListView2, list.get(i), list.get(i * 2 + list.size() / 3), list.get(i * 2 + list.size() / 3 + 1), temp);
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
                sql = "select * from (select * from MT_PLCSB_CNG1 order by cng1_time desc) where rownum=1";    //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==1){
                sql = "select * from (select * from MT_PLCSB_CNG2 order by cng2_time desc) where rownum=1";    //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==2){
                sql = "select * from (select * from MT_PLCSB_CNG3 order by cng3_time desc) where rownum=1";    //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==3){
                sql = "select * from (select * from MT_PLCSB_CNG4 order by cng4_time desc) where rownum=1";    //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            try {
                pre = con.prepareStatement(sql);
                result = pre.executeQuery();
            }catch (Exception e){
                Message msg = new Message();
                msg.what = 2;  //消息(一个整型值)
                mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
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
                DataResult dr = new DataResult();
                //参数赋值
                if(position==0){
                    List<String> list = dr.getStrList();
                    Log.d("DataBase",result.getString("JHYSJ_GYWD"));
                    list.add(result.getString("CNG1_JQYL"));
                    list.add(result.getString("CNG1_PQYL1"));
                    list.add(result.getString("CNG1_PQYL2"));
                    list.add(result.getString("CNG1_PQYL3"));
                    list.add(result.getString("CNG1_RHYYL"));
                    list.add(result.getString("CNG1_LQSYL"));
                    list.add(result.getString("CNG1_ZJDL"));
                    list.add(result.getString("CNG1_MPLHWD"));
                    list.add(result.getString("CNG1_RHYLQWD"));
                    list.add(result.getString("CNG1_RHYLHWD"));
                    list.add(result.getString("CNG1_DJZCWD1"));
                    list.add(result.getString("CNG1_DJZCWD2"));
                    list.add(result.getString("CNG1_DJDZWD1"));
                    list.add(result.getString("CNG1_DJDZWD2"));
                    list.add(result.getString("CNG1_DJDZWD3"));
                    list.add(result.getString("CNG1_GYFWD"));
                    dr.setStrList(list);

                }

                else if(position==1){
                    List<String> list = dr.getStrList();
                    Log.d("DataBase",result.getString("JHLCFJ01_LCFJZS"));
                    list.add(result.getString("CNG2_JQYL"));
                    list.add(result.getString("CNG2_PQYL1"));
                    list.add(result.getString("CNG2_PQYL2"));
                    list.add(result.getString("CNG2_PQYL3"));
                    list.add(result.getString("CNG2_RHYYL"));
                    list.add(result.getString("CNG2_LQSYL"));
                    list.add(result.getString("CNG2_ZJDL"));
                    list.add(result.getString("CNG2_MPLHWD"));
                    list.add(result.getString("CNG2_RHYLQWD"));
                    list.add(result.getString("CNG2_RHYLHWD"));
                    list.add(result.getString("CNG2_DJZCWD1"));
                    list.add(result.getString("CNG2_DJZCWD2"));
                    list.add(result.getString("CNG2_DJDZWD1"));
                    list.add(result.getString("CNG2_DJDZWD2"));
                    list.add(result.getString("CNG2_DJDZWD3"));
                    list.add(result.getString("CNG2_GYFWD"));
                    dr.setStrList(list);
                }
                else if(position==2){
                    List<String> list = dr.getStrList();
                    Log.d("DataBase",result.getString("JHLCFJ02_LCFJZS"));
                    list.add(result.getString("CNG3_JQYL"));
                    list.add(result.getString("CNG3_PQYL1"));
                    list.add(result.getString("CNG3_PQYL2"));
                    list.add(result.getString("CNG3_PQYL3"));
                    list.add(result.getString("CNG3_RHYYL"));
                    list.add(result.getString("CNG3_LQSYL"));
                    list.add(result.getString("CNG3_ZJDL"));
                    list.add(result.getString("CNG3_MPLHWD"));
                    list.add(result.getString("CNG3_RHYLQWD"));
                    list.add(result.getString("CNG3_RHYLHWD"));
                    list.add(result.getString("CNG3_DJZCWD1"));
                    list.add(result.getString("CNG3_DJZCWD2"));
                    list.add(result.getString("CNG3_DJDZWD1"));
                    list.add(result.getString("CNG3_DJDZWD2"));
                    list.add(result.getString("CNG3_DJDZWD3"));
                    list.add(result.getString("CNG3_GYFWD"));
                    dr.setStrList(list);
                }
                else if(position==3){
                    List<String> list = dr.getStrList();
                    Log.d("DataBase",result.getString("JHLCFJ01_LCFJZS"));
                    list.add(result.getString("CNG4_JQYL"));
                    list.add(result.getString("CNG4_PQYL1"));
                    list.add(result.getString("CNG4_PQYL2"));
                    list.add(result.getString("CNG4_PQYL3"));
                    list.add(result.getString("CNG4_RHYYL"));
                    list.add(result.getString("CNG4_LQSYL"));
                    list.add(result.getString("CNG4_ZJDL"));
                    list.add(result.getString("CNG4_MPLHWD"));
                    list.add(result.getString("CNG4_RHYLQWD"));
                    list.add(result.getString("CNG4_RHYLHWD"));
                    list.add(result.getString("CNG4_DJZCWD1"));
                    list.add(result.getString("CNG4_DJZCWD2"));
                    list.add(result.getString("CNG4_DJDZWD1"));
                    list.add(result.getString("CNG4_DJDZWD2"));
                    list.add(result.getString("CNG4_DJDZWD3"));
                    list.add(result.getString("CNG4_GYFWD"));
                    dr.setStrList(list);
                }
                //添加到结果列表
                drList.add(dr);
            }

            if(position==0){
                sql = "SELECT * FROM MT_PLCSBDESIGN where plcsbdesign_plcsbid = 'CNGPLC1'";   //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==1){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_plcsbid = 'CNGPLC2'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==2){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_plcsbid = 'CNGPLC3'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }else if(position==3){
                sql = "select * from MT_PLCSBDESIGN where plcsbdesign_plcsbid = 'CNGPLC4'";     //查询表名为“MT_PLCSB_JHYSJ”的所有内容
            }
            try {
                pre = con.prepareStatement(sql);
                result = pre.executeQuery();
            }catch (Exception e){
                Message msg = new Message();
                msg.what = 2;  //消息(一个整型值)
                mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
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
                if(position==0){
                    List<String> list = drList.get(0).getStrList();
                    Log.d("DataBase",result.getString("PLCSBDESIGN_NAME"));
                    list.add(result.getString("PLCSBDESIGN_LOWLIMIT")+"～"+result.getString("PLCSBDESIGN_HIGHLIMIT")+result.getString("PLCSBDESIGN_UNIT"));
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    drList.get(0).setStrList(list);
                }else if(position==1){
                    List<String> list = drList.get(0).getStrList();
                    list.add(result.getString("PLCSBDESIGN_LOWLIMIT")+"～"+result.getString("PLCSBDESIGN_HIGHLIMIT")+result.getString("PLCSBDESIGN_UNIT"));
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    drList.get(0).setStrList(list);
                }
                else if(position==2){
                    List<String> list = drList.get(0).getStrList();
                    list.add(result.getString("PLCSBDESIGN_LOWLIMIT")+"～"+result.getString("PLCSBDESIGN_HIGHLIMIT")+result.getString("PLCSBDESIGN_UNIT"));
                    list.add(result.getString("PLCSBDESIGN_NAME"));
                    drList.get(0).setStrList(list);
                }
                else if(position==3){
                    List<String> list = drList.get(0).getStrList();
                    list.add(result.getString("PLCSBDESIGN_LOWLIMIT")+"～"+result.getString("PLCSBDESIGN_HIGHLIMIT")+result.getString("PLCSBDESIGN_UNIT"));
                    list.add(result.getString("PLCSBDESIGN_NAME"));
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

