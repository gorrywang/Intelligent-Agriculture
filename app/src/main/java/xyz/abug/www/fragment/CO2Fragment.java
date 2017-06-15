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
 * CO2
 */

public class CO2Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private View mView;
    private static ToggleButton mToggleFengshan;
    private static ToggleButton mToggleFengming;

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
        mToggleFengshan = (ToggleButton) mView.findViewById(R.id.frag_co2_fengshan);
        mToggleFengming = (ToggleButton) mView.findViewById(R.id.frag_co2_fengming);
        mToggleFengshan.setOnCheckedChangeListener(this);
        mToggleFengming.setOnCheckedChangeListener(this);


    }

    /**
     * 点击
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getActivity(), isChecked + "", Toast.LENGTH_SHORT).show();
        int id = buttonView.getId();
        if (isChecked) {
            switch (id) {
                case R.id.frag_co2_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_KAI);
                    break;
                case R.id.frag_co2_fengshan:
                    //风扇
                    ContentActivity.control(Utils.KZ_FENGSHAN_KAI);
                    break;

            }
        } else {
            switch (id) {
                case R.id.frag_co2_fengming:
                    //蜂鸣
                    ContentActivity.control(Utils.KZ_FENGMING_GUAN);
                    break;
                case R.id.frag_co2_fengshan:
                    //风扇
                    ContentActivity.control(Utils.KZ_FENGSHAN_GUAN);
                    break;

            }
        }
    }

    public static void showData(CGQStatus cgqStatus) {
        String result = cgqStatus.getResult();
        if (result.equals("ok")) {
            //蜂鸣
            int buzzer = cgqStatus.getBuzzer();
            if (buzzer == 1) {
                //开
                mToggleFengming.setChecked(true);
            } else {
                //关
                mToggleFengming.setChecked(false);
            }
            //风扇
            int blower = cgqStatus.getBlower();
            if (blower == 1) {
                //开
                mToggleFengshan.setChecked(true);
            } else {
                //关
                mToggleFengshan.setChecked(false);
            }
        }
    }
}
