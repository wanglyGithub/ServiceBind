package com.wangly.service_binding.net;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangly on 2016/8/30.
 */
public class HttpUtils {
    public static String get(String path, Handler handler){
        try {
            if(path!=null){
                URL url=new URL(path);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(1500);
                conn.setRequestMethod("GET");
                int code=conn.getResponseCode();
                if(code==200){
                    InputStream is=conn.getInputStream();
                    return fromInPutToString(is);
                }
            }
        } catch (MalformedURLException e) {
            handler.obtainMessage(AsyncHttpClient.NET_ERROR, "Url 地址 有误！").sendToTarget();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            handler.obtainMessage(AsyncHttpClient.NET_ERROR, "网络连接异常").sendToTarget();
        }
        return null;
    }

    /**
     * 把输入流的内容 转化成 字符串
     * @param is
     * @return
     */
    public static String fromInPutToString(InputStream is){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer))!=-1){
                byteArrayOutputStream.write(buffer, 0, len);
            }
            is.close();
            byteArrayOutputStream.close();
            byte[] resultByte = byteArrayOutputStream.toByteArray();
            String temp = new String(resultByte);
            return temp;

        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

}
