package xyz.abug.www.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import java.util.ArrayList;
import java.util.List;

import xyz.abug.www.fragment.HelpFragment;
import xyz.abug.www.fragment.HomeFragment;
import xyz.abug.www.fragment.SettingFragment;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.service.GetJsonServer;
import xyz.abug.www.utils.Utility;
import xyz.abug.www.utils.Utils;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static xyz.abug.www.utils.Utils.CAST_CONFIG;
import static xyz.abug.www.utils.Utils.CAST_SENSOR;
import static xyz.abug.www.utils.Utils.LBT_STATUS;
import static xyz.abug.www.utils.Utils.STATUS_NETWORK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLinearHome, mLinearHelp, mLinearSetting;
    private TextView mTextHome, mTextHelp, mTextSetting;
    private ImageView mImageHome, mImageHelp, mImageSetting;
    private FragmentManager mFragmentManager;
    private Fragment mFragmentHome, mFragmentHelp, mFragmentSetting;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    //滑动适配器
    private MyFragmentPagerAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private static TextView mTextTitle;
    //刷新广播
    private MyBroadCast mMyBroadCast;
    //网络广播
    private NetWorkBroadcast mNetWorkBroadcast;
    //为提示使用
    private LinearLayout mLinear;
    //底部
    private static LinearLayout mLinearBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ui();
        bindID();
        init();

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

    @Override
    protected void onStart() {
        super.onStart();
        regBroad();
        startMyService();
    }

    /**
     * 注册广播
     */
    private void regBroad() {
        //网络广播
        if (mNetWorkBroadcast == null) {
            mNetWorkBroadcast = new NetWorkBroadcast();
        }
        IntentFilter intentFilter1 = new IntentFilter(CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkBroadcast, intentFilter1);


        //数据广播
        if (mMyBroadCast == null) {
            mMyBroadCast = new MyBroadCast();
        }
        IntentFilter intentFilter = new IntentFilter(CAST_SENSOR);
        intentFilter.addAction(CAST_CONFIG);
        registerReceiver(mMyBroadCast, intentFilter);
    }


    /**
     * 启动服务
     */

    private void startMyService() {
        Intent intent = new Intent(MainActivity.this, GetJsonServer.class);
        startService(intent);
    }

    /**
     * 初始化
     */
    private void init() {
        mFragmentManager = getSupportFragmentManager();
        //第一个颜色
        mImageHome.setImageResource(R.drawable.shouye_lu);
        mTextHome.setTextColor(MainActivity.this.getResources().getColor(R.color.colorText_set));
        //加载数据
        addData();
    }

    /**
     * 加载滑动数据
     */
    private void addData() {
        if (mFragmentHome == null) {
            mFragmentHome = new HomeFragment();
        }

        if (mFragmentSetting == null) {
            mFragmentSetting = new SettingFragment();
        }

        if (mFragmentHelp == null) {
            mFragmentHelp = new HelpFragment();
        }

        mFragmentList.add(mFragmentHome);
        mFragmentList.add(mFragmentSetting);
        mFragmentList.add(mFragmentHelp);

        mFragmentAdapter = new MyFragmentPagerAdapter(mFragmentManager);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }


    /**
     * 绑定ID
     */
    private void bindID() {
        mLinear = (LinearLayout) findViewById(R.id.main_linear_load);
        mLinearBar = (LinearLayout) findViewById(R.id.main_linear_bar);
        mTextTitle = (TextView) findViewById(R.id.main_toolbar_title);
        mLinearHome = (LinearLayout) findViewById(R.id.main_linear_home);
        mLinearSetting = (LinearLayout) findViewById(R.id.main_linear_setting);
        mLinearHelp = (LinearLayout) findViewById(R.id.main_linear_help);
        mTextHome = (TextView) findViewById(R.id.main_text_home);
        mTextHelp = (TextView) findViewById(R.id.main_text_help);
        mTextSetting = (TextView) findViewById(R.id.main_text_setting);
        mImageHome = (ImageView) findViewById(R.id.main_img_home);
        mImageHelp = (ImageView) findViewById(R.id.main_img_help);
        mImageSetting = (ImageView) findViewById(R.id.main_img_setting);
        mViewPager = (ViewPager) findViewById(R.id.main_frame_load);
        mLinearHome.setOnClickListener(this);
        mLinearHelp.setOnClickListener(this);
        mLinearSetting.setOnClickListener(this);
        //滑动页面
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //还原图案
                reduction();
                switch (position) {
                    case 0:
                        //设置颜色
                        mImageHome.setImageResource(R.drawable.shouye_lu);
                        mTextHome.setTextColor(MainActivity.this.getResources().getColor(R.color.colorText_set));
                        mTextTitle.setText("智 能 农 业");
                        break;
                    case 1:
                        //设置颜色
                        mImageSetting.setImageResource(R.drawable.shezhi_lu);
                        mTextSetting.setTextColor(MainActivity.this.getResources().getColor(R.color.colorText_set));
                        mTextTitle.setText("设 置");
                        break;
                    case 2:
                        //设置颜色
                        mImageHelp.setImageResource(R.drawable.bangzhu_lu);
                        mTextHelp.setTextColor(MainActivity.this.getResources().getColor(R.color.colorText_set));
                        mTextTitle.setText("帮 助");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View v) {
        //还原图案
        reduction();
        switch (v.getId()) {
            case R.id.main_linear_home:
                mViewPager.setCurrentItem(0);
                //设置颜色
                mImageHome.setImageResource(R.drawable.shouye_lu);
                mTextHome.setTextColor(this.getResources().getColor(R.color.colorText_set));
                mTextTitle.setText("智 能 农 业");
                break;
            case R.id.main_linear_setting:
                //设置
                mViewPager.setCurrentItem(1);
                //设置颜色
                mImageSetting.setImageResource(R.drawable.shezhi_lu);
                mTextSetting.setTextColor(this.getResources().getColor(R.color.colorText_set));
                mTextTitle.setText("设 置");
                break;
            case R.id.main_linear_help:
                //帮助
                mViewPager.setCurrentItem(2);
                //设置颜色
                mImageHelp.setImageResource(R.drawable.bangzhu_lu);
                mTextHelp.setTextColor(this.getResources().getColor(R.color.colorText_set));
                mTextTitle.setText("帮 助");
                break;
        }
    }

    /**
     * 还原图案
     */
    private void reduction() {
        mImageHome.setImageResource(R.drawable.b1);
        mImageSetting.setImageResource(R.drawable.b2);
        mImageHelp.setImageResource(R.drawable.b3);
        mTextHome.setTextColor(this.getResources().getColor(R.color.colorText_default));
        mTextHome.setTextColor(this.getResources().getColor(R.color.colorText_default));
        mTextHome.setTextColor(this.getResources().getColor(R.color.colorText_default));
    }


    /**
     * 隐藏所有fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mFragmentHelp != null) {
            transaction.hide(mFragmentHelp);
        }
        if (mFragmentSetting != null) {
            transaction.hide(mFragmentSetting);
        }
        if (mFragmentHome != null) {
            transaction.hide(mFragmentHome);
        }
    }

    /**
     * 滑动适配器
     */
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        //关闭服务
        Intent intent = new Intent(MainActivity.this, GetJsonServer.class);
        stopService(intent);
        //关闭广播
        if (mMyBroadCast != null)
            unregisterReceiver(mMyBroadCast);
        mMyBroadCast = null;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        regBroad();
        startMyService();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Utils.mIsGetData) {
            if (mMyBroadCast != null)
                unregisterReceiver(mMyBroadCast);
        }
        //轮播图关闭
        LBT_STATUS = false;
        Intent intent = new Intent(MainActivity.this, GetJsonServer.class);
        stopService(intent);
    }


    /**
     * 广播接收服务返回的数据
     */
    class MyBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(CAST_CONFIG)) {
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到阈值数据：" + data);
                Config config = Utility.jsonConfig(data);
                if (config.getResult().equals("failed")) {
                    return;
                }
                HomeFragment.showDataConfig(config);

            } else if (action.equals(CAST_SENSOR)) {
                //传感器
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到传感器数据：" + data);
                Sensor sensor = Utility.jsonSensor(data);
                if (sensor.result.equals("failed")) {
                    return;
                }
                //显示数据
                HomeFragment.showDataSensor(sensor);
            }
        }
    }


    private static boolean mBool = true;

    /**
     * 网络广播
     */
    class NetWorkBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CONNECTIVITY_ACTION)) {
                //监听网络
                //得到网络连接管理器
                ConnectivityManager connectionManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                //通过管理器得到网络实例
                NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                //判断是否连接
                if (networkInfo != null && networkInfo.isAvailable()) {
                    mBool = true;
                    if (STATUS_NETWORK != mBool) {
                        STATUS_NETWORK = true;
                        TSnackbar.make(mLinear, "网络已连接", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                    }
                } else {
                    mBool = false;
                    if (STATUS_NETWORK != mBool) {
                        STATUS_NETWORK = false;
                        TSnackbar.make(mLinear, "网络未连接", TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.WARNING).show();
                    }
                }
            }
        }
    }

    /**
     * 去掉标题
     */
    public static void closeTitle() {
        mTextTitle.setVisibility(View.GONE);
        mLinearBar.setVisibility(View.GONE);
    }

    /**
     * 显示标题
     */
    public static void showTitle() {
        mTextTitle.setVisibility(View.VISIBLE);
        mLinearBar.setVisibility(View.VISIBLE);
    }

    private long clickTime = 0; //记录第一次点击的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                TSnackbar.make(mLinear, "再次点击退出软件", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                clickTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
        }
        return true;
    }
}
