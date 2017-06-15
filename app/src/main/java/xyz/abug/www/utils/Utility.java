package xyz.abug.www.utils;

import com.google.gson.Gson;

import xyz.abug.www.gson.CGQStatus;
import xyz.abug.www.gson.Config;
import xyz.abug.www.gson.Sensor;

/**
 * Created by Dell on 2017/6/14.
 * 解析数据
 */

public class Utility {

    /**
     * 解析传感器的数值
     */
    public static Sensor jsonSensor(String json) {
        Sensor sensor = new Gson().fromJson(json, Sensor.class);
        return sensor;
    }

    /**
     * 解析阈值的数值
     */
    public static Config jsonConfig(String json) {
        Config config = new Gson().fromJson(json, Config.class);
        return config;
    }

    /**
     * 解析传感器状态
     */
    public static CGQStatus jsonStatus(String json) {
        CGQStatus cgqStatus = new Gson().fromJson(json, CGQStatus.class);
        return cgqStatus;
    }
}
