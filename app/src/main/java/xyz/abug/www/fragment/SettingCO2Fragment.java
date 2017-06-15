package xyz.abug.www.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.SetUtils;

/**
 * Created by Dell on 2017/6/6.
 */

public class SettingCO2Fragment extends Fragment implements View.OnClickListener {
    private View mView;
    private static EditText mMin;
    private static EditText mMax;
    private static TextView mText;
    private static ImageView mImg;
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting_co2, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindID();
    }

    /**
     * 设置图片
     */
    public static void setPic(Sensor mSensor, Config mConfig) {
        boolean b = SetUtils.startCompare(mSensor.co2, mConfig.getMinCo2(), mConfig.getMaxCo2());
        SetUtils.setPic(b,mImg);
    }

    /**
     * 绑定id
     */
    private void bindID() {
        mMax = (EditText) mView.findViewById(R.id.frag_set_co2_max);
        mMin = (EditText) mView.findViewById(R.id.frag_set_co2_min);
        mText = (TextView) mView.findViewById(R.id.frag_set_co2_text);
        mImg = (ImageView) mView.findViewById(R.id.frag_set_co2_img);
        mBtn = (Button) mView.findViewById(R.id.frag_set_co2_btn);
        mBtn.setOnClickListener(this);
    }

    /**
     * 设置阈值
     */
    public static void showDataYZ(Config config) {
        mMax.setText("" + config.getMaxCo2());
        mMin.setText("" + config.getMinCo2());
    }

    /**
     * 设置数据
     */
    public static void showDataSJ(Sensor sensor) {
        mText.setText(sensor.co2 + "");
    }


    @Override
    public void onClick(View v) {
        String max = mMax.getText().toString().trim();
        String min = mMin.getText().toString().trim();
        if (TextUtils.isEmpty(max) || TextUtils.isEmpty(min)) {
            Toast.makeText(getContext(), "请输入完整数据", Toast.LENGTH_SHORT).show();
            return;
        }
        int iMax = Integer.parseInt(max);
        int iMin = Integer.parseInt(min);
        if (iMax <= iMin) {
            Toast.makeText(getContext(), "数据不符合显示，请检查", Toast.LENGTH_SHORT).show();
            return;
        }
        final String data = "{\"minCo2\":" + min + ",\"maxCo2\":" + max + "}";
        //设置数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送设置
                final boolean bool = HttpUtils.controlMAXMIN(data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bool) {
                            //设置成功
                            getActivity().finish();
                        } else {
                            //设置失败
                            Toast.makeText(getContext(), "设置失败，请再次尝试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}
