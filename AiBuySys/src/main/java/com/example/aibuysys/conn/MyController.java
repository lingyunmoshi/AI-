package com.example.aibuysys.conn;

import com.example.aibuysys.ai.MyDeepSeek1;
import com.example.aibuysys.db.MyDataBase;
import com.example.aibuysys.db.bean.Account;
import com.example.aibuysys.db.bean.ApiResponse;
import com.example.aibuysys.db.bean.ExhibitionMsg;
import com.example.aibuysys.db.bean.Orders;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    //请求数据库账号表函数
    //http://127.0.0.1:8081/getAccount
    @RequestMapping("/getAccount")
    @CrossOrigin(origins = "*") // 解决跨域问题
    @ResponseBody
    public String getAccount() {
        System.out.println("请求账号表数据");
        return MyDataBase.selectAccount();
    }

    //请求数据库展会表函数
    //http://127.0.0.1:8081/getExhibitionMsg
    @RequestMapping("/getExhibitionMsg")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public String getExhibitionMsg() {
        System.out.println("请求展会表数据");
        return MyDataBase.selectExhibitionMsg();
    }

    //请求数据库订单表函数
    //http://127.0.0.1:8081/getOrders
    @RequestMapping("/getOrders")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public String getOrders() {
        System.out.println("请求订单表数据");

        return MyDataBase.selectOrders();
    }

