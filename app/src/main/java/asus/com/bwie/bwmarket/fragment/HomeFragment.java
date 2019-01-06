package asus.com.bwie.bwmarket.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.adapter.SYMLSSAdapter;
import asus.com.bwie.bwmarket.adapter.SYPZSHAdapter;
import asus.com.bwie.bwmarket.adapter.SYRXXPAdapter;
import asus.com.bwie.bwmarket.bean.HomeXBannerBean;
import asus.com.bwie.bwmarket.bean.SYShopBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class HomeFragment extends Fragment implements Iview {

    private XBanner xBanner;
    private RecyclerView rxxprecycleView;
    private IpresenterImpl ipresenter;
    private SYRXXPAdapter SYRXXPAdapter;
    private int xpnum=3;
    private int pznum=2;
    private SYMLSSAdapter symlssAdapter;
    private RecyclerView mlssrecycleView;
    private RecyclerView pzshRecycleView;
    private SYPZSHAdapter sypzshAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_layout, container, false);

        xBanner = inflate.findViewById(R.id.xbanner);
        rxxprecycleView = inflate.findViewById(R.id.rxxprecycleView);
        mlssrecycleView = inflate.findViewById(R.id.mlssrecycleView);
        pzshRecycleView = inflate.findViewById(R.id.pzshRecycleView);
        ipresenter = new IpresenterImpl(this);
        ipresenter.getXBanner(Apis.XBannerPath,HomeXBannerBean.class);
        initSYRXXPData();
        initSYMLSSData();
        initSYPZSHData();

        return inflate;
    }



    private void initSYRXXPData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),xpnum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rxxprecycleView.setLayoutManager(gridLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        SYRXXPAdapter = new SYRXXPAdapter(getContext());
        rxxprecycleView.addItemDecoration(new SpaceItemDecoration(20));
        rxxprecycleView.setAdapter(SYRXXPAdapter);
    }
    private void initSYMLSSData() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mlssrecycleView.setLayoutManager(linearLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        symlssAdapter = new SYMLSSAdapter(getContext());
        mlssrecycleView.addItemDecoration(new SpaceItemDecoration(20));
        mlssrecycleView.setAdapter(symlssAdapter);
    }
    private void initSYPZSHData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),pznum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        pzshRecycleView.setLayoutManager(gridLayoutManager);
        ipresenter.getXBanner(Apis.SPPath,SYShopBean.class);
        sypzshAdapter = new SYPZSHAdapter(getContext());
        pzshRecycleView.addItemDecoration(new SpaceItemDecoration(20));
        pzshRecycleView.setAdapter(sypzshAdapter);
    }


    @Override
    public void onSuccessData(Object data) {
        if (data instanceof SYShopBean){
            SYShopBean bean= (SYShopBean) data;
            SYRXXPAdapter.setCommodityListBeans(bean.getResult().getRxxp().get(0).getCommodityList());
            symlssAdapter.setCommodityListBeanXX(bean.getResult().getMlss().get(0).getCommodityList());
            sypzshAdapter.setCommodityListBeanXs(bean.getResult().getPzsh().get(0).getCommodityList());

        }
        else if (data instanceof HomeXBannerBean){
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
