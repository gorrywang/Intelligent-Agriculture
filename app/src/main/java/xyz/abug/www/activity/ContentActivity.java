package xyz.abug.www.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import xyz.abug.www.fragment.CO2Fragment;
import xyz.abug.www.fragment.KqFragment;
import xyz.abug.www.fragment.SunnyFragment;
import xyz.abug.www.fragment.TrFragment;
import xyz.abug.www.intelligentagriculture.R;

public class ContentActivity extends AppCompatActivity {

    private Fragment mFragmentCO2, mFragmentSunny, mFragmentTr, mFragmentKq;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private static int mInt;
    private TextView mTextTitle;
    private ImageButton mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        bindID();
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        switch (mInt) {
            case 1:
                //CO2浓度
                mFragmentCO2 = new CO2Fragment();
                mTransaction.add(R.id.content_frame_load, mFragmentCO2);
                mTextTitle.setText("CO2 详 情");
                break;
            case 2:
                //光照强度
                mFragmentSunny = new SunnyFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentSunny);
                mTextTitle.setText("光 照 详 情");
                break;
            case 3:
                //土壤指数
                mFragmentTr = new TrFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentTr);
                mTextTitle.setText("土 壤 详 情");
                break;
            case 4:
                //空气指数
                mFragmentKq = new KqFragment();
                mTransaction.add(R.id.content_frame_load, mFragmentKq);
                mTextTitle.setText("空 气 详 情");
                break;
        }
        mTransaction.commit();
    }

    private void bindID() {
        mTextTitle = (TextView) findViewById(R.id.context_text_title);
        mBtnBack = (ImageButton) findViewById(R.id.content_imgbtn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param context 上下文
     * @param i       页面
     */
    public static void ActivityJump(Activity context, int i) {
        Intent intent = new Intent(context, ContentActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mInt = i;
    }
}

