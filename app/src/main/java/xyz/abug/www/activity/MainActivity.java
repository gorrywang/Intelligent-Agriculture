package xyz.abug.www.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.abug.www.fragment.HelpFragment;
import xyz.abug.www.fragment.HomeFragment;
import xyz.abug.www.fragment.SettingFragment;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.service.GetJsonServer;
import xyz.abug.www.utils.Utility;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.CAST_CONFIG;
import static xyz.abug.www.utils.Utils.CAST_SENSOR;

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
    private TextView mTextTitle;
    private MyBroadCast mMyBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindID();
        init();
        regBroad();
        startMyService();
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
    }


    /**
     * 绑定ID
     */
    private void bindID() {
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
    protected void onDestroy() {
        super.onDestroy();
        Utils.mIsGetData = false;
        Intent intent = new Intent(MainActivity.this, GetJsonServer.class);
        stopService(intent);
        if (mMyBroadCast != null)
            unregisterReceiver(mMyBroadCast);
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

            } else if (action.equals(CAST_SENSOR)) {
                //传感器
                String data = intent.getStringExtra("data");
                Utils.logData("广播接受到传感器数据：" + data);
                Sensor sensor = Utility.jsonSensor(data);
                HomeFragment.showDataSensor(sensor);
            }
        }
    }

}
