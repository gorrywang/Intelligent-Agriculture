package xyz.abug.www.gson;

/**
 * Created by Dell on 2017/6/14.
 * 传感器状态
 */

public class CGQStatus {

    /**
     * Buzzer : 0
     * result : ok
     * WaterPump : 0
     * Roadlamp : 0
     * Blower : 0
     */

    private int Buzzer;
    private String result;
    private int WaterPump;
    private int Roadlamp;
    private int Blower;

    public int getBuzzer() {
        return Buzzer;
    }

    public void setBuzzer(int Buzzer) {
        this.Buzzer = Buzzer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getWaterPump() {
        return WaterPump;
    }

    public void setWaterPump(int WaterPump) {
        this.WaterPump = WaterPump;
    }

    public int getRoadlamp() {
        return Roadlamp;
    }

    public void setRoadlamp(int Roadlamp) {
        this.Roadlamp = Roadlamp;
    }

    public int getBlower() {
        return Blower;
    }

    public void setBlower(int Blower) {
        this.Blower = Blower;
    }
}
