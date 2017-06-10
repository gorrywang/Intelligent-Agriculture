package xyz.abug.www.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import xyz.abug.www.intelligentagriculture.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLinearCO2, mLinearSunny, mLinearKq, mLinearTr;
    private ImageButton mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bindID();

    }

    private void bindID() {
        mLinearCO2 = (LinearLayout) findViewById(R.id.setting_linear_co2);
        mLinearSunny = (LinearLayout) findViewById(R.id.setting_linear_sunny);
        mLinearKq = (LinearLayout) findViewById(R.id.setting_linear_kq);
        mLinearTr = (LinearLayout) findViewById(R.id.setting_linear_tr);
        mBtn = (ImageButton) findViewById(R.id.content_imgbtn_back);
        mLinearCO2.setOnClickListener(this);
        mLinearSunny.setOnClickListener(this);
        mLinearKq.setOnClickListener(this);
        mLinearTr.setOnClickListener(this);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = 1;
        switch (v.getId()) {
            case R.id.setting_linear_co2:
                //co2
                i = 1;
                break;
            case R.id.setting_linear_sunny:
                //光照
                i = 2;
                break;
            case R.id.setting_linear_kq:
                //空气
                i = 2;
                break;
            case R.id.setting_linear_tr:
                //土壤
                i = 3;
                break;
        }
        SettingContentActivity.activityJump(SettingActivity.this, i);
    }
}
