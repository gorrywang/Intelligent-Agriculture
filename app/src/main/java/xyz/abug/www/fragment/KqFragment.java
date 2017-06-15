package xyz.abug.www.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import xyz.abug.www.activity.ContentActivity;
import xyz.abug.www.gson.CGQStatus;
import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

/**
 * Created by Dell on 2017/6/5.
 * 空气
 */

public class KqFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static ToggleButton mImgBtnFengshan;
    private static ToggleButton mImgBtnGuangzhao;
    private static ToggleButton mImgBtnFengming;
    private static ToggleButton mImgBtnShuibeng;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_kq, container, false);
        bindID();
        return mView;
    }

    /**
     * 绑定id
     */
    private void bindID() {
        mImgBtnGuangzhao = (ToggleButton) mView.findViewById(R.id.frag_kq_guangzhao);
        mImgBtnFengming = (ToggleButton) mView.findViewById(R.id.frag_kq_fengming);
        mImgBtnFengshan = (ToggleButton) mView.findViewById(R.id.frag_kq_fengshan);
        mImgBtnShuibeng = (ToggleButton) mView.findViewById(R.id.frag_kq_shuibeng);
        mImgBtnGuangzhao.setOnCheckedChangeListener(this);
        mImgBtnFengming.setOnCheckedChangeListener(this);
        mImgBtnFengshan.setOnCheckedChangeListener(this);
        mImgBtnShuibeng.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getActivity(), isChecked + "", Toast.LENGTH_SHORT).show();
        int id = buttonView.getId();
        if (isChecked) {
            switch (id) {
                case R.id.frag_kq_guangzhao:
                    //光照
                    ContentActivity.control(Utils.KZ_DENGGUANG_KAI);
                    break;
                case R.id.frag_kq_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                    break;
                case R.id.frag_kq_fengshan:
                    //风扇
                    ContentActivity.control(Utils.KZ_FENGSHAN_KAI);
                    break;
                case R.id.frag_kq_shuibeng:
                    //水泵
                    ContentActivity.control(Utils.KZ_SHUIBENG_KAI);
                    break;

            }
        } else {
            switch (id) {
                case R.id.frag_kq_guangzhao:
                    //光照
                    ContentActivity.control(Utils.KZ_DENGGUANG_GUAN);
                    break;
                case R.id.frag_kq_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                    break;
                case R.id.frag_kq_fengshan:
                    //风扇
                    ContentActivity.control(Utils.KZ_FENGSHAN_GUAN);
                    break;
                case R.id.frag_kq_shuibeng:
                    //水泵
                    ContentActivity.control(Utils.KZ_SHUIBENG_GUAN);
                    break;

            }
        }
    }

    /**
     * 设备状态
     */
    public static void showData(CGQStatus cgqStatus) {
        String result = cgqStatus.getResult();
        if (result.equals("ok")) {
            //蜂鸣
            int buzzer = cgqStatus.getBuzzer();
            if (buzzer == 1) {
                //开
                mImgBtnFengming.setChecked(true);
            } else {
                //关
                mImgBtnFengming.setChecked(false);
            }
            //水泵
            int waterPump = cgqStatus.getWaterPump();
            if (waterPump == 1) {
                //开
                mImgBtnShuibeng.setChecked(true);
            } else {
                //关
                mImgBtnShuibeng.setChecked(false);
            }
            //风扇
            int blower = cgqStatus.getBlower();
            if (blower == 1) {
                //开
                mImgBtnFengshan.setChecked(true);
            } else {
                //关
                mImgBtnFengshan.setChecked(false);
            }
            //灯光
            int roadlamp = cgqStatus.getRoadlamp();
            if (roadlamp == 1) {
                mImgBtnGuangzhao.setChecked(true);
            } else {
                mImgBtnGuangzhao.setChecked(false);
            }
        }
    }
}
