package asus.com.bwie.bwmarket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.adapter.MyCircleAdapter;
import asus.com.bwie.bwmarket.bean.AddAdressBean;
import asus.com.bwie.bwmarket.bean.MyCircleBean;
import asus.com.bwie.bwmarket.callback.MyCallBack;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class MyCircleActivity extends AppCompatActivity implements Iview {

    private ImageView delcircle;
    private RecyclerView circleRecycle;
    private TextView evEdit;
    private IpresenterImpl ipresenter;
    private MyCircleAdapter myCircleAdapter;
    private MyCircleBean myCircleBean;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        circleRecycle = findViewById(R.id.iv_circle_recycle);
        ipresenter = new IpresenterImpl(this);
        getCircleData();
        myCircleAdapter = new MyCircleAdapter(this);
        circleRecycle.setAdapter(myCircleAdapter);
        circleRecycle.addItemDecoration(new SpaceItemDecoration(20));
        myCircleAdapter.setMyCircleListenter(new MyCircleAdapter.MyCircleListener() {
            @Override
            public void onClick(int position) {
                id = myCircleBean.getResult().get(position).getId();
                myCircleAdapter.delList(position);
                getDelMyCircle();
            }
        });



    }

    private void getDelMyCircle() {
        Map<String,String> map=new HashMap<>();
        map.put("circleId",id+"");
        ipresenter.delete(Apis.DelMyCirclePath,map,AddAdressBean.class);


    }

    private void getCircleData() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        circleRecycle.setLayoutManager(linearLayoutManager);
        ipresenter.getCircle(Apis.GetMyCirclePath,1,10,MyCircleBean.class);


    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof MyCircleBean){
            myCircleBean = (MyCircleBean) data;
            myCircleAdapter.setList(myCircleBean.getResult());


        }else if (data instanceof AddAdressBean){
            AddAdressBean addAdressBean= (AddAdressBean) data;

            myCircleAdapter.notifyDataSetChanged();
            }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
