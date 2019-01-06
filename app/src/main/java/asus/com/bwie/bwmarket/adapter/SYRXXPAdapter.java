package asus.com.bwie.bwmarket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.SYShopBean;

public class SYRXXPAdapter extends RecyclerView.Adapter<SYRXXPAdapter.ViewHolder> {


    private List<SYShopBean.ResultBean.RxxpBean.CommodityListBean> commodityListBeans=new ArrayList<>();
    private Context mContext;

    public SYRXXPAdapter(Context Context) {

        this.mContext = Context;
    }

    public void setCommodityListBeans(List<SYShopBean.ResultBean.RxxpBean.CommodityListBean> commodityListBean) {
        this.commodityListBeans = commodityListBean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.rxxp_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(mContext).load(commodityListBeans.get(position).getMasterPic()).into(holder.sy_shopimg);
        holder.sy_shopprice.setText("￥："+commodityListBeans.get(position).getPrice()+".00");
        holder.sy_shopname.setText(commodityListBeans.get(position).getCommodityName()+"");



    }

    @Override
    public int getItemCount() {
        return commodityListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sy_shopimg;
        private final TextView sy_shopname;
        private final TextView sy_shopprice;

        public ViewHolder(View itemView) {
            super(itemView);
            sy_shopimg = itemView.findViewById(R.id.sy_shopimg);
            sy_shopname = itemView.findViewById(R.id.sy_shopname);
            sy_shopprice = itemView.findViewById(R.id.sy_shopprice);

        }
    }
}
