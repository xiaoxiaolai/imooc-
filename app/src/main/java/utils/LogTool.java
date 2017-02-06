package utils;

import android.util.Log;

import java.sql.SQLException;

/**
 * Created by xiao on 2016/8/30.
 */
public class LogTool {
    public static void i(String name, String onCreate) {
        Log.i(name, "i: " + onCreate);
    }

    public static void e(String name, String s, SQLException e) {

            Log.e(name, "e: " + s, e);

    }
    public static void e(String name, String s) {
            Log.e(name, "e: " + s, null);


    }
}
