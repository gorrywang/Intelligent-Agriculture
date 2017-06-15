package xyz.abug.www.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.abug.www.activity.ContentActivity;
import xyz.abug.www.adapter.MyPagerAdapter;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.SetUtils.setPic;
import static xyz.abug.www.utils.SetUtils.startCompare;

/**
 * Created by Dell on 2017/6/5.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private List<ImageView> mList = new ArrayList<ImageView>();
    private ViewPager mViewPager;
    private int[] mPicGroup = new int[]{R.drawable.bana1, R.drawable.bana2, R.drawable.bana3, R.drawable.co2};
    private MyPagerAdapter mMyPagerAdapter;
    private LinearLayout mLinearPointView;
    private static TextView mTextCO2, mTextGzqd, mTextTrwd, mTextTrsd, mTextKqwd, mTextKqsd;
    private static TextView mTextSSCO2, mTextSSGzqd, mTextSSTrwd, mTextSSTrsd, mTextSSKqwd, mTextSSKqsd;
    private static ImageView mImgCO2, mImgGZ, mImgTR, mImgKQ;
    //记针
    private int count = 0;
    private ImageButton mImgBtnCO2, mImgBtnSunny, mImgBtnTr, mImgBtnKq;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//            Log.e("tag", mViewPager.getCurrentItem() + "");
        }
    };

    private static SharedPreferences mSp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindID();
        mSp = getContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        addPic();
        initPager();
        //开启轮播
        startPlay();
    }

    /**
     * 轮播线程
     */
    private void startPlay() {
        // 开启轮询
        new Thread() {
            public void run() {
                Utils.mIsGetData = true;
                while (Utils.mIsGetData) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    /**
     * bann滑动
     */
    private void initPager() {
        mMyPagerAdapter = new MyPagerAdapter(mList);
        mViewPager.setAdapter(mMyPagerAdapter);
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mList.size());
        mViewPager.setCurrentItem(pos);
        mLinearPointView.getChildAt(0).setEnabled(true);
    }

    /**
     * 添加图片
     */
    private void addPic() {
        ImageView imageView;
        //指示器
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i : mPicGroup) {
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(i);
            mList.add(imageView);
            imageView = null;
            //添加小白点指示器
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(25, 25);
            if (i != 0)
                layoutParams.leftMargin = 10;

            // 设置默认所有都不可用
            pointView.setEnabled(false);
            mLinearPointView.addView(pointView, layoutParams);
        }
//        mLinearPointView.getChildAt(count).setEnabled(true);
    }

    /**
     * 绑定id
     */
    private void bindID() {
        mImgCO2 = (ImageView) mView.findViewById(R.id.frag_home_img_p_co2);
        mImgTR = (ImageView) mView.findViewById(R.id.frag_home_img_p_tr);
        mImgKQ = (ImageView) mView.findViewById(R.id.frag_home_img_p_kq);
        mImgGZ = (ImageView) mView.findViewById(R.id.frag_home_img_p_gz);
        //******************************************************************************
        //设置CO2
        mTextSSCO2 = (TextView) mView.findViewById(R.id.frag_home_text_ss_co2);
        //设置光照强度
        mTextSSGzqd = (TextView) mView.findViewById(R.id.frag_home_text_ss_gz);
        //设置土壤温度
        mTextSSTrwd = (TextView) mView.findViewById(R.id.frag_home_text_ss_trwd);
        //设置土壤适度
        mTextSSTrsd = (TextView) mView.findViewById(R.id.frag_home_text_ss_trsd);
        //设置空气温度
        mTextSSKqwd = (TextView) mView.findViewById(R.id.frag_home_text_ss_kqwd);
        //设置空气适度
        mTextSSKqsd = (TextView) mView.findViewById(R.id.frag_home_text_ss_kqsd);
        //******************************************************************************
        //空气温度
        mTextKqwd = (TextView) mView.findViewById(R.id.frag_home_text_kqwd);
        //空气适度
        mTextKqsd = (TextView) mView.findViewById(R.id.frag_home_text_kqsd);
        //土壤温度
        mTextTrwd = (TextView) mView.findViewById(R.id.frag_home_text_trwd);
        //土壤湿度
        mTextTrsd = (TextView) mView.findViewById(R.id.frag_home_text_trsd);
        //光照强度
        mTextGzqd = (TextView) mView.findViewById(R.id.frag_home_text_gzqd);
        //co2浓度
        mTextCO2 = (TextView) mView.findViewById(R.id.frag_home_text_co2);
        mViewPager = (ViewPager) mView.findViewById(R.id.frag_home_viewpager_ban);
        mLinearPointView = (LinearLayout) mView.findViewById(R.id.frag_home_linear_pointview);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % mList.size();
                mLinearPointView.getChildAt(pos).setEnabled(true);
                mLinearPointView.getChildAt(count).setEnabled(false);
                count = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mImgBtnCO2 = (ImageButton) mView.findViewById(R.id.fragment_home_img_co2);
        mImgBtnSunny = (ImageButton) mView.findViewById(R.id.fragment_home_img_sunny);
        mImgBtnTr = (ImageButton) mView.findViewById(R.id.fragment_home_img_tr);
        mImgBtnKq = (ImageButton) mView.findViewById(R.id.fragment_home_img_kq);
        mImgBtnCO2.setOnClickListener(this);
        mImgBtnSunny.setOnClickListener(this);
        mImgBtnTr.setOnClickListener(this);
        mImgBtnKq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = 0;
        switch (v.getId()) {
            case R.id.fragment_home_img_co2:
                //co2
                i = 1;
                break;
            case R.id.fragment_home_img_sunny:
                //光照
                i = 2;
                break;
            case R.id.fragment_home_img_tr:
                //土壤
                i = 3;
                break;
            case R.id.fragment_home_img_kq:
                //空气
                i = 4;
                break;
        }
        ContentActivity.ActivityJump(getActivity(), i);
    }

    private static Config mConfig;

    /**
     * 传感器数值
     */
    public static void showDataSensor(Sensor sensor) {
        mTextCO2.setText(sensor.co2 + "");
        mTextGzqd.setText(sensor.light + "");
        mTextTrwd.setText(sensor.soilTemperature + "");
        mTextTrsd.setText(sensor.soilHumidity + "");
        mTextKqwd.setText(sensor.airTemperature + "");
        mTextKqsd.setText(sensor.airHumidity + "");
        //判断数据显示图片
        comparePic(sensor);
    }

    /**
     * 阈值
     */
    public static void showDataConfig(Config config) {
        mConfig = config;
        mTextSSCO2.setText("设定值:" + config.getMinCo2() + "~" + config.getMaxCo2());
        mTextSSGzqd.setText("设定值:" + config.getMinLight() + "~" + config.getMaxLight());
        mTextSSTrwd.setText("温度值:" + config.getMinSoilTemperature() + "~" + config.getMaxSoilTemperature());
        mTextSSTrsd.setText("湿度值:" + config.getMinSoilHumidity() + "~" + config.getMaxSoilHumidity());
        mTextSSKqwd.setText("温度值:" + config.getMinAirTemperature() + "~" + config.getMaxAirTemperature());
        mTextSSKqsd.setText("湿度值:" + config.getMinAirHumidity() + "~" + config.getMaxAirHumidity());
    }

    /**
     * 判断数据显示图片
     */
    private static void comparePic(Sensor sensor) {
        if (sensor == null) {
            return;
        }
        if (mConfig != null) {
            //判断co2
            int co2 = sensor.co2;
            int co2Min = mConfig.getMinCo2();
            int co2Max = mConfig.getMaxCo2();
            boolean b1 = startCompare(co2, co2Min, co2Max);
            setPic(b1, mImgCO2);
            //判断光照
            int sunny = sensor.light;
            int sunnyMin = mConfig.getMinLight();
            int sunnyMax = mConfig.getMaxLight();
            boolean b2 = startCompare(sunny, sunnyMin, sunnyMax);
            setPic(b2, mImgGZ);
            //判断土壤指数
            //土壤温度
            int trwd = sensor.soilTemperature;
            int trwdMin = mConfig.getMinSoilTemperature();
            int trwdMax = mConfig.getMaxSoilTemperature();
            boolean b3 = startCompare(trwd, trwdMin, trwdMax);
            setPic(b3, mImgTR);
            //土壤湿度
            int trsd = sensor.soilHumidity;
            int trsdMin = mConfig.getMinSoilHumidity();
            int trsdMax = mConfig.getMaxSoilHumidity();
            boolean b4 = startCompare(trsd, trsdMin, trsdMax);
            setPic(b4, mImgTR);
            //判断空气指数
            //空气温度
            int kqwd = sensor.airTemperature;
            int kqwdMin = mConfig.getMinAirTemperature();
            int kqwdMax = mConfig.getMaxAirTemperature();
            boolean b5 = startCompare(kqwd, kqwdMin, kqwdMax);
            setPic(b5, mImgKQ);
            //空气湿度
            int kqsd = sensor.airHumidity;
            int kqsdMin = mConfig.getMinAirHumidity();
            int kqsdMax = mConfig.getMaxAirHumidity();
            boolean b6 = startCompare(kqsd, kqsdMin, kqsdMax);
            setPic(b6, mImgKQ);
        }
    }
}
