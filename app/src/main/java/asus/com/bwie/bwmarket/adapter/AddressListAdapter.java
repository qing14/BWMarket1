package asus.com.bwie.bwmarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.AddressListBean;
import asus.com.bwie.bwmarket.bean.SYShopBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {


    private List<AddressListBean.ResultBean> resultBeans=new ArrayList<>();
    private Context mContext;

    public AddressListAdapter(Context Context) {
        this.mContext = Context;
    }

    public void setResultBeans(List<AddressListBean.ResultBean> resultBeans) {
        this.resultBeans = resultBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.addresslist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.my_address_item_name.setText(""+resultBeans.get(position).getRealName());
        holder.my_address_item_phone.setText(""+resultBeans.get(position).getPhone());
        holder.my_address_item_address.setText(""+resultBeans.get(position).getAddress());

        holder.my_address_item_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressListenter.onClick(position,resultBeans.get(position).getRealName(),resultBeans.get(position).getPhone(),resultBeans.get(position).getAddress(),resultBeans.get(position).getZipCode());
            }
        });


        holder.my_address_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultBeans.remove(position);
                notifyDataSetChanged();

            }
        });



    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView my_address_item_name;
        private final TextView my_address_item_phone;
        private final TextView my_address_item_address;
        private final TextView my_address_item_update;
        private final TextView my_address_item_delete;
        private final CheckBox my_address_item_checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            my_address_item_name = itemView.findViewById(R.id.my_address_item_name);
            my_address_item_phone = itemView.findViewById(R.id.my_address_item_phone);
            my_address_item_address = itemView.findViewById(R.id.my_address_item_address);
            my_address_item_update = itemView.findViewById(R.id.my_address_item_update);
            my_address_item_delete = itemView.findViewById(R.id.my_address_item_delete);
            my_address_item_checkbox = itemView.findViewById(R.id.my_address_item_checkbox);
        }
    }
    public MyAddressListenter addressListenter;

    public  void setMyAddressListenter(MyAddressListenter listenter){
        this.addressListenter =listenter;

    }


    public interface MyAddressListenter{
        void onClick(int p,String s1,String s2,String s3,String s4);

    }


}
