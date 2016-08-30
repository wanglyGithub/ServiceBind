package com.wangly.service_binding.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.wangly.service_binding.listener.ResultCallBack;

/**
 * Created by wangly on 2016/8/30.
 */
public class AsyncHttpClient {
    public static final int GET_SUCCESS = 102;
    public static final int GET_FAILED= 103;
    public static final int NET_ERROR = 104;
    public String url;
    public Context mContext;
    public ResultCallBack callBack;

    public AsyncHttpClient(String url,Context context,ResultCallBack listener){
        this.url = url;
        this.mContext = context;
        this.callBack = listener;
    }
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            String result=(String) msg.obj;
            switch (msg.what) {
                case GET_SUCCESS:
                    if(result!=null){
                        callBack.Succeed(result);
                    }
                    break;
                case GET_FAILED:
                    if(result!=null){
                        callBack.Failed(result);
                    }
                    break;

                case NET_ERROR:
                    if(result!=null){
                        callBack.Failed(result);
                    }
                    break;

                default:
                    break;
            }
        };
    };
    public void excute(){
        new Thread(new Task()).start();

    }

    class Task implements Runnable{

        @Override
        public void run() {
            if(!NetStateUtils.isNetworkAvailable(mContext)){
                handler.obtainMessage(NET_ERROR,"µ±Ç°ÎÞÍøÂç").sendToTarget();
                return;
            }
            String result=HttpUtils.get(url,handler);
            if(result!=null){
                handler.obtainMessage(GET_SUCCESS, result).sendToTarget();
            }else {
                handler.obtainMessage(GET_FAILED).sendToTarget();
            }
        }
    }
}
