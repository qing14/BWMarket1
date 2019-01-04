package asus.com.bwie.bwmarket;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.bwmarket.adapter.MyPagerAdapter;
import asus.com.bwie.bwmarket.fragment.BillFragment;
import asus.com.bwie.bwmarket.fragment.CircleFragment;
import asus.com.bwie.bwmarket.fragment.HomeFragment;
import asus.com.bwie.bwmarket.fragment.MyFragment;
import asus.com.bwie.bwmarket.fragment.ShopCarFragment;

public class ShowActivity extends AppCompatActivity {

    private ViewPager vp;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        vp = findViewById(R.id.vp);
        radioGroup = findViewById(R.id.rg);
        List<Fragment> list=new ArrayList<>();

        list.add(new HomeFragment());
        list.add(new CircleFragment());
        list.add(new ShopCarFragment());
        list.add(new BillFragment());
        list.add(new MyFragment());

        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) list);
        vp.setAdapter(myPagerAdapter);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_button_home:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.home_button_circle:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.home_button_shop:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.home_button_bill:
                        vp.setCurrentItem(3);
                        break;
                    case R.id.home_button_my:
                        vp.setCurrentItem(4);
                        break;
                }
            }
        });

    }


}
