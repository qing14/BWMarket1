package asus.com.bwie.bwmarket.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.bwmarket.Apis;
import asus.com.bwie.bwmarket.R;
import asus.com.bwie.bwmarket.bean.LoginBean;
import asus.com.bwie.bwmarket.presenter.IpresenterImpl;
import asus.com.bwie.bwmarket.view.Iview;

public class MainActivity extends AppCompatActivity implements Iview {

    private EditText phonenum;
    private EditText password;
    private CheckBox jzpassword;
    private TextView go_zc;
    private Button login;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private IpresenterImpl ipresenter;
    private String phonenums;
    private String passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        go_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phonenums = phonenum.getText().toString();
                passwords = password.getText().toString();
                if (jzpassword.isChecked()){
                    editor.putBoolean("check_jz",true);
                    editor.putString("phonenum", phonenums);
                    editor.putString("password", passwords);
                    editor.commit();
                }else {
                    editor.clear();
                    editor.commit();
                    jzpassword.setChecked(false);
                }

                getPath();

            }
        });
        findViewById(R.id.login_seepassword).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==MotionEvent.ACTION_DOWN){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }else if (event.getAction()==MotionEvent.ACTION_UP){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                return true;
            }
        });

    }

    private void getPath() {
        Map<String,String> map=new HashMap<>();
        map.put("phone",phonenums);
        map.put("pwd",passwords);
        ipresenter.startRequest(Apis.loginPath,map,LoginBean.class);

    }

    private void initView() {
        phonenum = findViewById(R.id.login_phone);
        password = findViewById(R.id.login_password);
        jzpassword = findViewById(R.id.jzpassword);
        go_zc = findViewById(R.id.go_zc);
        login = findViewById(R.id.login);
        ipresenter = new IpresenterImpl(this);
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean check_jz = sharedPreferences.getBoolean("check_jz", false);
        if (check_jz){
            String phonenums = sharedPreferences.getString("phonenum", null);
            String passwords = sharedPreferences.getString("password", null);
            phonenum.setText(phonenums);
            password.setText(passwords);
            jzpassword.setChecked(true);
        }
    }

    @Override
    public void onSuccessData(Object data) {
    LoginBean loginBean= (LoginBean) data;

    if (phonenum==null||password==null){

        Toast.makeText(MainActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
    }else {
        if (loginBean.getStatus().equals("0000")) {
            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            intent.putExtra("phonename", phonenum.getText().toString());
            startActivity(intent);
        }else if(loginBean.getStatus().equals("1001")){
            Toast.makeText(MainActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    }

    @Override
    public void onFailData(Exception e) {
        e.printStackTrace();
        }
}
