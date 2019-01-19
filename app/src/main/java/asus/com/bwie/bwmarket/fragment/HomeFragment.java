package asus.com.bwie.bwmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.activity.ParticularsActivity;
import asus.com.bwie.bwmarket.adapter.ByKeyWordAdapter;
import asus.com.bwie.bwmarket.adapter.MoreAdapter;
import asus.com.bwie.bwmarket.adapter.SYMLSSAdapter;
import asus.com.bwie.bwmarket.adapter.SYPZSHAdapter;
import asus.com.bwie.bwmarket.adapter.SYRXXPAdapter;
import asus.com.bwie.bwmarket.bean.ByKeywordBean;
import asus.com.bwie.bwmarket.bean.ClickMoreBean;
import asus.com.bwie.bwmarket.bean.HomeXBannerBean;
import asus.com.bwie.bwmarket.bean.SYShopBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class HomeFragment extends Fragment implements Iview {

    private XBanner xBanner;
    private int xpnum=3;
    private int pznum=2;

    private RecyclerView rxxprecycleView;
    private RecyclerView mlssrecycleView;
    private RecyclerView pzshRecycleView;

    private SYRXXPAdapter syrxxpAdapter;
    private SYMLSSAdapter symlssAdapter;
    private SYPZSHAdapter sypzshAdapter;

    private IpresenterImpl ipresenter;
    private ImageView rxmore;
    private ImageView mlmore;
    private ImageView pzmore;
    private ScrollView scrollView;
    private RecyclerView moreRecycle;
    private MoreAdapter moreAdapter;
    private LinearLayout morelinear;
    private ImageView back;
    private ImageView sy_sousuo;
    private EditText editText;
    private ByKeyWordAdapter byKeyWordAdapter;
    private String ed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_layout, container, false);

        xBanner = inflate.findViewById(R.id.xbanner);
        rxxprecycleView = inflate.findViewById(R.id.rxxprecycleView);
        mlssrecycleView = inflate.findViewById(R.id.mlssrecycleView);
        pzshRecycleView = inflate.findViewById(R.id.pzshRecycleView);
        //更多按钮
        rxmore = inflate.findViewById(R.id.rxmore);
        mlmore = inflate.findViewById(R.id.mlmore);
        pzmore = inflate.findViewById(R.id.pzmore);
        
        scrollView = inflate.findViewById(R.id.scrollView);
        moreRecycle = inflate.findViewById(R.id.moreRecycle);
        morelinear = inflate.findViewById(R.id.morelinear);
        back = inflate.findViewById(R.id.back);
        editText = inflate.findViewById(R.id.edit);
        sy_sousuo = inflate.findViewById(R.id.sy_sousuo);



        ipresenter = new IpresenterImpl(this);
        ipresenter.getXBanner(Apis.XBannerPath,HomeXBannerBean.class);
        initSYRXXPData();
        initSYMLSSData();
        initSYPZSHData();
        rxmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMoreData(1002);
            }
        });
        mlmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMoreData(1003);
            }
        });
        pzmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMoreData(1004);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.VISIBLE);
                morelinear.setVisibility(View.GONE);

                editText.getText().clear();
            }
        });
        sy_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = editText.getText().toString();

                initByKeyWordData();


            }
        });


        return inflate;
    }
    private void initByKeyWordData() {
        scrollView.setVisibility(View.GONE);
        morelinear.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),pznum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        moreRecycle.setLayoutManager(gridLayoutManager);
        Log.e("cxy",editText.getText().toString());
        ipresenter.getXBanner(Apis.ByKeywordPath+"?keyword="+ed+"&page=1&count=10",ByKeywordBean.class);
        byKeyWordAdapter = new ByKeyWordAdapter(getContext());
        moreRecycle.addItemDecoration(new SpaceItemDecoration(5));
        moreRecycle.setAdapter(byKeyWordAdapter);
        byKeyWordAdapter.setOnByKeyWordClickListenter(new ByKeyWordAdapter.ByKeyWordClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ParticularsActivity.class);
                int pid = byKeyWordAdapter.getPid(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

    }

    private void initMoreData(int num) {
        scrollView.setVisibility(View.GONE);
        morelinear.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),pznum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        moreRecycle.setLayoutManager(gridLayoutManager);
        ipresenter.getXBanner(Apis.MoreShopPath+"?labelId="+num+"&page=1&count=10",ClickMoreBean.class);
        moreAdapter = new MoreAdapter(getContext());
        moreRecycle.addItemDecoration(new SpaceItemDecoration(10));
        moreRecycle.setAdapter(moreAdapter);
        moreAdapter.setOnMoreClickListenter(new MoreAdapter.MoreClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ParticularsActivity.class);
                int pid = moreAdapter.getPid(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

    }


    private void initSYRXXPData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),xpnum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rxxprecycleView.setLayoutManager(gridLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        syrxxpAdapter = new SYRXXPAdapter(getContext());
        rxxprecycleView.addItemDecoration(new SpaceItemDecoration(20));
        rxxprecycleView.setAdapter(syrxxpAdapter);
        syrxxpAdapter.setOnRXXPClickListenter(new SYRXXPAdapter.RXXPClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ParticularsActivity.class);
                int pid = syrxxpAdapter.getPid(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });


    }
    private void initSYMLSSData() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mlssrecycleView.setLayoutManager(linearLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        symlssAdapter = new SYMLSSAdapter(getContext());
        mlssrecycleView.addItemDecoration(new SpaceItemDecoration(20));
        mlssrecycleView.setAdapter(symlssAdapter);
        symlssAdapter.setOnMLSSClickListenter(new SYMLSSAdapter.MLSSClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ParticularsActivity.class);
                int pid = symlssAdapter.getPidxx(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });
    }
    private void initSYPZSHData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),pznum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        pzshRecycleView.setLayoutManager(gridLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        sypzshAdapter = new SYPZSHAdapter(getContext());
        pzshRecycleView.addItemDecoration(new SpaceItemDecoration(20));
        pzshRecycleView.setAdapter(sypzshAdapter);
        sypzshAdapter.setOnPZSHClickListenter(new SYPZSHAdapter.PZSHClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ParticularsActivity.class);
                int pid = sypzshAdapter.getPidx(position);
                Bundle bundle=new Bundle();
                bundle.putString("pid",pid+"");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });


    }


    @Override
    public void onSuccessData(Object data) {
        if (data instanceof SYShopBean){
            SYShopBean bean= (SYShopBean) data;
            syrxxpAdapter.setCommodityListBeans(bean.getResult().getRxxp().get(0).getCommodityList());
            symlssAdapter.setCommodityListBeanXX(bean.getResult().getMlss().get(0).getCommodityList());
            sypzshAdapter.setCommodityListBeanXs(bean.getResult().getPzsh().get(0).getCommodityList());

        }else if (data instanceof ClickMoreBean){
            ClickMoreBean clickMoreBean= (ClickMoreBean) data;
            moreAdapter.setResultBeans(clickMoreBean.getResult());
        }else if (data instanceof ByKeywordBean){
            ByKeywordBean byKeywordBean= (ByKeywordBean) data;
            List<ByKeywordBean.ResultBean> result = byKeywordBean.getResult();

            if(result.size()==0){
                Toast.makeText(getActivity(),"没有您想要的内容",Toast.LENGTH_SHORT).show();

            }else {
                byKeyWordAdapter.setResultBeans(byKeywordBean.getResult());

            }

        }else if (data instanceof HomeXBannerBean){
            HomeXBannerBean bannerBean = (HomeXBannerBean) data;
            //为XBanner绑定数据
            xBanner.setData(bannerBean.getResult(),null);
            //XBanner适配数据
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    HomeXBannerBean.ResultBean bannerbean= (HomeXBannerBean.ResultBean) model;
                    Glide.with(getActivity()).load(bannerbean.getImageUrl()).into((ImageView) view);
                    banner.setPageChangeDuration(1000);

                }
            });

        }


    }

    @Override
    public void onFailData(Exception e) {
            ipresenter.detach();
    }
}