//    //删除数据库账号表函数
//    //http://127.0.0.1:8081/deleteAccount
//    @RequestMapping("/deleteAccount")
//    @ResponseBody
//    public String deleteAccount(String user){
//        System.out.println("删除账号表数据");
//         MyDataBase.deleteAccount(user);
//         return "删除成功";

    //http://127.0.0.1:8081/login?username=""&password=""
    @RestController
    @CrossOrigin(origins = "http://localhost:8081") // 允许来自http://localhost:8081的请求
    public class LoginController {

        @RequestMapping("/login")
        @ResponseBody
        public String login(@RequestParam String username, @RequestParam String password) {
            System.out.println("登录账号：" + username + " 登录密码:" + password);

            // 把得到的账号密码传到数据库进行比对，是否登陆成功
            boolean isLogin = MyDataBase.testLogin(username, password);
            if (isLogin) {
                System.out.println("登陆成功");
                return "success";
            } else {
                System.out.println("登陆失败！");
                return "fail";
            }
        }
    }

    //注册账号函数
    //
    @RequestMapping("/register")
    @CrossOrigin(origins = "*") // 解决跨域问题
    @ResponseBody
    public ApiResponse register(@RequestBody Account account) {
        boolean isRegister = MyDataBase.insertAccount(account);
        if (isRegister) {
            return new ApiResponse(true, "注册成功");
        } else {
            return new ApiResponse(false, "注册失败");
        }
    }

    @PostMapping("/addExhibition")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public ApiResponse addExhibition(@RequestBody ExhibitionMsg exhibitionMsg) {
        try {
            MyDataBase.insertExhibitionMsg(exhibitionMsg);
            return new ApiResponse(true, "展会信息添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "展会信息添加失败：" + e.getMessage());
        }
    }


    @PostMapping("/chat")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public String chat(@RequestBody String userInput) {
        System.out.println("进来AI函数!");
        String msg = MyDeepSeek1.generateReply(userInput);
        //删除账号
        if (msg.contains("DE_ACCOUNT") && msg.contains("_#")) {
            int start = msg.lastIndexOf("DE_ACCOUNT");
            int end = msg.indexOf("#");
            String account = msg.substring(start + 11, end - 1);
            boolean b = MyDataBase.deleteAccount(account);

        }
        //添加账号
        else if (msg.contains("IN_ACCOUNT") && msg.contains("_#")) {
            Account account = getAccount("IN_ACCOUNT", "_#", msg);
            boolean b = MyDataBase.insertAccount(account);
            {
            }
        }
        //修改密码
        else if (msg.contains("UP_ACCOUNT") && msg.contains("_#")) {
            Account account = getAccount("UP_ACCOUNT", "_#", msg);
            String pass = account.getPass();
            String use = account.getUser();
            MyDataBase.updateAccountPass(use, pass);
        }
        //删除展会表
        else if (msg.contains("DE_EXHIBITIONMSG") && msg.contains("_#")) {
            int start = msg.lastIndexOf("DE_EXHIBITIONMSG");
            int end = msg.indexOf("#");
            String id = msg.substring(start + 17, end - 1);
            MyDataBase.deleteExhibitionMsg(id);
        }
        //插入展会表
        else if (msg.contains("IN_EXHIBITIONMSG") && msg.contains("_#")) {
            ExhibitionMsg exhibitionmsg = getExhibitionMsg("IN_EXHIBITIONMSG", "_#", msg);
            MyDataBase.insertExhibitionMsg(exhibitionmsg);
        }
        //更新展会表
        else if (msg.contains("UP_EXHIBITIONMSG") && msg.contains("_#")) {
            int start = msg.lastIndexOf("IN_EXHIBITIONMSG");
            int end = msg.indexOf("#");
            String ExhibitionMsg = msg.substring(start + 16, end - 1);
            String[] s = ExhibitionMsg.split("_");
            ExhibitionMsg exhibitionMsg = new ExhibitionMsg(Integer.parseInt(s[0]),s[1],Integer.parseInt(s[2]),s[3],s[4],s[5]);
            int price = exhibitionMsg.getPrice();
            int id = exhibitionMsg.getId();
            MyDataBase.updateExhibitionMsg(price,id);
        }
        //删除订单表
        else if(msg.contains("DE_ORDER") && msg.contains("_#")){
            int start = msg.lastIndexOf("DE_ORDER");
            int end = msg.indexOf("#");
            String user = msg.substring(start + 9, end - 1);
            MyDataBase.deleteOrders(user);

        }
        //插入订单表
        else if (msg.contains("IN_ORDER") && msg.contains("_#")){
            Orders order = getOrders("IN_ORDER", "_#", msg);
            MyDataBase.insertOrders(order);

        }
        //更新订单表
        else if (msg.contains("UP_ORDER") && msg.contains("_#")){
            Orders order = getOrders("UP_ORDER", "_#", msg);
            MyDataBase.updateOrders(order);

        }


        return msg;
    }

   /* public static void main(String[] args) {
        String msg = "IN_EXHIBITIONMSG9_新展览_200_2025-12-01_2025-12-31_新展览介绍_#";
        int start = msg.lastIndexOf("IN_EXHIBITIONMSG");
        int end = msg.indexOf("#");
        String ExhibitionMsg = msg.substring(start + 16, end - 1);
        String[] s = ExhibitionMsg.split("_");
        ExhibitionMsg exhibitionMsg = new ExhibitionMsg(Integer.parseInt(s[0]),s[1],Integer.parseInt(s[2]),s[3],s[4],s[5]);
        System.out.println(exhibitionMsg);
    }*/

    //封装user对象
    private Account getAccount(String bengin, String end1, String msg) {

        int start = msg.lastIndexOf(bengin);
        int end = msg.indexOf(end1);
        String user = msg.substring(start + 11, end - 1);
        String[] s = user.split("_");
        Account account = new Account(s[0], s[1], s[2], s[3], s[4]);
        return account;
    }

    //封装ExhibitionMsg对象
    private ExhibitionMsg getExhibitionMsg(String bengin, String end1, String msg) {

        int start = msg.lastIndexOf(bengin);
        int end = msg.indexOf(end1);
        String ExhibitionMsg = msg.substring(start + 17, end - 1);
        String[] s = ExhibitionMsg.split("_");
        ExhibitionMsg exhibitionMsg = new ExhibitionMsg(null,s[0],Integer.parseInt(s[1]),s[2],s[3],s[4]);
        return exhibitionMsg;
    }
    //封装订单表
    private Orders getOrders(String bengin, String end1, String msg) {

        int start = msg.lastIndexOf(bengin);
        int end = msg.indexOf(end1);
        String Orders = msg.substring(start + 9, end - 1);
        String[] s = Orders.split("_");
        Orders orders = new Orders(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),s[4],s[5]);
        return orders;
    }
}

