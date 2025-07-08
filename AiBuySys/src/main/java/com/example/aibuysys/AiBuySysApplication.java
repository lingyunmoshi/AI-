package com.example.aibuysys;

import com.example.aibuysys.AiData.AIMsg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.aibuysys.AiData.AIMsg.AI_M1;
import static com.example.aibuysys.ai.MyDeepSeek1.generateReply;
import static com.example.aibuysys.db.MyDataBase.openConnection;

//项目运行的启动类
@SpringBootApplication
public class AiBuySysApplication {

    //项目主函数
    //运行当前main函数，相当于把服务器运行了
    // {Spring Boot项目自带一个小型服务器}
    public static void main(String[] args) {

        SpringApplication.run(AiBuySysApplication.class, args);
        openConnection();
        generateReply(AI_M1);
        System.out.println("hello world1");
        System.out.println("hello world2");
    }

}
