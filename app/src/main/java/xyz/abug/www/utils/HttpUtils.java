package xyz.abug.www.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS).writeTimeout(3000, TimeUnit.SECONDS).connectTimeout(3000, TimeUnit.SECONDS).build();
        //请求
        Request request = new Request.Builder().url(mStrURL).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(mCallback);
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
