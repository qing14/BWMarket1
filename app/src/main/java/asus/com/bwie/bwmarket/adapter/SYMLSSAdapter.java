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

public class SYMLSSAdapter extends RecyclerView.Adapter<SYMLSSAdapter.ViewHolder> {


    private List<SYShopBean.ResultBean.MlssBean.CommodityListBeanXX> commodityListBeanXXs=new ArrayList<>();
    private Context mContext;

    public SYMLSSAdapter(Context Context) {
        this.mContext = Context;
    }

    public void setCommodityListBeanXX(List<SYShopBean.ResultBean.MlssBean.CommodityListBeanXX> commodityListBeanXX) {
        this.commodityListBeanXXs = commodityListBeanXX;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.mlss_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(mContext).load(commodityListBeanXXs.get(position).getMasterPic()).into(holder.sy_mlss_shopimg);
        holder.sy_mlss_shopprice.setText("￥："+commodityListBeanXXs.get(position).getPrice()+".00");
        holder.sy_mlss_shopname.setText(commodityListBeanXXs.get(position).getCommodityName()+"");



    }

    @Override
    public int getItemCount() {
        return commodityListBeanXXs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sy_mlss_shopimg;
        private final TextView sy_mlss_shopname;
        private final TextView sy_mlss_shopprice;

        public ViewHolder(View itemView) {
            super(itemView);
            sy_mlss_shopimg = itemView.findViewById(R.id.sy_mlss_shopimg);
            sy_mlss_shopname = itemView.findViewById(R.id.sy_mlss_shopname);
            sy_mlss_shopprice = itemView.findViewById(R.id.sy_mlss_shopprice);

        }
    }
}
