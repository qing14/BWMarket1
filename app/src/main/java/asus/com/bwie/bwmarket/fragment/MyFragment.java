package asus.com.bwie.bwmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.activity.MyAddressActivity;
import asus.com.bwie.bwmarket.activity.MyCircleActivity;
import asus.com.bwie.bwmarket.activity.MyFootActivity;
import asus.com.bwie.bwmarket.activity.MyInfromationActivity;
import asus.com.bwie.bwmarket.activity.MyMoneyActivity;
import asus.com.bwie.bwmarket.bean.CZBean;
import asus.com.bwie.bwmarket.bean.SelByIdBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class MyFragment extends Fragment implements View.OnClickListener ,Iview {

    private TextView my_information;
    private TextView my_circle;
    private TextView my_foot;
    private TextView my_money;
    private TextView my_address;
    private IpresenterImpl ipresenter;
    private ImageView imagetx;
    private TextView mynickName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_layout, container, false);
        my_information = view.findViewById(R.id.my_Information);
        my_circle = view.findViewById(R.id.my_Circle);
        my_foot = view.findViewById(R.id.my_Foot);
        my_money = view.findViewById(R.id.my_Money);
        my_address = view.findViewById(R.id.my_Address);
        imagetx = view.findViewById(R.id.imagetx);
        mynickName = view.findViewById(R.id.my_NickName);
        my_information.setOnClickListener(this);
        my_circle.setOnClickListener(this);
        my_foot.setOnClickListener(this);
        my_money.setOnClickListener(this);
        my_address.setOnClickListener(this);
        EventBus.getDefault().register(this);
        IpresenterImpl ipresenter=new IpresenterImpl(this);
        ipresenter.get(Apis.GetUserByIdPath,SelByIdBean.class);

        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onCZBean(CZBean bean){
        if (bean.getObject().equals("image")){
            imagetx.setImageResource(Integer.parseInt(bean.getObject()+""));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_Information:
                Intent intent=new Intent(getActivity(),MyInfromationActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"已进入个人资料",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_Circle:
                Intent intentcircle=new Intent(getActivity(),MyCircleActivity.class);
                startActivity(intentcircle);
                Toast.makeText(getActivity(),"已进入我的圈子",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_Foot:
                Intent intentfoot=new Intent(getActivity(),MyFootActivity.class);
                startActivity(intentfoot);
                Toast.makeText(getActivity(),"已进入我的足迹",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_Money:
                Intent intentmoney=new Intent(getActivity(),MyMoneyActivity.class);
                startActivity(intentmoney);
                Toast.makeText(getActivity(),"已进入我的钱包",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_Address:
                Intent intentAddress=new Intent(getActivity(),MyAddressActivity.class);
                startActivity(intentAddress);
                Toast.makeText(getActivity(),"已进入我的收货地址",Toast.LENGTH_SHORT).show();
                break;




        }
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof SelByIdBean){
            SelByIdBean selByIdBean= (SelByIdBean) data;
            mynickName.setText(selByIdBean.getResult().getNickName());
            notify();
        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
