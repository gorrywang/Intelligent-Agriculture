package xyz.abug.www.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.abug.www.utils.HttpUtils;
import xyz.abug.www.utils.Utils;

import static xyz.abug.www.utils.Utils.CAST_CONFIG;
import static xyz.abug.www.utils.Utils.CAST_SENSOR;
import static xyz.abug.www.utils.Utils.URL_GET_CONFIG;
import static xyz.abug.www.utils.Utils.URL_GET_HTTP_HEAD;
import static xyz.abug.www.utils.Utils.URL_GET_SENSOR;
import static xyz.abug.www.utils.Utils.mIP;

/**
 * Created by Dell on 2017/6/14.
 */

public class GetJsonServer extends Service {


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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.mIsGetData = false;
    }

    private void getData() {
        new Thread() {
            @Override
            public void run() {
                RequestBody body = new FormBody.Builder().add("", "").build();
                while (Utils.mIsGetData) {
                    //获取传感器数据
                    HttpUtils.sendQuestBackResponse(URL_GET_HTTP_HEAD + mIP + URL_GET_SENSOR, body, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Utils.logData("传感器数值：后台获取数据错误");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();
                            Utils.logData("传感器数值：后台获取数据：" + data);
                            Intent intent = new Intent(CAST_SENSOR);
                            intent.putExtra("data", data);
                            sendBroadcast(intent);
                        }
                    });

                    //获取传感器数据
                    HttpUtils.sendQuestBackResponse(URL_GET_HTTP_HEAD + mIP + URL_GET_CONFIG, body, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Utils.logData("传感器阈值：后台获取数据错误");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();
                            Utils.logData("传感器阈值：后台获取数据：" + data);
                            Intent intent = new Intent(CAST_CONFIG);
                            intent.putExtra("data", data);
                            sendBroadcast(intent);
                        }
                    });

                    //睡眠
                    try {
                        sleep(Utils.mAlertTime * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
}
