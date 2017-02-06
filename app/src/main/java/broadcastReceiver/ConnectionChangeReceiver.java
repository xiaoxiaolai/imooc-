package broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2016/9/4.
 */

public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static List<ReConnection> reConnections =new ArrayList<>();

    public static void registeredReConnection(ReConnection connection){

        reConnections.add(connection);
    }

    public static void unRegisteredReConnection(ReConnection connection){
        reConnections.remove(connection);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobNetInfo.isConnected()|| wifiNetInfo.isConnected()) {

            for (ReConnection connection:reConnections
                 ) {
                connection.connectionIng();
            }
            //改变背景或者 处理网络的全局变量
        }else {
            //改变背景或者 处理网络的全局变量
        }
    }
   public interface ReConnection{
        public void connectionIng();
    }
}
