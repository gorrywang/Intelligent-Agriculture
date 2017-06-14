package xyz.abug.www.fragment;

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
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

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
    }
}
