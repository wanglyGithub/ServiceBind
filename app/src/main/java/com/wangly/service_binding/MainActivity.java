package com.wangly.service_binding;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wangly.service_binding.listener.ServiceCallBack;
import com.wangly.service_binding.service.BindService;

public class MainActivity extends AppCompatActivity {
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setText("Service 服务绑定的使用！");
    }


    public void bindService(View view) {
        Log.d("MainActivity", "绑定Service 服务");

        Intent intent = new Intent(this, BindService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }


    public void unBindService(View view) {
        Log.d("MainActivity", "解除绑定");

        unbindService(connection);
    }


    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "服务连接");

            BindService.MyBinder binder = (BindService.MyBinder) iBinder;
            BindService service = binder.getBindService();
            service.callServiceMethod();

            service.getServiceData();
            service.setServiceCallBack(new ServiceCallBack() {
                @Override
                public void Succeed(String result) {
                    Log.d("MainActivity", "获取数据成功");
                    Log.d("MainActivity",result);
                }

                @Override
                public void Failed(String result) {
                    Log.d("MainActivity", "获取数据失败");
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "断开连接");
        }
    };
}
