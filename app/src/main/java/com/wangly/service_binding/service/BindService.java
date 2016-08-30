package com.wangly.service_binding.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wangly.service_binding.listener.ResultCallBack;
import com.wangly.service_binding.listener.ServiceCallBack;
import com.wangly.service_binding.net.AsyncHttpClient;

/**
 * Created by wangly on 2016/8/30.
 */
public class BindService extends Service implements ResultCallBack {

    private  Intent intent;
    private ServiceCallBack callBack;

    public void setServiceCallBack(ServiceCallBack callBack){
        this.callBack = callBack;
    }
    @Nullable
    @Override
    /***
     * 如果说你Activity 中的 ServiceConnection 内部类中的
     * onServiceConnected 方法没有连接成功的话，应该是你的
     * Binding 没有成功  （onBind(Intent intent)）
     */
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        intent = intent;
        return super.onStartCommand(intent, flags, startId);
        //该方法每次创建都会执行

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //该方法只在创建的时候执行一次
    }


    /**
     * 调用Service 服务中的方法
     */
    public void callServiceMethod(){
        Log.d("BindService", "调用Service 服务中的方法");
    }

    /**
     * 获取在Service 中操作的最终数据
     * 使用Interface 回调
     */
    public void getServiceData(){
        AsyncHttpClient client = new AsyncHttpClient("https://www.baidu.com/",this,this);
        client.excute();

        Log.d("BindService", "获取Service 操作的数据");
    }




    private MyBinder binder = new MyBinder();

    @Override
    public void Succeed(String result) {
        if (null != callBack ){
            callBack.Succeed(result);
        }
    }

    @Override
    public void Failed(String result) {
        if (null != callBack ){
            callBack.Failed(result);
        }
    }

    public class  MyBinder extends Binder{
        public BindService getBindService(){
            return BindService.this;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnbind(intent);
    }

}
