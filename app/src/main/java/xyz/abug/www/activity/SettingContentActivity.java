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

import xyz.abug.www.fragment.SettingCO2Fragment;
import xyz.abug.www.fragment.SettingSunnyFragment;
import xyz.abug.www.fragment.SettingTrFragment;
import xyz.abug.www.intelligentagriculture.R;

public class SettingContentActivity extends AppCompatActivity {
    private static int count = 0;
    private FragmentTransaction mTransaction;
    private FragmentManager mManager;
    private Fragment mFr;
    private ImageButton mImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_content);
        mImgBtn = (ImageButton) findViewById(R.id.setting_content_imgbtn_back);
        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        switch (count) {
            case 1:
                //CO2
                mFr = new SettingCO2Fragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);

                break;
            case 2:
                //光照
                mFr = new SettingSunnyFragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
            case 3:
                //土壤
                mFr = new SettingTrFragment();
                mTransaction.add(R.id.setting_content_frame_load, mFr);
                break;
        }
        mTransaction.commit();

    }

    public static void activityJump(Activity context, int i) {
        Intent intent = new Intent(context, SettingContentActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        count = i;
    }
}
