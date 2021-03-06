package xyz.abug.www.gson;

/**
 * Created by Dell on 2017/6/14.
 * 阈值数据
 * {"maxCo2":200,"result":"ok","maxLight":333,"minCo2":30,"minLight":10,"maxSoilHumidity":60,"minSoilHumidity":30,"minAirHumidity":30,"minAirTemperature":30,"maxAirHumidity":40,"maxAirTemperature":40,"controlAuto":0,"maxSoilTemperature":60,"minSoilTemperature":30,"maxPM25":500}
 */

public class Config {

    /**
     * maxCo2 : 200
     * result : ok
     * maxLight : 333
     * minCo2 : 30
     * minLight : 10
     * maxSoilHumidity : 60
     * minSoilHumidity : 30
     * minAirHumidity : 30
     * minAirTemperature : 30
     * maxAirHumidity : 40
     * maxAirTemperature : 40
     * controlAuto : 0
     * maxSoilTemperature : 60
     * minSoilTemperature : 30
     * maxPM25 : 500
     */

    private int maxCo2;
    private String result;
    private int maxLight;
    private int minCo2;
    private int minLight;
    private int maxSoilHumidity;
    private int minSoilHumidity;
    private int minAirHumidity;
    private int minAirTemperature;
    private int maxAirHumidity;
    private int maxAirTemperature;
    private int controlAuto;
    private int maxSoilTemperature;
    private int minSoilTemperature;
    private int maxPM25;

    public int getMaxCo2() {
        return maxCo2;
    }

    public void setMaxCo2(int maxCo2) {
        this.maxCo2 = maxCo2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMaxLight() {
        return maxLight;
    }

    public void setMaxLight(int maxLight) {
        this.maxLight = maxLight;
    }

    public int getMinCo2() {
        return minCo2;
    }

    public void setMinCo2(int minCo2) {
        this.minCo2 = minCo2;
    }

    public int getMinLight() {
        return minLight;
    }

    public void setMinLight(int minLight) {
        this.minLight = minLight;
    }

    public int getMaxSoilHumidity() {
        return maxSoilHumidity;
    }

    public void setMaxSoilHumidity(int maxSoilHumidity) {
        this.maxSoilHumidity = maxSoilHumidity;
    }

    public int getMinSoilHumidity() {
        return minSoilHumidity;
    }

    public void setMinSoilHumidity(int minSoilHumidity) {
        this.minSoilHumidity = minSoilHumidity;
    }

    public int getMinAirHumidity() {
        return minAirHumidity;
    }

    public void setMinAirHumidity(int minAirHumidity) {
        this.minAirHumidity = minAirHumidity;
    }

    public int getMinAirTemperature() {
        return minAirTemperature;
    }

    public void setMinAirTemperature(int minAirTemperature) {
        this.minAirTemperature = minAirTemperature;
    }

    public int getMaxAirHumidity() {
        return maxAirHumidity;
    }

    public void setMaxAirHumidity(int maxAirHumidity) {
        this.maxAirHumidity = maxAirHumidity;
    }

    public int getMaxAirTemperature() {
        return maxAirTemperature;
    }

    public void setMaxAirTemperature(int maxAirTemperature) {
        this.maxAirTemperature = maxAirTemperature;
    }

    public int getControlAuto() {
        return controlAuto;
    }

    public void setControlAuto(int controlAuto) {
        this.controlAuto = controlAuto;
    }

    public int getMaxSoilTemperature() {
        return maxSoilTemperature;
    }

    public void setMaxSoilTemperature(int maxSoilTemperature) {
        this.maxSoilTemperature = maxSoilTemperature;
    }

    public int getMinSoilTemperature() {
        return minSoilTemperature;
    }

    public void setMinSoilTemperature(int minSoilTemperature) {
        this.minSoilTemperature = minSoilTemperature;
    }

    public int getMaxPM25() {
        return maxPM25;
    }

    public void setMaxPM25(int maxPM25) {
        this.maxPM25 = maxPM25;
    }
}
