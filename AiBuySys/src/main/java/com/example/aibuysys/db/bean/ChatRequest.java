package com.example.aibuysys.db.bean;

import java.util.List;


//写法不规范，不推荐
//"model":"deepseek-chat","message":[{"role":"user","content"：“你好,你会java吗？”}]}";
//请求DeepSeek的实体类
public class ChatRequest {
    public String model;
    public List<Message> messages;

    public class Message{
        public String role;
        public String content;
    }
}

