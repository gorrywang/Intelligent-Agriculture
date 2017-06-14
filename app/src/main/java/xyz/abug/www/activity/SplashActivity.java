package xyz.abug.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout mLinear;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindID();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ip设置界面
                        mLinear.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }

    private void bindID() {
        mLinear = (LinearLayout) findViewById(R.id.splash_linear);
        mEdit = (EditText) findViewById(R.id.splash_edit);
    }

    public void setIp(View view) {
        String trim = mEdit.getText().toString().trim();
        boolean ip = isIP(trim);
        if (ip) {
            //正确
            Utils.mIP = trim;
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            //错误
            Toast.makeText(SplashActivity.this, "请输入合法地址", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 判断IP
     */
    public boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        //============对之前的ip判断的bug在进行判断
        if (ipAddress == true) {
            String ips[] = addr.split("\\.");
            if (ips.length == 4) {
                try {
                    for (String ip : ips) {
                        if (Integer.parseInt(ip) < 0 || Integer.parseInt(ip) > 255) {
                            return false;
                        }
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }
        return ipAddress;
    }

}
