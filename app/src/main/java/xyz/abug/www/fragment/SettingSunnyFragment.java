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

/**
 * Created by Dell on 2017/6/6.
 */

public class SettingSunnyFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private static EditText mMax;
    private static EditText mMin;
    private static TextView mText;
    private static ImageView mImg;
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting_sunny, container, false);
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
        mMax = (EditText) mView.findViewById(R.id.frag_set_sunny_max);
        mMin = (EditText) mView.findViewById(R.id.frag_set_sunny_min);
        mText = (TextView) mView.findViewById(R.id.frag_set_sunny_text);
        mImg = (ImageView) mView.findViewById(R.id.frag_set_sunny_img);
        mBtn = (Button) mView.findViewById(R.id.frag_set_sunny_btn);
        mBtn.setOnClickListener(this);
    }

    public static void showDataYZ(Config config) {
        mMax.setText("" + config.getMaxLight() + "");
        mMin.setText("" + config.getMinLight() + "");
    }

    public static void showDataSJ(Sensor sensor) {
        mText.setText(sensor.light + "");
    }


    @Override
    public void onClick(View v) {
        String max = mMax.getText().toString().trim();
        String min = mMin.getText().toString().trim();
        if (TextUtils.isEmpty(max) || TextUtils.isEmpty(min)) {
//            Toast.makeText(getContext(), "请输入完整数据", Toast.LENGTH_SHORT).show();
            SettingContentActivity.showToast("请输入完整数据", Prompt.WARNING);
            return;
        }
        int iMax = Integer.parseInt(max);
        int iMin = Integer.parseInt(min);
        if (iMax <= iMin) {
//            Toast.makeText(getContext(), "数据不符合显示，请检查", Toast.LENGTH_SHORT).show();
            SettingContentActivity.showToast("数据不符合现实，请检查", Prompt.WARNING);
            return;
        }
        final String data = "{\"minLight\":" + min + ",\"maxLight\":" + max + "}";
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
        boolean b = SetUtils.startCompare(mSensor.light, mConfig.getMinLight(), mConfig.getMaxLight());
        SetUtils.setPic(b, mImg);
    }
}
