package xyz.abug.www.fragment;

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

import com.trycatch.mysnackbar.Prompt;

import xyz.abug.www.activity.SettingContentActivity;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.SetUtils;
import xyz.abug.www.utils.Utils;

/**
 * Created by Dell on 2017/6/6.
 * 空气设置
 */

public class SettingKqFragment extends Fragment implements View.OnClickListener {
    private View mView;
    public static EditText mWdMax, mWdMin, mSdMax, mSdMin;
    private static TextView mText1, mText2;
    private static ImageView mImg1, mImg2;
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting_kongqi, container, false);
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
        mWdMax = (EditText) mView.findViewById(R.id.frag_set_kq_wd_max);
        mSdMax = (EditText) mView.findViewById(R.id.frag_set_kq_sd_max);
        mWdMin = (EditText) mView.findViewById(R.id.frag_set_kq_wd_min);
        mSdMin = (EditText) mView.findViewById(R.id.frag_set_kq_sd_min);
        mText1 = (TextView) mView.findViewById(R.id.frag_set_kq_text1);//温度
        mText2 = (TextView) mView.findViewById(R.id.frag_set_kq_text2);//适度
        mImg1 = (ImageView) mView.findViewById(R.id.frag_set_kq_img1);
        mImg2 = (ImageView) mView.findViewById(R.id.frag_set_kq_img2);
        mBtn = (Button) mView.findViewById(R.id.frag_set_kq_btn);
        mBtn.setOnClickListener(this);

    }

    /**
     * 设置空气阈值
     */
    public static void showDataYZ(Config config) {
        mWdMax.setText(config.getMaxAirTemperature() + "");
        mWdMin.setText(config.getMinAirTemperature() + "");
        mSdMax.setText(config.getMaxAirHumidity() + "");
        mSdMin.setText(config.getMinAirHumidity() + "");
    }

    public static void showDataSJ(Sensor sensor) {
        mText1.setText(sensor.airTemperature + "");
        mText2.setText(sensor.airHumidity + "");
    }

    @Override
    public void onClick(View v) {
        String max_wd = mWdMax.getText().toString().trim();
        String min_wd = mWdMin.getText().toString().trim();
        String max_sd = mSdMax.getText().toString().trim();
        String min_sd = mSdMin.getText().toString().trim();
        if (TextUtils.isEmpty(max_wd) || TextUtils.isEmpty(min_wd) || TextUtils.isEmpty(max_sd) || TextUtils.isEmpty(min_sd)) {
//            Toast.makeText(getContext(), "请输入完整数据", Toast.LENGTH_SHORT).show();
            SettingContentActivity.showToast("请输入完整数据", Prompt.WARNING);
            return;
        }
        int iMax = Integer.parseInt(max_wd);
        int iMin = Integer.parseInt(min_wd);
        if (iMax <= iMin) {
//            Toast.makeText(getContext(), "数据不符合显示，请检查", Toast.LENGTH_SHORT).show();
            SettingContentActivity.showToast("数据不符合现实，请检查", Prompt.WARNING);
            return;
        }

        iMax = Integer.parseInt(max_sd);
        iMin = Integer.parseInt(min_sd);
        if (iMax <= iMin) {
//            Toast.makeText(getContext(), "数据不符合显示，请检查", Toast.LENGTH_SHORT).show();
            SettingContentActivity.showToast("数据不符合现实，请检查", Prompt.WARNING);
            return;
        }

        final String data = "{\"minAirTemperature\":" + min_wd + ",\"maxAirTemperature\":" + max_wd + ",\"minAirHumidity\":" + min_sd + ",\"maxAirHumidity\":" + max_sd + "}";
        Utils.logData("发送范围数据：" + data);
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
                            SettingContentActivity.showToast("设置成功", Prompt.SUCCESS);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    getActivity().finish();
                                }
                            }.start();
                        } else {
                            //设置失败
//                            Toast.makeText(getContext(), "设置失败，请再次尝试", Toast.LENGTH_SHORT).show();
                            SettingContentActivity.showToast("请再次尝试设置", Prompt.WARNING);
                        }
                    }
                });
            }
        }).start();

    }

    public static void setPic(Sensor mSensor, Config mConfig) {
        //设置空气温度
        boolean b1 = SetUtils.startCompare(mSensor.airTemperature, mConfig.getMinAirTemperature(), mConfig.getMaxAirTemperature());
        SetUtils.setPic(b1, mImg1);
        //设置空气适度
        boolean b2 = SetUtils.startCompare(mSensor.airHumidity, mConfig.getMinAirHumidity(), mConfig.getMaxAirHumidity());
        SetUtils.setPic(b2, mImg2);
    }
}
