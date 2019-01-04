package asus.com.bwie.bwmarket.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.HomeXBannerBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class HomeFragment extends Fragment implements Iview {

    private XBanner xBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_layout, container, false);
        xBanner = inflate.findViewById(R.id.xbanner);
        IpresenterImpl ipresenter=new IpresenterImpl(this);
        ipresenter.getXBanner(Apis.XBannerPath,HomeXBannerBean.class);

        return inflate;
    }

    @Override
    public void onSuccessData(Object data) {
//        if (data instanceof HomeXBannerBean){
//            final HomeXBannerBean bannerBean = (HomeXBannerBean) data;
//            //为XBanner绑定数据
//            xBanner.setData(bannerBean.getResult(),null);
//            //XBanner适配数据
//            xBanner.loadImage(new XBanner.XBannerAdapter() {
//                @Override
//                public void loadBanner(XBanner banner, Object model, View view, int position) {
//                    HomeXBannerBean.ResultBean bannerbean= (HomeXBannerBean.ResultBean) model;
//                    Glide.with(getActivity()).load(bannerbean.getImageUrl()).into((ImageView) view);
//                    banner.setPageChangeDuration(1000);
//
//                }
//            });
//
//        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
