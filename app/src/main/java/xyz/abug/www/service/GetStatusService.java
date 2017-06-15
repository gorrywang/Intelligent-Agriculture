package xyz.abug.www.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.mIP;

/**
 * Created by Dell on 2017/6/15.
 */

public class GetStatusService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.mIsGetData = true;
        getData();
    }

    boolean mBool = true;

    private void getData() {
        new Thread() {
            @Override
            public void run() {
                RequestBody body = new FormBody.Builder().add("", "").build();
                while (mBool) {
                    HttpUtils.sendQuestBackResponse(Utils.URL_GET_HTTP_HEAD + mIP + Utils.URL_GET_STATUS, body, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            Utils.logData("获取到设备状态：" + string);
//                            final CGQStatus cgqStatus = Utility.jsonStatus(string);
                            Intent intent = new Intent(Utils.CAST_STATUS);
                            intent.putExtra("asd", string);
                            sendBroadcast(intent);
                        }
                    });
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBool = false;
    }
}
