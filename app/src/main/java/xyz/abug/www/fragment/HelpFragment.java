package xyz.abug.www.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.abug.www.activity.MainActivity;
import xyz.abug.www.intelligentagriculture.R;

/**
 * Created by Dell on 2017/6/5.
 */

public class HelpFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private LinearLayout mLinearData , mLinearDh;
    private TextView mText1 ,mText2,mText3,mText4 ,mTextData;
    private ImageView mImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_help, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindID();
    }

    private void bindID() {
        mImg = (ImageView) mView.findViewById(R.id.frag_help_img_close);
        mLinearData = (LinearLayout) mView.findViewById(R.id.frag_help_linear_data);
        mLinearDh = (LinearLayout) mView.findViewById(R.id.frag_help_linear_dh);
        mText1 = (TextView) mView.findViewById(R.id.frag_help_text_1);
        mText2 = (TextView) mView.findViewById(R.id.frag_help_text_2);
        mText3 = (TextView) mView.findViewById(R.id.frag_help_text_3);
        mText4 = (TextView) mView.findViewById(R.id.frag_help_text_4);
        mTextData = (TextView) mView.findViewById(R.id.frag_help_text_data);
        mText1.setOnClickListener(this);
        mText2.setOnClickListener(this);
        mText3.setOnClickListener(this);
        mText4.setOnClickListener(this);
        mImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mLinearData.setVisibility(View.VISIBLE);
//        mLinearDh.setVisibility(View.GONE);
        mText1.setEnabled(false);
        mText2.setEnabled(false);
        mText3.setEnabled(false);
        mText4.setEnabled(false);
        MainActivity.closeTitle();
        switch (v.getId()) {
            case R.id.frag_help_text_1:
                //第一条指引
                mTextData.setText("我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|我是第一条指引|");
                break;

            case R.id.frag_help_text_2:
                //第二条指引
                mTextData.setText("我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|我是第二条指引|");
                break;

            case R.id.frag_help_text_3:
                //第三条指引
                mTextData.setText("我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|我是第三条指引|");
                break;

            case R.id.frag_help_text_4:
                //第四条指引
                mTextData.setText("我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|我是第四条指引|");
                break;

            case R.id.frag_help_img_close:
                //返回
                mLinearData.setVisibility(View.GONE);
//                mLinearDh.setVisibility(View.VISIBLE);
                MainActivity.showTitle();
                mText1.setEnabled(true);
                mText2.setEnabled(true);
                mText3.setEnabled(true);
                mText4.setEnabled(true);
                break;
        }
    }

}
