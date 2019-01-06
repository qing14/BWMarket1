package asus.com.bwie.bwmarket.presenter;

import java.util.Map;

import asus.com.bwie.bwmarket.callback.MyCallBack;

public interface Ipresenter {

    //登录注册
    void startRequest(String urlData, Map<String,String> map, Class clazz);
    //圈子列表
    void getCircle(String urlData,int page,int count, Class clazz, int i );
    //首页轮播
    void getXBanner(String urlData,Class clazz);
    //get
    void get(String urlData,Class clazz);
}
