package xyz.abug.www.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import xyz.abug.www.activity.SettingActivity;
import xyz.abug.www.intelligentagriculture.R;

/**
 * Created by Dell on 2017/6/5.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private LinearLayout mLinearZD, mLinearQC, mLinearBB, mLinearTc, mLinearGy, mLinearData, mLinearLb;
    private ImageView mImage;

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
        mImage = (ImageView) mView.findViewById(R.id.frag_setting_img_right);
        mLinearZD = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_zidong);
        mLinearQC = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_qingchu);
        mLinearBB = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_banben);
        mLinearTc = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_tuichu);
        mLinearGy = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_guanyu);
        mLinearData = (LinearLayout) mView.findViewById(R.id.frag_setting_linear_data);
        mLinearLb = (LinearLayout) mView.findViewById(R.id.aaa_bbb);
        mLinearZD.setOnClickListener(this);
        mLinearQC.setOnClickListener(this);
        mLinearBB.setOnClickListener(this);
        mLinearTc.setOnClickListener(this);
        mLinearGy.setOnClickListener(this);
    }


    /**
     * 返回键监听
     */
    private boolean mBackBool = true;

    /**
     * 关于我们判定值
     */
    private boolean mGyBool = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_setting_linear_zidong:
                //自动控制
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.frag_setting_linear_qingchu:
                //清除数据
                System.gc();
                TSnackbar.make(mView, "数据已清除", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                break;
            case R.id.frag_setting_linear_banben:
                TSnackbar.make(mView, "当前已是最新版本", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                break;
            case R.id.frag_setting_linear_tuichu:
                //退出
                if (mBackBool) {
                    TSnackbar.make(mView, "再次点击退出软件", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                    mBackBool = false;
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mBackBool = true;

                        }
                    }.start();
                    break;
                } else {
                    //退出
                    getActivity().finish();
                }
                break;
            case R.id.frag_setting_linear_guanyu:
                //关于
                if (mGyBool) {
                    //要关闭
                    mGyBool = false;
                    //旋转图标
                    xzFalse(mImage);
                    mLinearData.setVisibility(View.GONE);
                    mLinearLb.setVisibility(View.VISIBLE);
                } else {
                    //要打开
                    mGyBool = true;
                    //旋转图标
                    xzTrue(mImage);
                    mLinearData.setVisibility(View.VISIBLE);
                    mLinearLb.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void xzTrue(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 90.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void xzFalse(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(90.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }
}
