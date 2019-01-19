package asus.com.bwie.bwmarket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.AddAdressBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class UpdataAddessActivity extends AppCompatActivity implements Iview {


    private CityPicker cityPicker;
    private EditText up_shname;
    private EditText up_shphone;
    private TextView up_shaddress_address;
    private EditText up_shaddress_detail;
    private EditText up_shzipCode;
    private TextView up_id;
    private IpresenterImpl ipresenter;
    private String id;
    private String name;
    private String phone;
    private String address;
    private String addressDetail;
    private String zcide1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_addess);
        up_shname = findViewById(R.id.up_shname);
        up_shphone = findViewById(R.id.up_shphone);
        up_shaddress_address = findViewById(R.id.up_shaddress_address);
        up_shaddress_detail = findViewById(R.id.up_shaddress_detail);
        up_shzipCode = findViewById(R.id.up_shzipCode);
        up_id = findViewById(R.id.up_id);
        ipresenter = new IpresenterImpl(this);

        Intent intent = getIntent();
        int p = intent.getIntExtra("p", 0);
        String name = intent.getStringExtra("s1");
        String phone = intent.getStringExtra("s2");
        String address = intent.getStringExtra("s3");
        String zcide  =intent.getStringExtra("s4");
        //赋值给所在的控件
        up_id.setText(p+"");
        up_shname.setText(name);
        up_shphone.setText(phone);
        up_shaddress_detail.setText(address);
        up_shzipCode.setText(zcide);

        getAdress();
        findViewById(R.id.up_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void getAdress() {
        cityPicker = new CityPicker.Builder(this)
                .textSize(20)
                .title("城市选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#5f8a8a")
                .titleTextColor("#000000")
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("XX市")
                .district("XX区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        findViewById(R.id.up_shaddress_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... strings) {
                        //省份
                        String province = strings[0];
                        //城市
                        String city = strings[1];
                        //区县
                        String district = strings[2];
                        //邮编
                        String code = strings[3];
                        //给地址和邮编赋值
                        up_shaddress_address.setText(province + " " + city + " " + district);
                        up_shzipCode.setText(code);
                    }

                    @Override
                    public void onCancel() {
                        up_shaddress_address.setText("");
                        up_shzipCode.setText("");
                    }
                });
            }
        });
    }

    private void submit() {
        id = up_id.getText().toString();
        name = up_shname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(UpdataAddessActivity.this, "收件人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        phone = up_shphone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(UpdataAddessActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        address = up_shaddress_address.getText().toString().trim();
        addressDetail = up_shaddress_detail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            Toast.makeText(UpdataAddessActivity.this, "详细地址必须写6个字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        zcide1 = up_shzipCode.getText().toString().trim();
        if (TextUtils.isEmpty(zcide1)) {
            Toast.makeText(UpdataAddessActivity.this, "邮编不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        getUpAddressData();
    }

    private void getUpAddressData() {
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("realName",name+"");
        map.put("phone",phone+"");
        map.put("address",address+""+addressDetail);
        map.put("zipCode",zcide1+"");
        ipresenter.put(Apis.UpdataAddressPath,map,AddAdressBean.class);
    }

    @Override
    public void onSuccessData(Object data) {
        if(data instanceof AddAdressBean)
        {
            AddAdressBean bean= (AddAdressBean) data;
            if(bean.getMessage().equals("修改成功"))
            {
                Toast.makeText(this, bean.getMessage()+"", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdataAddessActivity.this,MyAddressActivity.class);
                startActivity(intent);
            }else
            {
                Toast.makeText(this, bean.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFailData(Exception e) {

    }
}
