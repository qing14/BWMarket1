package asus.com.bwie.bwmarket.presenter;

import java.util.Map;

import asus.com.bwie.bwmarket.bean.CircleListBean;
import asus.com.bwie.bwmarket.callback.MyCallBack;
import asus.com.bwie.bwmarket.model.Imodelmpl;
import asus.com.bwie.bwmarket.view.Iview;

public class IpresenterImpl implements Ipresenter {
    private Imodelmpl imodelmpl;
    private Iview iview;

    public IpresenterImpl(Iview iview) {
        imodelmpl=new Imodelmpl();
        this.iview = iview;
    }

    //登录注册
    @Override
    public void startRequest(String urlData, Map<String, String> map, Class clazz) {
        imodelmpl.startRequestData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });

    }
    //圈子列表
    @Override
    public void getCircle(String urlData,int page,int count, Class clazz, int i) {
        imodelmpl.getCircleData(urlData,page,count, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }
    //XBanner
    @Override
    public void getXBanner(String urlData,Class clazz) {
        imodelmpl.getXBannerData(urlData,clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }
    //get
    @Override
    public void get(String urlData,Class clazz) {
        imodelmpl.get(urlData,clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }

    @Override
    public void post(String urlData, Map<String, String> map, Class clazz) {
        imodelmpl.postData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }


    public void detach(){
        imodelmpl=null;
        iview=null;
    }
}
