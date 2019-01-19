package asus.com.bwie.bwmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.activity.MyAddressActivity;
import asus.com.bwie.bwmarket.adapter.ShangPinAdapter;
import asus.com.bwie.bwmarket.bean.ShopCarBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.JiaJianView;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class ShopCarFragment extends Fragment implements Iview{

    private RecyclerView shopCarRecyclerView;
    private CheckBox shop_checkbox;
    private TextView shop_price;
    private TextView sum_price_txt;
    private ShangPinAdapter shangPinAdapter;
    private ShopCarBean shopCarBean;
    private List<ShopCarBean.ResultBean> listAll;
    private IpresenterImpl ipresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopcar_layout, container, false);

        shopCarRecyclerView = view.findViewById(R.id.shopcarRecyclerview);
        shop_checkbox = view.findViewById(R.id.shop_checkbox);
        shop_price = view.findViewById(R.id.shop_price);
        sum_price_txt = view.findViewById(R.id.sum_price_txt);
        ipresenter = new IpresenterImpl(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopCarRecyclerView.setLayoutManager(linearLayoutManager);
        shangPinAdapter = new ShangPinAdapter(getActivity());
        shopCarRecyclerView.setAdapter(shangPinAdapter);
        ipresenter.get(Apis.ShopCarPath,ShopCarBean.class);

        view.findViewById(R.id.add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyAddressActivity.class);
                startActivity(intent);
            }
        });
        shopCarRecyclerView.addItemDecoration(new SpaceItemDecoration(10));


        shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangPinAdapter.notifyDataSetChanged();
                checkSeller(shop_checkbox.isChecked());
            }
        });


        shangPinAdapter.setListener(new ShangPinAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                double totalPrice=0;
                int num=0;
                int totalNum = 0;
                listAll = shopCarBean.getResult();
                for (int i = 0; i < listAll.size(); i++) {
                    totalNum = totalNum + listAll.get(i).getCount();
                    if (listAll.get(i).isCheck()) {
                        totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getCount());
                        num = num + listAll.get(i).getCount();
                    }
                }
                if (num < totalNum) {
                    shop_checkbox.setChecked(false);
                } else {
                    shop_checkbox.setChecked(true);
                }
                shop_price.setText("合计：" + totalPrice);
                sum_price_txt.setText("去结算（" + num + ")");

            }
        });



        return view;
    }


    @Override
    public void onSuccessData(Object data) {
        if (data instanceof ShopCarBean){
            shopCarBean = (ShopCarBean) data;
            shangPinAdapter.setList(shopCarBean.getResult());
        }
    }

    @Override
    public void onFailData(Exception e) {

    }

    private void checkSeller(boolean bool){
        double totalPrice=0;
        int num=0;
        listAll = shopCarBean.getResult();
        for (int i = 0; i< listAll.size(); i++){
                listAll.get(i).setCheck(bool);
                totalPrice=totalPrice+(listAll.get(i).getPrice()* listAll.get(i).getCount());
                num=num+ listAll.get(i).getCount();
                shangPinAdapter.notifyDataSetChanged();
        }

        if (bool){
            shop_price.setText("合计："+totalPrice);
            sum_price_txt.setText("去结算（"+num+")");

        }else {

            shop_price.setText("合计：0.00");
            sum_price_txt.setText("去结算（0)");
        }
    }



}
