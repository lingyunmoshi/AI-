package com.example.aibuysys.ai;

import java.util.List;

public class DeepSeekResult {

    public String id;
    public String object;
    public int created;
    public String model;
    public String system_fingerprint;
    public List<Choices> choices;

    //��дusage��������Ӱ�����

    //choices ���ݼ�����
    public class Choices {
        public int index;
        //public Object logprobs ȥ���������
        public String  finish_reason;
        public Message message;

    }

    //��Ϣ
    public class Message {
        public String role;
        public String content;
    }
}
