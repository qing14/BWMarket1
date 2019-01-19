package asus.com.bwie.bwmarket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.adapter.AddressListAdapter;
import asus.com.bwie.bwmarket.bean.AddressListBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class MyAddressActivity extends AppCompatActivity implements Iview {

    private TextView my_increased_address;
    private RecyclerView my_address_recycleview;
    private IpresenterImpl ipresenter;
    private AddressListAdapter addressListAdapter;
    private AddressListBean addressListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        my_increased_address = findViewById(R.id.my_increased_address);
        my_increased_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyAddressActivity.this,AddAdressActivity.class);
                startActivity(intent);
            }
        });
        my_address_recycleview = findViewById(R.id.my_address_recycleview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        my_address_recycleview.setLayoutManager(linearLayoutManager);
        ipresenter = new IpresenterImpl(this);
        getAddressListData();
        addressListAdapter = new AddressListAdapter(this);
        my_address_recycleview.setAdapter(addressListAdapter);
        addressListAdapter.setMyAddressListenter(new AddressListAdapter.MyAddressListenter() {
            @Override
            public void onClick(int p, String s1, String s2, String s3, String s4) {
                Intent intent=new Intent(MyAddressActivity.this,UpdataAddessActivity.class);

                int id = addressListBean.getResult().get(p).getId();
                intent.putExtra("p",id);
                intent.putExtra("s1",s1);
                intent.putExtra("s2",s2);
                intent.putExtra("s3",s3);
                intent.putExtra("s4",s4);
                startActivity(intent);

            }

        });



    }

    private void getAddressListData() {
        ipresenter.get(Apis.AddressListPath,AddressListBean.class);

    }


    @Override
    public void onSuccessData(Object data) {
        if (data instanceof AddressListBean){
            addressListBean = (AddressListBean) data;
            if (addressListBean.getMessage().equals("查询成功")){
                addressListAdapter.setResultBeans(addressListBean.getResult());
            }else {
                Toast.makeText(MyAddressActivity.this,"您暂时没有收获地址,先去添加一个",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyAddressActivity.this,AddAdressActivity.class);
                startActivity(intent);
            }


        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
