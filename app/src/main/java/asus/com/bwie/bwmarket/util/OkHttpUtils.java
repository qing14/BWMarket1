package asus.com.bwie.bwmarket.util;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.BaseApis;
import asus.com.bwie.bwmarket.bean.CircleListBean;
import asus.com.bwie.bwmarket.callback.MyCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OkHttpUtils {

    private final String BASE_URL = "http://172.17.8.100/small/";
    public static OkHttpUtils okHttpUtils;
    public OkHttpClient mClient;
    public Handler handler=new Handler(Looper.getMainLooper());
    private BaseApis baseApis;

    public static OkHttpUtils getOkHttpUtils() {
        if (okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if (null==okHttpUtils){
                    okHttpUtils=new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }
    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        baseApis = retrofit.create(BaseApis.class);


    }

    //登录注册
    public void postEneuque(String urlData, Map<String,String> map, final Class clas, final MyCallBack myCallBack){
        FormBody.Builder builder=new FormBody.Builder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .url(urlData)
                .post(body)
                .build();
        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Object o = new Gson().fromJson(json, clas);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onSuccess(o);
                    }
                });
            }


        });
    }
    public Map<String,RequestBody> generateRequestBody(Map<String,String> requestDataMap){
        Map<String,RequestBody> requestBodyMap=new HashMap<>();

        for (String key:requestDataMap.keySet()){
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form_data"),
                   requestDataMap.get(key) ==null ? "" :requestDataMap.get(key) );
            requestBodyMap.put(key,requestBody);
        }
        return requestBodyMap;
    }

    /**
     * get请求
     * @param urlData
     *圈子列表
     * @return
     */
    public OkHttpUtils getCircle(String urlData,int page,int count){
        baseApis.getcircle(urlData,page,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return okHttpUtils;
    }

    /**
     * 首页的XBanner轮播
     * @param urlData
     * @param listener
     * @return
     */

    public OkHttpUtils getXBanners(String urlData, HttpListener listener){
        baseApis.getXBanner(urlData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return okHttpUtils;
    }

    /**
     * post清单
     *
     */
    public OkHttpUtils postFormBody(String url,Map<String,RequestBody> map){
        if (map==null){
            map=new HashMap<>();
        }
        baseApis.postFormBody(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return okHttpUtils;
    }
    /**
     * 普通post请求
     */
    public OkHttpUtils post(String url,Map<String,String> map){
        if (map==null){
            map=new HashMap<>();

        }
        baseApis.post(url,map)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return okHttpUtils;
    }



    /**
     * 观察者
     */
    private Observer observer=new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }
        @Override
        public void onError(Throwable e) {
            if (listener !=null){
                listener.onFail(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (listener!=null){
                    listener.onSuccess(data);
                }
            }catch (IOException e){
                e.printStackTrace();
                if (listener!=null){
                    listener.onFail(e.getMessage());
                }
        }


        }
    };

    private HttpListener listener;

    public void result(HttpListener listener){
        this.listener=listener;
    }

    public interface HttpListener{
        void onSuccess(String data);

        void onFail(String error);
    }





}
