package asus.com.bwie.bwmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.activity.MainActivity;
import asus.com.bwie.bwmarket.adapter.CircleListAdapter;
import asus.com.bwie.bwmarket.bean.CircleDZBean;
import asus.com.bwie.bwmarket.bean.CircleListBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.SpaceItemDecoration;
import asus.com.bwie.bwmarket.view.Iview;

public class CircleFragment extends Fragment implements Iview {
    private int mPage=1;
    private IpresenterImpl ipresenter;
    private XRecyclerView circle_recycle;
    private CircleListAdapter circleListAdapter;
    private TextView circle_dianzan_num;
    private ImageView circle_dianzan_img;
    int count=5;

    private int isClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_layout, container, false);
        circle_recycle = view.findViewById(R.id.circle_recycle);
        ipresenter = new IpresenterImpl(this);
        circle_dianzan_img = view.findViewById(R.id.circle_dianzan_img);
        circleListAdapter = new CircleListAdapter(getActivity());
        circle_recycle.addItemDecoration(new SpaceItemDecoration(10));
        circle_recycle.setAdapter(circleListAdapter);
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

        mPage=1;
        requestData();
        circleListAdapter.setOnDZClickListenter(new CircleListAdapter.DZClickListenter() {
            @Override
            public void onClick(int WhetherGreat, int position, int id) {
                isClick=position;
                    if (WhetherGreat==1){
                        CancelLike(id);
                    }else {
                        getLike(id);
                    }
            }
        });


        return view;
    }
    //取消
    private void CancelLike(int id) {
        Map<String,String> map=new HashMap<>();
        map.put("circleId",id+"");
        ipresenter.delete(Apis.UnCircleDZPath,map,CircleDZBean.class);

    }
    //点赞
    private void getLike(int id) {
        Map<String,String> map=new HashMap<>();
        map.put("circleId",id+"");
        ipresenter.startRequest(Apis.CricleDZPath,map,CircleDZBean.class);
    }

    private void requestData() {
        ipresenter.getCircle(Apis.circleListPath,mPage,count,CircleListBean.class);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof CircleListBean){
            CircleListBean circleListBean= (CircleListBean) data;
            circleListAdapter.setList(circleListBean.getResult());

            mPage++;
            circle_recycle.loadMoreComplete();
            circle_recycle.refreshComplete();

        } else if(data instanceof CircleDZBean){
            CircleDZBean circleDZBean= (CircleDZBean) data;
            if(circleDZBean.getMessage().equals("请先登陆")){
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }else if(circleDZBean.getMessage().equals("点赞成功")){
                Toast.makeText(getContext(), circleDZBean.getMessage(), Toast.LENGTH_SHORT).show();
                circleListAdapter.Dianzan(isClick);
                circleListAdapter.notifyDataSetChanged();
            }else {
                circleListAdapter.UnDaianzan(isClick);
                Toast.makeText(getContext(), circleDZBean.getMessage(), Toast.LENGTH_SHORT).show();
                circleListAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onFailData(Exception e) {
        ipresenter.detach();
    }
}
