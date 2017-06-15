package xyz.abug.www.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import xyz.abug.www.intelligentagriculture.R;

/**
 * Created by Dell on 2017/6/15.
 * 图片比较
 */

public class SetUtils {

    /**
     * 设置图片
     */
    public static void setPic( boolean b, ImageView imageView) {
        if (b) {
            imageView.setImageResource(R.drawable.p1);
        } else {
            imageView.setImageResource(R.drawable.p2);
        }
    }

    /**
     * 开始判断图片
     */
    public static boolean startCompare(int now, int min, int max) {
        if (now >= min && now <= max) {
            //正常
            Utils.logData(now + "," + min + "," + max + "正常");
            return true;
        }
        Utils.logData(now + "," + min + "," + max + "不正常");
        return false;
    }
}
