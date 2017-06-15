package xyz.abug.www.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout mLinear;
    private EditText mEdit;
    private MKLoader mMkLoader;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindID();
        ui();
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
                        mMkLoader.setVisibility(View.INVISIBLE);
                        mView.setVisibility(View.INVISIBLE);
                        mLinear.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }

    private void ui() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void bindID() {
        mView = findViewById(R.id.splash_view);
        mMkLoader = (MKLoader) findViewById(R.id.splash_loader);
        mLinear = (LinearLayout) findViewById(R.id.splash_linear);
        mEdit = (EditText) findViewById(R.id.splash_edit);
    }

    public void setIp(View view) {
        String trim = mEdit.getText().toString().trim();
        boolean ip = isIP(trim);
        if (ip) {
            //正确
            Utils.mIP = trim;
            mLinear.setVisibility(View.GONE);
            mMkLoader.setVisibility(View.VISIBLE);
            mView.setVisibility(View.VISIBLE);
            //几秒之后跳转
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }.start();
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
