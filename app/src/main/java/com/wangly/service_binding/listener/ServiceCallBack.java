package com.wangly.service_binding.listener;

/**
 * Created by wangly on 2016/8/30.
 */
public interface ServiceCallBack {
    void Succeed(String result);
    void Failed(String result);
}
