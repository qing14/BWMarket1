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

public class SYPZSHAdapter extends RecyclerView.Adapter<SYPZSHAdapter.ViewHolder> {


    private List<SYShopBean.ResultBean.PzshBean.CommodityListBeanX> commodityListBeanXs=new ArrayList<>();
    private Context mContext;

    public SYPZSHAdapter(Context Context) {

        this.mContext = Context;
    }

    public void setCommodityListBeanXs(List<SYShopBean.ResultBean.PzshBean.CommodityListBeanX> commodityListBeanX) {
        this.commodityListBeanXs = commodityListBeanX;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.pzsh_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(mContext).load(commodityListBeanXs.get(position).getMasterPic()).into(holder.sy_pzsh_shopimg);
        holder.sy_pzsh_shopprice.setText("￥："+commodityListBeanXs.get(position).getPrice()+".00");
        holder.sy_pzsh_shopname.setText(commodityListBeanXs.get(position).getCommodityName()+"");



    }

    @Override
    public int getItemCount() {
        return commodityListBeanXs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sy_pzsh_shopimg;
        private final TextView sy_pzsh_shopname;
        private final TextView sy_pzsh_shopprice;

        public ViewHolder(View itemView) {
            super(itemView);
            sy_pzsh_shopimg = itemView.findViewById(R.id.sy_pzsh_shopimg);
            sy_pzsh_shopname = itemView.findViewById(R.id.sy_pzsh_shopname);
            sy_pzsh_shopprice = itemView.findViewById(R.id.sy_pzsh_shopprice);

        }
    }
}
