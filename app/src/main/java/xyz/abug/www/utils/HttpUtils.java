package xyz.abug.www.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static xyz.abug.www.utils.Utils.JSON;
import static xyz.abug.www.utils.Utils.URL_SET_CONTROL;
import static xyz.abug.www.utils.Utils.mAlertTime;
import static xyz.abug.www.utils.Utils.mIP;

/**
 * Created by Dell on 2017/6/14.
 * 联网
 */

public class HttpUtils {
    /**
     * 获取数据
     *
     * @param mStrURL   链接地址
     * @param mCallback 回掉接口
     */
    public static void sendQuestBackResponse(String mStrURL, RequestBody requestBody, Callback mCallback) {
        Utils.logData("访问网址：" + mStrURL);
        //客户端
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(mAlertTime, TimeUnit.SECONDS).writeTimeout(mAlertTime, TimeUnit.SECONDS).connectTimeout(mAlertTime, TimeUnit.SECONDS).build();
        //请求
        Request request = new Request.Builder().url(mStrURL).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(mCallback);
    }


    /**
     * 控制传感器,必须在子线程中使用
     */
    public static boolean controlCGQ(String json) {
        RequestBody body = RequestBody.create(JSON, json);
        //客户端
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(mAlertTime, TimeUnit.SECONDS).writeTimeout(mAlertTime, TimeUnit.SECONDS).connectTimeout(mAlertTime, TimeUnit.SECONDS).build();
        //请求
        final Request request = new Request.Builder().url(Utils.URL_GET_HTTP_HEAD + mIP + URL_SET_CONTROL).post(body).build();
        //发送请求
        JSONObject jsonObject;
        try {
            Response execute = client.newCall(request).execute();
            String string = execute.body().string();
            jsonObject = new JSONObject(string);
            String result = jsonObject.getString("result");
            if (result.equals("ok")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断有无网络
     *
     * @param context 上下文
     * @return true成功 false失败
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm == null)) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
