package xyz.abug.www.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import xyz.abug.www.fragment.CO2Fragment;
import xyz.abug.www.fragment.KqFragment;
import xyz.abug.www.fragment.SunnyFragment;
import xyz.abug.www.fragment.TrFragment;
import xyz.abug.www.gson.CGQStatus;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.service.GetJsonServer;
import xyz.abug.www.service.GetStatusService;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.Utility;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.CAST_CONFIG;
import static xyz.abug.www.utils.Utils.CAST_SENSOR;
import static xyz.abug.www.utils.Utils.CAST_STATUS;

public class ContentActivity extends AppCompatActivity {

    private Fragment mFragmentCO2, mFragmentSunny, mFragmentTr, mFragmentKq;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private static int mInt;
    private static TextView mTextTitle;
    private ImageButton mBtnBack;
    private static Activity mMyContext;
    private MyCast mMyCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ui();
        bindID();
//        //获取设备状态
//        getStatus();
        //启动广播
        cast();
        //启动service
        service();
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

    private void ui() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 启动广播
     */
    private void cast() {
        if (mMyCast == null) {
            mMyCast = new MyCast();
        }
        IntentFilter filter = new IntentFilter(CAST_STATUS);
        filter.addAction(CAST_SENSOR);
        filter.addAction(CAST_CONFIG);
        registerReceiver(mMyCast, filter);
    }

    /**
     * 启动服务
     */
    private void service() {
        Intent intent = new Intent(ContentActivity.this, GetStatusService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent1 = new Intent(ContentActivity.this, GetJsonServer.class);
        startService(intent1);
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
//                            Toast.makeText(mMyContext, "ok", Toast.LENGTH_SHORT).show();
                            TSnackbar.make(mTextTitle, "设置成功", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();

                        } else {
                            TSnackbar.make(mTextTitle, "设置失败", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.ERROR).show();

                        }
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(ContentActivity.this, GetStatusService.class);
        stopService(intent);
        intent = null;
        intent = new Intent(ContentActivity.this, GetStatusService.class);
        stopService(intent);
        //取消广播
        if (mMyCast != null)
            unregisterReceiver(mMyCast);
    }


    /**
     * 显示数据(开关按钮)
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

    /**
     * 显示数据(范围变化)
     */
    private void showDataStatus(Config config) {
        switch (mInt) {
            case 1:
                //CO2浓度
                CO2Fragment.showData(config);
                break;
            case 2:
                //光照强度
                SunnyFragment.showData(config);
                break;
            case 3:
                //土壤指数
                TrFragment.showData(config);
                break;
            case 4:
                //空气指数
                KqFragment.showData(config);
                break;
        }
    }

    /**
     * 显示数据(范围变化)
     */
    private void showDataStatus(Sensor sensor) {
        switch (mInt) {
            case 1:
                //CO2浓度
                CO2Fragment.showData(sensor);
                break;
            case 2:
                //光照强度
                SunnyFragment.showData(sensor);
                break;
            case 3:
                //土壤指数
                TrFragment.showData(sensor);
                break;
            case 4:
                //空气指数
                KqFragment.showData(sensor);
                break;
        }
    }

    private class MyCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Utils.CAST_STATUS)) {
                //设备状态
                String asd = intent.getStringExtra("asd");
                CGQStatus cgqStatus = Utility.jsonStatus(asd);
                Log.e("tag", cgqStatus.getResult());
                if (cgqStatus != null) {
                    showDataStatus(cgqStatus);
                }
            } else if (action.equals(Utils.CAST_CONFIG)) {
                //范围
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到阈值数据：" + data);
                Config config = Utility.jsonConfig(data);
                if (config.getResult().equals("failed")) {
                    return;
                }
                if (config != null) {
                    showDataStatus(config);
                }

            } else if (action.equals(Utils.CAST_SENSOR)) {
                //数值
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到传感器数据：" + data);
                Sensor sensor = Utility.jsonSensor(data);
                if (sensor.result.equals("failed")) {
                    return;
                }
                if (sensor != null) {
                    showDataStatus(sensor);
                }

            }
        }
    }
}

