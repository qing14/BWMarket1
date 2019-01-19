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

public class AddAdressActivity extends AppCompatActivity implements Iview {

    private EditText my_add_address_name;
    private EditText my_add_address_phone;
    private TextView my_add_address_address;
    private EditText my_add_address_address_detail;
    private EditText my_add_address_postcode;
    private IpresenterImpl ipresenter;
    private CityPicker cityPicker;
    private String address;
    private String addressDetail;
    private String name;
    private String phone;
    private String postcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        my_add_address_name = findViewById(R.id.my_add_address_name);
        my_add_address_phone = findViewById(R.id.my_add_address_phone);
        my_add_address_address = findViewById(R.id.my_add_address_address);
        my_add_address_address_detail = findViewById(R.id.my_add_address_address_detail);
        my_add_address_postcode = findViewById(R.id.my_add_address_postcode);
        ipresenter = new IpresenterImpl(this);
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

        findViewById(R.id.my_add_address_address).setOnClickListener(new View.OnClickListener() {
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
                        my_add_address_address.setText(province + " " + city + " " + district);
                        my_add_address_postcode.setText(code);
                    }

                    @Override
                    public void onCancel() {
                        my_add_address_address.setText("");
                        my_add_address_postcode.setText("");
                    }
                });
            }
        });
        findViewById(R.id.my_add_address_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    private void submit() {
        name = my_add_address_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(AddAdressActivity.this, "收件人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        phone = my_add_address_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(AddAdressActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        address = my_add_address_address.getText().toString().trim();
        addressDetail = my_add_address_address_detail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            Toast.makeText(AddAdressActivity.this, "详细地址必须写6个字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        postcode = my_add_address_postcode.getText().toString().trim();
        if (TextUtils.isEmpty(postcode)) {
            Toast.makeText(AddAdressActivity.this, "邮编不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        getAddressData();
    }

    private void getAddressData() {
        Map<String, String> map = new HashMap<>();
        map.put("realName", name);
        map.put("phone", phone);
        map.put("address", address + " " + addressDetail);
        map.put("zipCode", postcode);
        ipresenter.startRequest(Apis.AddnewAddressPath, map, AddAdressBean.class);
    }

    @Override
    public void onSuccessData(Object data) {
        AddAdressBean addAdressBean = (AddAdressBean) data;
        if (addAdressBean.getMessage().equals("添加成功")) {
            Intent intent = new Intent(AddAdressActivity.this, MyAddressActivity.class);
            startActivity(intent);
            Toast.makeText(AddAdressActivity.this, "" + addAdressBean.getMessage(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(AddAdressActivity.this, "" + addAdressBean.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFailData(Exception e) {

    }
}
