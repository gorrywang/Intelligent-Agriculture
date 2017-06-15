package xyz.abug.www.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.abug.www.activity.ContentActivity;
import xyz.abug.www.gson.CGQStatus;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

/**
 * Created by Dell on 2017/6/5.
 * 光照
 */

public class SunnyFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private static ImageView mToggleFengming;
    private static ImageView mToggleGuangzhao;
    private static TextView mText1;
    private static TextView mText2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sunny, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindID();
    }

    /**
     * 绑定id
     */
    private void bindID() {
        mToggleFengming = (ImageView) mView.findViewById(R.id.frag_sunny_fengming);
        mToggleGuangzhao = (ImageView) mView.findViewById(R.id.frag_sunny_guangzhao);
        mText1 = (TextView) mView.findViewById(R.id.b_text_1);
        mText2 = (TextView) mView.findViewById(R.id.b_text_2);
        mToggleFengming.setOnClickListener(this);
        mToggleGuangzhao.setOnClickListener(this);
    }


    private static CGQStatus status;

    /**
     * 设备状态(开关)
     */
    public static void showData(CGQStatus cgqStatus) {
        status = cgqStatus;
        String result = cgqStatus.getResult();
        if (result.equals("ok")) {
            //蜂鸣
            int buzzer = cgqStatus.getBuzzer();
            if (buzzer == 1) {
                //开
                mToggleFengming.setImageResource(R.drawable.dakaibaojing2);
            } else {
                //关
                mToggleFengming.setImageResource(R.drawable.dakaibaojing);
            }
            //光照
            int roadlamp = cgqStatus.getRoadlamp();
            if (roadlamp == 1) {
                //开
                mToggleGuangzhao.setImageResource(R.drawable.dakaiguangzhao2);
            } else {
                //关
                mToggleGuangzhao.setImageResource(R.drawable.dakaiguangzhao);
            }
        }
    }


    /**
     * 设备状态(范围)
     */
    public static void showData(Config config) {
        String result = config.getResult();
        if (result.equals("ok")) {
            mText2.setText("正常光照强度：" + config.getMinLight() + "~" + config.getMaxLight());
        }
    }

    /**
     * 显示数据（当前）
     */
    public static void showData(Sensor sensor) {
        if (sensor.result.equals("ok")) {
            mText1.setText("当前光照强度：" + sensor.light);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_sunny_guangzhao:
                //光照
                int roadlamp = status.getRoadlamp();
                if (roadlamp == 1) {
                    ContentActivity.control(Utils.KZ_DENGGUANG_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_DENGGUANG_KAI);
                }
                break;
            case R.id.frag_sunny_fengming:
                int buzzer = status.getBuzzer();
                //蜂鸣
                if (buzzer == 1) {
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                }
                break;

        }
    }
}
