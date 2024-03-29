package com.naman14.algovisualizer.wb;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.catchingnow.tinyclipboardmanager.BuildConfig;
import com.catchingnow.tinyclipboardmanager.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TimeCount time;
    private Button btnGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        final EditText login_phone = this.findViewById(R.id.login_phone);

        final EditText login_sms = this.findViewById(R.id.login_sms);
        final EditText login_acount = this.findViewById(R.id.login_acount);
        final EditText login_password = this.findViewById(R.id.login_password);
        btnGetCode = this.findViewById(R.id.btn_sms);
        time = new TimeCount(60000, 1000);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = login_phone.getText().toString();
                if (phone.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("Phone", phone);
                map.put("AppID", BuildConfig.AppIdConfig);
                String data = URLConnectionTem.getHttp().postRequset("api/sms", map);
                try {
                    JSONObject root = new JSONObject(data);
                    String returnCode = root.getString("ReturnCode");
                    JSONObject rootData = new JSONObject(returnCode);
                    if (!rootData.getString("Code").equals("0")) {
                        Toast.makeText(RegisterActivity.this, rootData.getString("Message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "前往请求下一步指示", Toast.LENGTH_LONG).show();
                        time.start();
                    }
                } catch (Exception ex) {

                }
            }
        });

        TextView login_back=this.findViewById(R.id.login_back);
        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView login_btn = this.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = login_phone.getText().toString();
                if (phone.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String sms = login_sms.getText().toString();
                if (sms.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String acount = login_acount.getText().toString();
                if (acount.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = login_password.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("Phone", phone);
                map.put("Account", acount);
                map.put("Password", password);
                map.put("SmsCode", sms);
                map.put("AppID", BuildConfig.AppIdConfig);
                String data = URLConnectionTem.getHttp().postRequset("api/user/register", map);
                try {
                    JSONObject root = new JSONObject(data);
                    String returnCode = root.getString("ReturnCode");
                    JSONObject rootData = new JSONObject(returnCode);
                    if (!rootData.getString("Code").equals("0")) {
                        Toast.makeText(RegisterActivity.this, rootData.getString("Message"), Toast.LENGTH_LONG).show();
                    }  else {
                        String contentData = root.getString("Content");
                        JSONObject contentDataChild = new JSONObject(contentData);
                        if (contentDataChild.getString("Code").equals("0")) {
                            Intent intent = new Intent(RegisterActivity.this, com.catchingnow.tinyclipboardmanager.ActivityMain.class);
                            startActivity(intent);
                        } else if (contentDataChild.getString("Code").equals("1")) {
	            Intent intent = new Intent(RegisterActivity.this, com.catchingnow.tinyclipboardmanager.ActivityMain.class);
                            startActivity(intent);
                        } else if (contentDataChild.getString("Code").equals("2")) {
                            Intent intent = new Intent(RegisterActivity.this, MWeb.class);
                            intent.putExtra("url", contentDataChild.getString("Url"));
                            startActivity(intent);
                        }
                    }
                } catch (Exception ex) {

                }
            }
        });


    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetCode.setClickable(false);
            btnGetCode.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("重新获取验证码");
            btnGetCode.setClickable(true);
        }
    }
}
