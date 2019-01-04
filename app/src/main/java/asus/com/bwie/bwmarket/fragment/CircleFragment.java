package asus.com.bwie.bwmarket.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.adapter.CircleListAdapter;
import asus.com.bwie.bwmarket.bean.CircleListBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class CircleFragment extends Fragment implements Iview {
    private int mPage=1;
    private IpresenterImpl ipresenter;
    private XRecyclerView circle_recycle;
    private CircleListAdapter circleListAdapter;
    private TextView circle_dianzan_num;
    private ImageView circle_dianzan_img;
    int count=9;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_layout, container, false);
        circle_recycle = view.findViewById(R.id.circle_recycle);
        ipresenter = new IpresenterImpl(this);
        circle_dianzan_img = view.findViewById(R.id.circle_dianzan_img);
        circle_dianzan_num = view.findViewById(R.id.circle_dianzan_num);
        circle_recycle.setLoadingMoreEnabled(true);
        circle_recycle.setPullRefreshEnabled(true);
        circle_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                requestData();


            }

            @Override
            public void onLoadMore() {
                requestData();

            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        circle_recycle.setLayoutManager(linearLayoutManager);
        circleListAdapter = new CircleListAdapter(getActivity());
        circle_recycle.setAdapter(circleListAdapter);
        mPage=1;
        requestData();
        return view;
    }

    private void requestData() {
        ipresenter.getCircle(Apis.circleListPath,mPage,count,CircleListBean.class, 0);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof CircleListBean){
            CircleListBean circleListBean= (CircleListBean) data;
            circleListAdapter.setList(circleListBean.getResult());

        }
        mPage++;
        circle_recycle.loadMoreComplete();
        circle_recycle.refreshComplete();

    }

    @Override
    public void onFailData(Exception e) {

    }
}
