package xyz.abug.www.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xyz.abug.www.intelligentagriculture.R;
import xyz.abug.www.utils.Utils;

public class SetIPActivity extends Activity {

    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);
        mEdit = (EditText) findViewById(R.id.set_ip_edit_ip);
    }

    public void onCommit(View view) {
        String trim = mEdit.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(SetIPActivity.this, "请输入IP", Toast.LENGTH_SHORT).show();
            return;
        }
        Utils.mIP = trim;
        finish();
    }
}
