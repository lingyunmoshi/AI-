package com.example.aibuysys.ai;

import com.example.aibuysys.ai.ChatRequest;
import com.example.aibuysys.ai.DeepSeekResult;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// AI 聊天接口调用服务
public class MyDeepSeek1 {

    // DeepSeek API 地址和密钥
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-9523424f9b464f95aed1593c1a465864";

    private static ChatRequest chatRequest;

    // 静态初始化代码块
    static {
        chatRequest = new ChatRequest();
        chatRequest.model = "deepseek-chat";
        chatRequest.messages = new ArrayList<>();
    }

    public static void main(String[] args) {
        String res = generateReply("你好");
        System.out.printf(res);
    }

    // AI 回复生成方法
    public static String generateReply(String userInput) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ChatRequest.Message msg = chatRequest.new Message();
            msg.role = "user";
            msg.content = userInput;
            chatRequest.messages.add(msg);

            Gson gson = new Gson();
            String json = gson.toJson(chatRequest);

            byte[] bodyBytes = json.getBytes(StandardCharsets.UTF_8);
            OutputStream os = connection.getOutputStream();
            os.write(bodyBytes);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                DeepSeekResult deepSeekResult = gson.fromJson(response.toString(), DeepSeekResult.class);
                String aiMessage = deepSeekResult.choices.get(0).message.content;

                ChatRequest.Message aiMsg = chatRequest.new Message();
                aiMsg.role = "assistant";
                aiMsg.content = aiMessage;
                chatRequest.messages.add(aiMsg);
                 System.out.println("DK:"+aiMessage);
                return aiMessage;
            } else {
                return "连接 AI 服务失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 服务暂不可用，请稍后再试";
        }
    }
}