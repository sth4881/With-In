package com.example.graduationproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class OCR {
    public static void main(String[] args) {
        String apiURL = "https://0e5de5a9aebe4da1bcd5ef84a78605f0.apigw.ntruss.com/custom/v1/6604/ebe336381e3a156e85375e32f99ee0a86a480f2747219998eff226d9234ee616/infer";
        String secretKey = "YnBEZ2dMTVljTkxrQ0FmS0Z2bll2SXdoenF4Z01KTXM=";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "ocr");
            // Object Storage의 URL을 불러와서 OCR 적용
            // image.put("url", "https://kr.object.ncloudstorage.com/bitbucket/sample.jpg"); // image should be public, otherwise, should use data
            
            // 저장소 내부의 파일을 불러와서 OCR 적용
            FileInputStream inputStream = new FileInputStream("/Users/SJH/eclipse-workspace/GraduationProject/src/com/ocr/sample.jpg");
            byte[] buffer = new byte[inputStream.available()];
            image.put("data", buffer);
            inputStream.read(buffer);
            inputStream.close();
            
            // 다수의 이미지를 처리
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            
            // JSON Request 전송
            String postParams = json.toString();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            // JSON Response 처리
            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            // JSON Response 데이터 가공
            String jsonString = br.readLine();
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONObject jsonImages = jsonObj.getJSONArray("images").getJSONObject(0);
            JSONArray jsonFields = jsonImages.getJSONArray("fields");
            for(int i=0; i<jsonFields.length(); i++) {
            	JSONObject obj = jsonFields.getJSONObject(i);
            	String name = obj.getString("name");
            	String inferText = obj.getString("inferText");
            	System.out.println(name+" : "+inferText);
            }
            br.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
