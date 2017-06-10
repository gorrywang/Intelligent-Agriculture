package xyz.abug.www.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xyz.abug.www.activity.SettingActivity;
import xyz.abug.www.intelligentagriculture.R;

/**
 * Created by Dell on 2017/6/5.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private LinearLayout mLinearZD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindID();
    }

    /**
     * 绑定ID
     */
    private void bindID() {
        mLinearZD = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_zidong);
        mLinearZD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_setting_linear_zidong:
                //自动控制
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
