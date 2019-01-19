package asus.com.bwie.bwmarket;



import android.provider.SyncStateContract;

import java.util.Map;

import asus.com.bwie.bwmarket.bean.CircleListBean;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Completable;
import rx.Observable;

public interface BaseApis<T> {
    @GET
    Observable<ResponseBody> get(@Url String url);

    @GET
    Observable<ResponseBody> getXBanner(@Url String url);

    @GET
    Observable<ResponseBody> getcircle(@Url String url,@Query("page")int page,@Query("count") int count);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE
    Observable<ResponseBody> del(@Url String url,@QueryMap Map<String,String> map);

    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String,String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String,String> map);

    @Multipart
    @POST
    Observable<ResponseBody> postFormBody(@Url String url, @PartMap Map<String, RequestBody> requestBodyMap);



}
