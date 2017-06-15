package xyz.abug.www.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * CO2
 */

public class CO2Fragment extends Fragment implements View.OnClickListener {

    private View mView;
    private static ImageView mToggleFengshan;
    private static ImageView mToggleFengming;
    private static TextView mText1;
    private static TextView mText2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_co2, container, false);
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
        mToggleFengshan = (ImageView) mView.findViewById(R.id.frag_co2_fengshan);
        mToggleFengming = (ImageView) mView.findViewById(R.id.frag_co2_fengming);
        mToggleFengshan.setOnClickListener(this);
        mToggleFengming.setOnClickListener(this);
        mText1 = (TextView) mView.findViewById(R.id.a_text_1);
        mText2 = (TextView) mView.findViewById(R.id.a_text_2);


    }

    private static CGQStatus status;

    /**
     * 显示数据（开关显示）
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
            //风扇
            int blower = cgqStatus.getBlower();
            if (blower == 1) {
                //开
                mToggleFengshan.setImageResource(R.drawable.dakaifengshan2);
            } else {
                //关
                mToggleFengshan.setImageResource(R.drawable.dakaifengshan);
            }
        }
    }

    /**
     * 显示数据（范围）
     */
    public static void showData(Config config) {
        String result = config.getResult();
        if (result.equals("ok")) {
            mText2.setText("CO2设定范围：" + config.getMinCo2() + "~" + config.getMaxCo2());
        }
    }

    /**
     * 显示数据（当前）
     */
    public static void showData(Sensor sensor) {
        if (sensor.result.equals("ok")) {
            mText1.setText("当前CO2浓度：" + sensor.co2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_co2_fengming:
                int buzzer = status.getBuzzer();
                //蜂鸣
                if (buzzer == 1) {
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                }
                break;
            case R.id.frag_co2_fengshan:
                int blower = status.getBlower();
                //风扇
                if (blower == 1) {
                    ContentActivity.control(Utils.KZ_FENGSHAN_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_FENGSHAN_KAI);
                }
                break;

        }
    }
}
