package httpClient;

import okhttp3.OkHttpClient;

/**
 * Created by steven on 16/3/17.
 */
public class OkHttp3Utils {
    private static OkHttpClient okHttpClient = null;

    public  static OkHttpClient getOkHttpSingletonInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }
}
