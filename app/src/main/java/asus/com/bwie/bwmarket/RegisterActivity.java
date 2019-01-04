package asus.com.bwie.bwmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.bean.RegisterBean;
import asus.com.bwie.bwmarket.presenter.Ipresenter;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.util.YZUtils;
import asus.com.bwie.bwmarket.view.Iview;

public class RegisterActivity extends AppCompatActivity implements Iview {

    private EditText zc_phone;
    private EditText zc_security;
    private EditText zc_password;
    private TextView getsecurity;
    private TextView go_login;
    private Button zc_button;
    private String zcphones;
    private String zcpasswords;
    private IpresenterImpl ipresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);
        initView();
        PDUserandPwd();
        //密码框显示隐藏
        findViewById(R.id.zc_seepassword).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==MotionEvent.ACTION_DOWN){
                    zc_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    zc_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
        //已经注册，去登陆跳转
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        zc_phone = findViewById(R.id.zc_phone);
        zc_security = findViewById(R.id.zc_security);
        zc_password = findViewById(R.id.zc_password);
        getsecurity = findViewById(R.id.getsecurity);
        go_login = findViewById(R.id.go_login);
        zc_button = findViewById(R.id.zc_button);
        ipresenter = new IpresenterImpl(RegisterActivity.this);

    }
    // 点击注册，判断手机号和密码是否正确
    private void PDUserandPwd() {
        zc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (YZUtils.isPhoneValidator(zc_phone.getText().toString())){
                    if (YZUtils.isPwdValidator(zc_password.getText().toString())){

                        getData();
                    }else {
                        Toast.makeText(RegisterActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this,"手机号错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put("phone",zc_phone.getText().toString());
        map.put("pwd",zc_password.getText().toString());
        ipresenter.startRequest(Apis.registerPath,map,RegisterBean.class);
    }


    @Override
    public void onSuccessData(Object data) {
        if (data instanceof RegisterBean){
            RegisterBean registerBean= (RegisterBean) data;
            String status = registerBean.getStatus();
            if (status.equals("0000")){
                Toast.makeText(RegisterActivity.this,"注册成功,登陆一下看看有什么新东西吧",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(RegisterActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }


        }

    }

    @Override
    public void onFailData(Exception e) {
        ipresenter.detach();
    }
}
