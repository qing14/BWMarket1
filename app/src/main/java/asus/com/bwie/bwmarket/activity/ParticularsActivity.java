package asus.com.bwie.bwmarket.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.ShopParticularsBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class ParticularsActivity extends AppCompatActivity implements Iview {

    private String pid;
    private IpresenterImpl ipresenter;
    private Banner banner;
    private TextView shopxq_name;
    private TextView shopxq_price;
    private TextView shopxq_saleNum;
    private TextView shopxq_xq;
    private WebView webView;
    private ImageView pzmore;
    private ImageView mlmore;
    private ImageView rxmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);

        banner = findViewById(R.id.banner);
        shopxq_name = findViewById(R.id.shopxq_name);
        shopxq_price = findViewById(R.id.shopxq_price);
        shopxq_saleNum = findViewById(R.id.shopxq_saleNum);
        shopxq_xq = findViewById(R.id.shopxq_xq);
        webView = findViewById(R.id.webView);
        rxmore = findViewById(R.id.rxmore);
        mlmore = findViewById(R.id.mlmore);
        pzmore = findViewById(R.id.pzmore);


        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        Toast.makeText(this, pid,Toast.LENGTH_SHORT).show();
        ipresenter = new IpresenterImpl(this);



        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(ParticularsActivity.this).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });

        ipresenter.getXBanner(Apis.ShopXQPath+"?commodityId="+pid,ShopParticularsBean.class);


    }


    @Override
    public void onSuccessData(Object data) {
        List<String> list=new ArrayList<>();
        ShopParticularsBean bean= (ShopParticularsBean) data;
        ShopParticularsBean.ResultBean result = bean.getResult();
        String[] split = result.getPicture().split(",");
        for (int i=0;i<split.length;i++){
            list.add(split[i]);
        }
        banner.setImages(list);
        banner.start();

        shopxq_name.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        shopxq_price.setText("￥："+bean.getResult().getPrice()+".00");
        shopxq_name.setText(bean.getResult().getCommodityName()+"");
        shopxq_saleNum.setText("已售"+bean.getResult().getSaleNum()+"件");
        shopxq_xq.setText(bean.getResult().getDescribe());
        webView.loadDataWithBaseURL(null,bean.getResult().getDetails(),"text/html","utf-8",null);



    }

    @Override
    public void onFailData(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null){
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }

    }
}
