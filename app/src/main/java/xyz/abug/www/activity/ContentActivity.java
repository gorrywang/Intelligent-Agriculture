package xyz.abug.www.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.abug.www.fragment.CO2Fragment;
import xyz.abug.www.fragment.KqFragment;
import xyz.abug.www.fragment.SunnyFragment;
import xyz.abug.www.fragment.TrFragment;
import xyz.abug.www.gson.CGQStatus;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.Utility;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.mIP;

public class ContentActivity extends AppCompatActivity {

    private Fragment mFragmentCO2, mFragmentSunny, mFragmentTr, mFragmentKq;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private static int mInt;
    private TextView mTextTitle;
    private ImageButton mBtnBack;
    private static Activity mMyContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        bindID();
        //获取设备状态
        getStatus();
        mMyContext = ContentActivity.this;
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        switch (mInt) {
            case 1:
                //CO2浓度
                mFragmentCO2 = new CO2Fragment();
                mTransaction.add(R.id.content_frame_load, mFragmentCO2);
                mTextTitle.setText("CO2 详 情");
                break;
            case 2:
                //光照强度
                mFragmentSunny = new SunnyFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentSunny);
                mTextTitle.setText("光 照 详 情");
                break;
            case 3:
                //土壤指数
                mFragmentTr = new TrFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentTr);
                mTextTitle.setText("土 壤 详 情");
                break;
            case 4:
                //空气指数
                mFragmentKq = new KqFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentKq);
                mTextTitle.setText("空 气 详 情");
                break;
        }
        mTransaction.commit();
    }

    private void bindID() {
        mTextTitle = (TextView) findViewById(R.id.context_text_title);
        mBtnBack = (ImageButton) findViewById(R.id.content_imgbtn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param context 上下文
     * @param i       页面
     */
    public static void ActivityJump(Activity context, int i) {
        Intent intent = new Intent(context, ContentActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mInt = i;
    }

    /**
     * 控制代码
     */
    public static void control(final String utilsKZ) {
        new Thread() {
            @Override
            public void run() {
                final boolean b = HttpUtils.controlCGQ(utilsKZ);
                mMyContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            Toast.makeText(mMyContext, "ok", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();
    }

    /**
     * 获取当前的状态
     */
    private void getStatus() {
        RequestBody body = new FormBody.Builder().add("", "").build();
        HttpUtils.sendQuestBackResponse(Utils.URL_GET_HTTP_HEAD + mIP + Utils.URL_GET_STATUS, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Utils.logData("获取到设备状态：" + string);
                final CGQStatus cgqStatus = Utility.jsonStatus(string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showDataStatus(cgqStatus);
                    }
                });
            }
        });
    }

    /**
     * 显示数据
     */
    private void showDataStatus(CGQStatus cgqStatus) {
        switch (mInt) {
            case 1:
                //CO2浓度
                CO2Fragment.showData(cgqStatus);
                break;
            case 2:
                //光照强度
                SunnyFragment.showData(cgqStatus);
                break;
            case 3:
                //土壤指数
                TrFragment.showData(cgqStatus);
                break;
            case 4:
                //空气指数
                KqFragment.showData(cgqStatus);
                break;
        }
    }

}

