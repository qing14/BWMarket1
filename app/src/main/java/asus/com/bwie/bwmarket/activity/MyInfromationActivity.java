package asus.com.bwie.bwmarket.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.CZBean;
import asus.com.bwie.bwmarket.bean.SelByIdBean;
import asus.com.bwie.bwmarket.bean.UpdataPassWordBean;
import asus.com.bwie.bwmarket.bean.UpdataUserNameBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class MyInfromationActivity extends AppCompatActivity implements View.OnClickListener ,Iview {

    private SimpleDraweeView change_tx;
    private TextView change_name;
    private TextView change_password;
    private IpresenterImpl ipresenter;
    private SelByIdBean selByIdBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infromation);
        change_tx = findViewById(R.id.change_tx);
        change_name = findViewById(R.id.change_name);
        change_password = findViewById(R.id.change_password);

        change_tx.setOnClickListener(this);
        change_name.setOnClickListener(this);
        change_password.setOnClickListener(this);
        ipresenter = new IpresenterImpl(MyInfromationActivity.this);
        ipresenter.get(Apis.GetUserByIdPath,SelByIdBean.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_tx:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MyInfromationActivity.this);
                dialog.setTitle("需要更换头像么");
                dialog.setIcon(R.mipmap.bitmap);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                        startActivityForResult(intent,1);



                      }
                });
                dialog.setNegativeButton("取消",null);


                AlertDialog alertDialog = dialog.create();
                dialog.show();


                break;
            case R.id.change_name:
                AlertDialog.Builder dialogname=new AlertDialog.Builder(MyInfromationActivity.this);
                final EditText name = new EditText(this);
                String s = change_name.getText().toString();
                dialogname.setTitle("需要更换名称么");
                dialogname.setView(name);
                name.setText(s);
                dialogname.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final Editable text = name.getText();
                        Map<String,String> map=new HashMap<>();
                        map.put("nickName",text+"");

                        ipresenter.put(Apis.UpdataUsernamePath,map,UpdataUserNameBean.class);
                        change_name.setText(text);
                    }
                });
                dialogname.setNegativeButton("取消",null);

                dialogname.show();


                break;
            case R.id.change_password:
                final AlertDialog.Builder dialogpass=new AlertDialog.Builder(MyInfromationActivity.this);
                final EditText editTextPass = new EditText(this);
                final String pass = change_password.getText().toString().trim();
                dialogpass.setTitle("需要更换密码么");
                dialogpass.setTitle("你的旧密码："+pass);
                dialogpass.setView(editTextPass);
                dialogpass.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass = editTextPass.getText().toString();
                        Map<String,String> map=new HashMap<>();
                        map.put("oldPwd",pass+"");
                        map.put("newPwd",newPass+"");
                        change_password.setText(newPass);
                        ipresenter.put(Apis.UpdataPasswordPath,map,UpdataPassWordBean.class);


                    }
                });

                dialogpass.setNegativeButton("取消",null);

                dialogpass.show();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==1){
            if (data!=null){
                change_tx.setImageURI(data.getData());
                }
        }
    }



    @Override
    public void onSuccessData(Object data) {
        if (data instanceof SelByIdBean){
            selByIdBean = (SelByIdBean) data;
            change_name.setText(selByIdBean.getResult().getNickName());
            change_password.setText(selByIdBean.getResult().getPassword());
            change_tx.setImageURI(selByIdBean.getResult().getHeadPic());
        }


    }

    @Override
    public void onFailData(Exception e) {

    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
