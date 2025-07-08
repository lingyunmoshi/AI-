package com.example.aibuysys.db.bean;

import java.util.List;

//AI语言模型返回的数据实体类结构
public class DeepSeekResult {
    public String id;
    public String object;
    public int created;
    public String modle;
    public String system_fingerprint;
    public List<Choices> choices;

    //不写usage变量，不会影响解析，只是不再解析这个数据

    //Choices数组集合
    public class Choices{
        public int index;
        //去掉这个参数
        //public Objects logprobs;
        public String finish_reason;
        public Message message;
    }

    //信息
    public class Message{
        public String role;
        public String content;
    }
}
