package xyz.abug.www.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import xyz.abug.www.fragment.SettingCO2Fragment;
import xyz.abug.www.fragment.SettingKqFragment;
import xyz.abug.www.fragment.SettingSunnyFragment;
import xyz.abug.www.fragment.SettingTrFragment;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.service.GetJsonServer;
import xyz.abug.www.utils.Utility;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.CAST_CONFIG;
import static xyz.abug.www.utils.Utils.CAST_SENSOR;

public class SettingContentActivity extends AppCompatActivity {
    private static int mCount = 0;
    private FragmentTransaction mTransaction;
    private FragmentManager mManager;
    private Fragment mFr;
    private ImageButton mImgBtn;
    private MyBroadCast mMyBroadCast;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_content);
        mContext = this;
        //注册广播
        regBroad();
        //开启服务
        startMyService();
        mImgBtn = (ImageButton) findViewById(R.id.setting_content_imgbtn_back);
        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        switch (mCount) {
            case 1:
                //CO2
                mFr = new SettingCO2Fragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
            case 2:
                //光照
                mFr = new SettingSunnyFragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
            case 3:
                //空气
                mFr = new SettingKqFragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
            case 4:
                //土壤
                mFr = new SettingTrFragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
        }
        mTransaction.commit();

    }

    public static void activityJump(Activity context, int i) {
        Intent intent = new Intent(context, SettingContentActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mCount = i;
    }

    /**
     * 启动服务
     */
    private void startMyService() {
        Intent intent = new Intent(SettingContentActivity.this, GetJsonServer.class);
        startService(intent);
    }

    /**
     * 注册广播
     */
    private void regBroad() {
        mMyBroadCast = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter(CAST_SENSOR);
        intentFilter.addAction(CAST_CONFIG);
        registerReceiver(mMyBroadCast, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(SettingContentActivity.this, GetJsonServer.class);
        stopService(intent);
        if (mMyBroadCast != null)
            unregisterReceiver(mMyBroadCast);
    }

    private static void closeService() {
        Intent intent = new Intent(mContext, GetJsonServer.class);
        mContext.stopService(intent);
    }

    private int a = 0;
    private int b = 0;
    private Sensor mSensor;
    private Config mConfig;

    /**
     * 广播接收服务返回的数据
     */
    private class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (a == 1 && b == 1) {
                if (mSensor != null && mConfig != null) {
                    setPic(mSensor, mConfig);
                    //关闭服务
                    closeService();
                }
                return;
            }

            String action = intent.getAction();
            if (action.equals(CAST_CONFIG)) {
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到阈值数据：" + data);
                Config config = Utility.jsonConfig(data);
                if (config.getResult().equals("failed")) {
                    return;
                } else {
                    //有数据
                    a = 1;
                    mConfig = config;
                    showDataYZ(config);
                }

            } else if (action.equals(CAST_SENSOR)) {
                //传感器
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到传感器数据：" + data);
                Sensor sensor = Utility.jsonSensor(data);
                if (sensor.result.equals("failed")) {
                    return;
                } else {
                    //有数据
                    b = 1;
                    mSensor = sensor;
                    showDataSJ(sensor);
                }
            }
            Log.e("tag", "a=" + a + ",b=" + b);
        }
    }

    /**
     * 设置图片
     */
    private void setPic(Sensor mSensor, Config mConfig) {
        switch (mCount) {
            case 1:
                //co2
                SettingCO2Fragment.setPic(mSensor, mConfig);
                break;
            case 2:
                //光照
                SettingSunnyFragment.setPic(mSensor, mConfig);
                break;
            case 3:
                //空气
                SettingKqFragment.setPic(mSensor, mConfig);
                break;
            case 4:
                //土壤
                SettingTrFragment.setPic(mSensor, mConfig);
                break;
        }
    }

    /**
     * 设置阈值
     */
    private void showDataYZ(Config config) {
        switch (mCount) {
            case 1:
                //co2
                SettingCO2Fragment.showDataYZ(config);
                break;
            case 2:
                //光照
                SettingSunnyFragment.showDataYZ(config);
                break;
            case 3:
                //空气
                SettingKqFragment.showDataYZ(config);
                break;
            case 4:
                //土壤
                SettingTrFragment.showDataYZ(config);
                break;
        }
    }

    /**
     * 设置数据
     */
    private void showDataSJ(Sensor sensor) {
        switch (mCount) {
            case 1:
                //co2
                SettingCO2Fragment.showDataSJ(sensor);
                break;
            case 2:
                //光照
                SettingSunnyFragment.showDataSJ(sensor);
                break;
            case 3:
                //空气
                SettingKqFragment.showDataSJ(sensor);
                break;
            case 4:
                //土壤
                SettingTrFragment.showDataSJ(sensor);
                break;
        }
    }
}
