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
 * 土壤
 */

public class TrFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    private View mView;
    private static ToggleButton  mImgBtnGuangzhao;
    private static ToggleButton mImgBtnFengming;
    private static ToggleButton mImgBtnShuibeng;

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
        mImgBtnGuangzhao = (ToggleButton) mView.findViewById(R.id.frag_tr_guangzhao);
        mImgBtnFengming = (ToggleButton) mView.findViewById(R.id.frag_tr_fengming);
        mImgBtnShuibeng = (ToggleButton) mView.findViewById(R.id.frag_tr_shuibeng);
        mImgBtnGuangzhao.setOnCheckedChangeListener(this);
        mImgBtnFengming.setOnCheckedChangeListener(this);
        mImgBtnShuibeng.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getActivity(), isChecked + "", Toast.LENGTH_SHORT).show();
        int id = buttonView.getId();
        if (isChecked) {
            switch (id) {
                case R.id.frag_tr_guangzhao:
                    //光照
                    ContentActivity.control(Utils.KZ_DENGGUANG_KAI);
                    break;
                case R.id.frag_tr_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                    break;
                case R.id.frag_tr_shuibeng:
                    //水泵
                    ContentActivity.control(Utils.KZ_SHUIBENG_KAI);
                    break;

            }
        } else {
            switch (id) {
                case R.id.frag_tr_guangzhao:
                    //光照
                    ContentActivity.control(Utils.KZ_DENGGUANG_GUAN);
                    break;
                case R.id.frag_tr_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                    break;
                case R.id.frag_tr_shuibeng:
                    //水泵
                    ContentActivity.control(Utils.KZ_SHUIBENG_GUAN);
                    break;

            }
        }
    }

    /**
     * 设备 状态
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
