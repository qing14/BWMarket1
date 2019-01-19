package asus.com.bwie.bwmarket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.adapter.MyFootAdapter;
import asus.com.bwie.bwmarket.bean.MyFootBean;
import asus.com.bwie.bwmarket.presenter.Ipresenter;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class MyFootActivity extends AppCompatActivity implements Iview {

    private RecyclerView myfootRecycle;
    private IpresenterImpl ipresenter;
    private MyFootAdapter myFootAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot);
        myfootRecycle = findViewById(R.id.my_foot_recycle);
        ipresenter = new IpresenterImpl(this);
        myFootAdapter = new MyFootAdapter(this);
        getMyFootData();
        myFootAdapter.setMyFootClickListener(new MyFootAdapter.MyFootListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getApplicationContext(),ParticularsActivity.class);
                int pid = myFootAdapter.getPid(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void getMyFootData() {
        myfootRecycle.addItemDecoration(new SpaceItemDecoration(10));
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        myfootRecycle.setLayoutManager(staggeredGridLayoutManager);

        myfootRecycle.setAdapter(myFootAdapter);
        ipresenter.getCircle(Apis.GetMyFootPath,1,10,MyFootBean.class);

    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof MyFootBean){
            MyFootBean myFootBean= (MyFootBean) data;
            myFootAdapter.setList(myFootBean.getResult());

        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
