package com.example.aibuysys.db;


import com.example.aibuysys.db.bean.Account;
import com.example.aibuysys.db.bean.ExhibitionMsg;
import com.example.aibuysys.db.bean.Orders;
import com.google.gson.Gson;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDataBase {
    //数据库连接对象类
    public static Connection connection;


    //老师的数据库：
   // public static String URL = "jdbc:mysql://yisurds-68298dac7091d8.rds.ysydb1.com:3306/";
    //登录数据库的账号
    //public static String PR = "buydb01";
    //public static String USER = "68298dac7091d83763:mysql123";
    //登录数据库的密码
    //public static String PASS = "qq123456Q";


    //本地数据库：
    //数据库连接名（本地链接）
    //public static String URL = "jdbc:mysql://localhost:3306/";
   public static   String URL = "jdbc:mysql://localhost:3306/webnet?serverTimezone=UTC";
    //登录数据库的账号
  public static String USER = "root";
    //登录数据库的密码
   public static String PASS = "13316285143asd";


    //数据库名字
  //  public static String PR = "webnet";
    //public static String PR = "";


    //JBDC java数据库开发
//    public static void main(String[] args) {
//        openConnection();
////
//    }
//
    //打开数据库的函数
    public static boolean openConnection() {

        boolean res = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立数据库的链接
            connection = DriverManager.getConnection(URL , USER, PASS);
            //判断数据库是否连接成功

            if (!connection.isClosed()) {
                //System.out.println("数据库连接成功");
                System.out.println("connect the database is success");
                System.out.println();

                selectAccount();   //打印用户表
                selectExhibitionMsg();  //打印展会表
                selectOrders();      //打印订单表
//                deleteOrders("张三" );  //删除订单表数据
//                deleteAccount("user9");   //删除用户表数据

//                Account account = new Account();
//                account.setUser("usertest");
//                account.setPass("passtest");
//                account.setSex("男");
//                account.setPhone("10086");
//                account.setName("小王");
//                insertAccount(account);
//
//              insertExhibitionMsg(new ExhibitionMsg(10,"原神展览会",999,"2025-5-27","2025-5-30","圆神启动"));  //添加展会表数据
                //insertOrders(new Orders("老八",10,4,999,"现金支付","2025-5-28"));   //添加订单表数据
                //updateAccountPass("usertest","123456");   //修改账号表账号密码
                //updateAccountMsg("男","1008611","小王","usertest");  //修改账号表数据
                // updateExhibitionMsg(100,9);

            } else {
                res = false;
                System.out.println("connect the database is fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //账号密码判断
    public static boolean testLogin(String user,String pass){
        //查询
        String sql = "select * from Account where user=? and pass=?";
                try{
                    //封装sql语句类
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1,user);
                    ps.setString(2,pass);
                    //查询类
                    ResultSet rs = ps.executeQuery();
                    //如果账号密码判断成功，会执行一次rs.next
                    if(rs.next()){
                        return true;//成功
                    }else {
                        return false;//失败
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
    }

    //查询账号表
    public static String selectAccount() {
        //查询语句
        String sql = "select * from Account";
        try {
            //封装sql语句的类
            Statement ps = connection.prepareStatement(sql);
            //执行操作的类
            ResultSet rs = ps.executeQuery(sql);
            //JSON数据结构解析类
            Gson gson = new Gson();

            //实例化account实体类数组组合
            List<Account> list = new ArrayList<>();
            //读取表里的每一条数据
            while (rs.next()) {
                String user = rs.getString("user");
                String pass = rs.getString("pass");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String name = rs.getString("name");
                //正常打印
                //System.out.println(user + "|" + pass + "|" + sex + "|" + phone + "|" + name);
                Account account = new Account();
                account.user = rs.getString("user");
                account.pass = rs.getString("pass");
                account.sex = rs.getString("sex");
                account.phone = rs.getString("phone");
                account.name = rs.getString("name");
//                String json = gson.toJson(account);
//                System.out.println(json);
//                return json;
                //添加账户数据实体类对象在集合数组里面
                list.add(account);
            }
            System.out.println();
            String json = gson.toJson(list);

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询展会表
    public static <Exhibition> String selectExhibitionMsg() {

        String sql = "select * from ExhibitionMsg";
        try {
            //封装sql语句的类
            PreparedStatement ps = connection.prepareStatement(sql);
            //执行操作的类
            ResultSet rs = ps.executeQuery();
            //json数据结构解析
            Gson  gson = new Gson();
            //展会实体类集合
            List<ExhibitionMsg> list =new ArrayList<>();
            //读取表里的每一条数据
            while (rs.next()) {

                int id = rs.getInt("id");
                String exhibition = rs.getString("exhibition");
                int price = rs.getInt("price");
                String startdate = rs.getString("startdate");
                String enddate = rs.getString("enddate");
                String description = rs.getString("description");

                ExhibitionMsg exhibitionMsg = new ExhibitionMsg();


                exhibitionMsg.exhibition = rs.getString("exhibition");
                exhibitionMsg.price = rs.getInt("price");
                exhibitionMsg.startdate = rs.getString("startdate");
                exhibitionMsg.enddate = rs.getString("enddate");
                exhibitionMsg.description = rs.getString("description");

                //正常打印
                // System.out.println(id + "|" + exhibition + "|" + price + "|" + startdate + "|" + enddate + "|" + description);
                //json打印
//                String json = gson.toJson(exhibitionMsg);
//                System.out.println(json);
                list.add(exhibitionMsg);
            }
            System.out.println();
            String json = gson.toJson(list);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询订单表
    public static String selectOrders() {
        String sql = "select * from Orders";
        try {
            //封装sql语句的类
            PreparedStatement ps = connection.prepareStatement(sql);
            //执行操作的类
            ResultSet rs = ps.executeQuery();
            //读取表里的每一条数据
            List<Orders> list = new ArrayList<>();
            Gson gson = new Gson();
            while (rs.next()) {

                Orders orders = new Orders();
                orders.user = rs.getString("user");
                orders.exhibition_id = rs.getInt("exhibition_id");
                orders.quantity = rs.getInt("quantity");
                orders.money = rs.getInt("money");
                orders.paymethod = rs.getString("paymethod");
                orders.time = rs.getString("time");
                //实例化account实体类数组组合
                list.add(orders);
            }
            System.out.println();
            String json = gson.toJson(list);
            return json;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //删除订单表的函数
    public static void deleteOrders(String user) {
        //删除订单的语句
        String sql = "delete from Orders where user= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
            ps.setString(1, user);
            //执行删除操作
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("delete the orders is success");
            } else {
                System.out.println("delete the orders is fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除账号表的函数
    public static boolean deleteAccount(String user) {
        //删除语句
        String sql = "delete from Account where user = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
            ps.setString(1, user);
            //执行删除操作
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("delete the account is success");
                return true;
            } else {
                System.out.println("delete the account is fail");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //删除展会表的函数
    public static boolean deleteExhibitionMsg(String id) {
        //删除语句
        String sql = "delete from exhibitionmsg where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
            ps.setString(1, id);
            //执行删除操作
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("delete the exhibitionmsg is success");
                return true;
            } else {
                System.out.println("delete the exhibitionmsg is fail");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //添加账号信息表的函数
    public static boolean insertAccount(Account account) {
        if(account.name==null){
            return false;
        }
        //插入表语句
        String sql = "insert into Account value(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
            ps.setString(1, account.user);
            ps.setString(2, account.pass);
            ps.setString(3, account.sex);
            ps.setString(4, account.phone);
            ps.setString(5, account.name);

            //执行语句代码
            int num = ps.executeUpdate();
            if (num == 1) {
                System.out.println("add the account table is success！");
                return true;
            } else {
                System.out.println("add the account table is fail！");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    //添加展会表的函数
//    public static void insertExhibitionMsg(ExhibitionMsg exhibitionMsg ) {
//        //插入表语句
//        String sql = "insert into ExhibitionMsg value(?,?,?,?,?,?)";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
//            ps.setInt(1,exhibitionMsg.id);
//            ps.setString(2, exhibitionMsg.exhibition);
//            ps.setInt(3, exhibitionMsg.price);
//            ps.setString(4, exhibitionMsg.startdate);
//            ps.setString(5, exhibitionMsg.enddate);
//            ps.setString(6, exhibitionMsg.description);
//
//            //执行语句代码
//            int num = ps.executeUpdate();
//            if (num == 1) {
//                System.out.println("add the ExhibitionMSg table is success！");
//            } else {
//                System.out.println("add the ExhibitionMSg table is fail！");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public static void insertExhibitionMsg(ExhibitionMsg exhibitionMsg) {
    // 插入表语句，移除了 id 字段
    String sql = "insert into ExhibitionMsg (exhibition, price, startdate, enddate, description) values (?,?,?,?,?)";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        // 从第二个占位符开始设置值
        ps.setString(1, exhibitionMsg.exhibition);
        ps.setInt(2, exhibitionMsg.price);
        ps.setString(3, exhibitionMsg.startdate);
        ps.setString(4, exhibitionMsg.enddate);
        ps.setString(5, exhibitionMsg.description);

        // 执行语句代码
        int num = ps.executeUpdate();
        if (num == 1) {
            System.out.println("add the ExhibitionMsg table is success！");
        } else {
            System.out.println("add the ExhibitionMsg table is fail！");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    //添加订单表的函数
    public static void insertOrders(Orders orders ) {
        //插入表语句
        String sql = "insert into Orders value(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //1代表sql语句里的第一个问号 ? 把数据传到第一个问号里面
            ps.setString(1, orders.user);
            ps.setInt(2, orders.exhibition_id);
            ps.setInt(3, orders.quantity);
            ps.setInt(4, orders.money);
            ps.setString(5, orders.paymethod);
            ps.setString(6, orders.time);

            //执行语句代码
            int num = ps.executeUpdate();
            if (num == 1) {
                System.out.println("add the Orders table is success！");
            } else {
                System.out.println("add the Orders table is fail！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改用户账户密码信息
    public static void updateAccountPass(String user, String pass) {
        String  sql = "update Account set pass = ? where user = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, user);
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("update the account is success");
            }
        }catch ( Exception e){
            e.printStackTrace();
        }

    }


    //修改用户数据函数
    public static void updateAccountMsg ( String sex, String phone, String name , String user) {

        String  sql = "update Account set sex = ?, phone = ?, name = ? where user = ?";
        try {
            //封装数据
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sex);
            ps.setString(2, phone);
            ps.setString(3, name);
            ps.setString(4, user);
            //执行修改
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("update the account is success");
            }
        }catch (  Exception e){
            e.printStackTrace();
        }
    }

    //修改展会表票价函数
    public static void updateExhibitionMsg ( int price,  int id) {
        String  sql = "update ExhibitionMsg set price = ? where id = ?";
        try {
            //封装数据
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, price);
            ps.setInt(2, id);
            //执行修改
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("update the ExhibitionMsg is success");
            }
        }catch (  Exception e){
            e.printStackTrace();
        }
    }
    //更新订单表的函数
    public static void updateOrders(Orders orders) {
        //删除订单的语句
        String sql = "update orders set exhibition_id=?,quantity=?,money=?,paymethod=?,time=? where user= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            //封装数据

            ps.setInt(1, orders.getExhibition_id());
            ps.setInt(2,orders.getQuantity());
            ps.setInt(3, orders.getMoney());
            ps.setString(4, orders.getPaymethod());
            ps.setString(5, orders.getTime());
            ps.setString(6, orders.getUser());
            //执行修改
            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("update the orders is success");
            } else {
                System.out.println("update the orders is fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}