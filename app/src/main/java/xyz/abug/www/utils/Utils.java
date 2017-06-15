package xyz.abug.www.utils;

import android.util.Log;

import okhttp3.MediaType;

/**
 * Created by Dell on 2017/6/5.
 * 工具类
 */

public class Utils {
    /**
     * 各类控制代码
     */
    public static final String KZ_DENGGUANG_KAI = "{\"Roadlamp\":1}";
    public static final String KZ_DENGGUANG_GUAN = "{\"Roadlamp\":0}";

    public static final String KZ_SHUIBENG_KAI = "{\"WaterPump\":1}";
    public static final String KZ_SHUIBENG_GUAN = "{\"WaterPump\":0}";

    public static final String KZ_FENGMING_KAI = "{\"Buzzer\":1}";
    public static final String KZ_FENGMING_GUAN = "{\"Buzzer\":0}";

    public static final String KZ_FENGSHAN_KAI = "{\"Blower\":1}";
    public static final String KZ_FENGSHAN_GUAN = "{\"Blower\":0}";
    /**
     * POST传参格式类型
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 传感器数据广播
     */
    public static final String CAST_SENSOR = "xyz.abug.www.znyy";
    /**
     * 获取到阈值广播
     */
    public static final String CAST_CONFIG = "xyz.abug.www.znyy.yz";
    /**
     * 获取到广播
     */
    public static final String CAST_STATUS = "xyz.abug.www.znyy.status";
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
     * 设备的状态
     */
    public static final String URL_GET_STATUS = ":8890/type/jason/action/getContorllerStatus";
    /**
     * 控制协议
     */
    public static final String URL_SET_CONTROL = ":8890/type/jason/action/control";
    /**
     * 设置范围
     */
    public static final String URL_SET_MAXMIN = ":8890/type/jason/action/setConfig";
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
