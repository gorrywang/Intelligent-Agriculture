package xyz.abug.www.utils;

import android.util.Log;

/**
 * Created by Dell on 2017/6/5.
 * 工具类
 */

public class Utils {
    /**
     * 传感器数据广播
     */
    public static final String CAST_SENSOR ="xyz.abug.www.znyy";
    /**
     * 获取到阈值广播
     */
    public static final String CAST_CONFIG ="xyz.abug.www.znyy.yz";
    /**
     * 程序结束后关闭获取数据
     */
    public static boolean mIsGetData = true;
    /**
     * 获取刷新的时间
     */
    public static int mAlertTime = 5;
    /**
     * 获取IP地址
     */
    public static String mIP = "";

    /**
     * http开头
     */
    public static final String URL_GET_HTTP_HEAD = "http://";
    /**
     * 获取传感器的数值
     * http://192.168.1.119:8890/type/jason/action/getSensor
     * {"result":"ok","airHumidity":53,"PM25":-1,"airTemperature":29,"soilTemperature":32,"co2":75,"soilHumidity":59,"light":1724}
     */
    public static final String URL_GET_SENSOR = ":8890/type/jason/action/getSensor";
//    public static final String URL_GET_SENSOR = "https://www.baidu.com/";
    /**
     * 获取传感器警告范围
     * http://10.44.1.104:8890/type/jason/action/getConfig
     * {"maxCo2":200,"result":"ok","maxLight":333,"minCo2":30,"minLight":10,"maxSoilHumidity":60,"minSoilHumidity":30,"minAirHumidity":30,"minAirTemperature":30,"maxAirHumidity":40,"maxAirTemperature":40,"controlAuto":0,"maxSoilTemperature":60,"minSoilTemperature":30,"maxPM25":500}
     */
    public static final String URL_GET_CONFIG = ":8890/type/jason/action/getConfig";
    /**
     * Log打印开关
     */
    public static boolean mISLog = true;

    /**
     * 打印数据Log
     */
    public static void logData(String logStr) {
        if (mISLog) {
            Log.e("Data", logStr);
        }
    }
}
