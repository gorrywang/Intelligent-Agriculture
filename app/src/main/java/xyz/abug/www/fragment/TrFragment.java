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
 * 土壤
 */

public class TrFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private static ImageView mImgBtnGuangzhao;
    private static ImageView mImgBtnFengming;
    private static ImageView mImgBtnShuibeng;

    private static TextView mText1, mText2, mText3, mText4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tr, container, false);
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
        mImgBtnGuangzhao = (ImageView) mView.findViewById(R.id.frag_tr_guangzhao);
        mImgBtnFengming = (ImageView) mView.findViewById(R.id.frag_tr_fengming);
        mImgBtnShuibeng = (ImageView) mView.findViewById(R.id.frag_tr_shuibeng);
        mText1 = (TextView) mView.findViewById(R.id.c_text_1);
        mText2 = (TextView) mView.findViewById(R.id.c_text_2);
        mText3 = (TextView) mView.findViewById(R.id.c_text_3);
        mText4 = (TextView) mView.findViewById(R.id.c_text_4);
        mImgBtnGuangzhao.setOnClickListener(this);
        mImgBtnFengming.setOnClickListener(this);
        mImgBtnShuibeng.setOnClickListener(this);
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
                mImgBtnFengming.setImageResource(R.drawable.dakaibaojing2);
            } else {
                //关
                mImgBtnFengming.setImageResource(R.drawable.dakaibaojing);
            }
            //光照
            int blower = cgqStatus.getRoadlamp();
            if (blower == 1) {
                //开
                mImgBtnGuangzhao.setImageResource(R.drawable.dakaiguangzhao2);
            } else {
                //关
                mImgBtnGuangzhao.setImageResource(R.drawable.dakaiguangzhao);
            }
            //水泵
            int waterPump = cgqStatus.getWaterPump();
            if (waterPump == 1) {
                //开
                mImgBtnShuibeng.setImageResource(R.drawable.dakaishui2);
            } else {
                //关
                mImgBtnShuibeng.setImageResource(R.drawable.dakaishui);
            }
        }
    }

    /**
     * 显示数据（当前）
     */
    public static void showData(Sensor sensor) {
        if (sensor.result.equals("ok")) {
            mText1.setText("当前土壤温度：" + sensor.soilTemperature);
            mText2.setText("当前土壤湿度：" + sensor.soilHumidity);
        }
    }

    /**
     * 设备状态(范围)
     */
    public static void showData(Config config) {
        String result = config.getResult();
        if (result.equals("ok")) {
            //温度
            mText3.setText("温度设定范围：" + config.getMinSoilTemperature() + "~" + config.getMaxSoilTemperature());
            //湿度
            mText4.setText("湿度设定范围：" + config.getMinSoilHumidity() + "~" + config.getMaxSoilHumidity());
        }
    }

    @Override
    public void onClick(View v) {
        if (status == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.frag_tr_guangzhao:
                //光照
                int roadlamp = status.getRoadlamp();
                if (roadlamp == 1) {
                    ContentActivity.control(Utils.KZ_DENGGUANG_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_DENGGUANG_KAI);
                }
                break;
            case R.id.frag_tr_fengming:
                //蜂鸣
                int buzzer = status.getBuzzer();
                if (buzzer == 1) {
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                }
                break;
            case R.id.frag_tr_shuibeng:
                //水泵
                int waterPump = status.getWaterPump();
                if (waterPump == 1) {
                    ContentActivity.control(Utils.KZ_SHUIBENG_GUAN);
                } else {
                    ContentActivity.control(Utils.KZ_SHUIBENG_KAI);
                }
                break;

        }
    }
}
