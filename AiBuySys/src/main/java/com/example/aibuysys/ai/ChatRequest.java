package com.example.aibuysys.ai;
import java.util.List;

public class ChatRequest {

    public String model;
    public List<Message> messages;
    public class Message{
        public String role;
        public String content;
    }
}
