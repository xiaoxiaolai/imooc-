//package httpClient;
//
//import android.util.Log;
//
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import java.io.IOException;
//
///**
// * Created by xiao on 2016/8/16.
// */
//
//public class httpClient {
//    public httpClient() {
//    }
//
//    static OkHttpClient client = new OkHttpClient();
//    private volatile int maxRequestCount = 10000;
//    int mTotalCount = 0;
//    int mErrorCount = 0;
//    int mSuccessCount = 0;
//    static double beginTime;
//
//    public static void getAsyncUrl() {
//        beginTime = System.currentTimeMillis();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 1; i++) {
//                        String response = asyncGet("http://blog.csdn.net/forevercbb/article/details/51037833");
//                        Log.d("tiny", " " + response);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    private static String asyncGet(String url) throws IOException {
//        final String[] result = new String[1];
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                result[0] = response.body().string();
//            }
//        });
//        return result[0];
//    }
//
//    public static void postToServer() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 1; i++) {
//                        beginTime = System.currentTimeMillis();
////                        Timestamp d;
////                        d = new Timestamp(System.currentTimeMillis(),new );
//                        String response = post("http://www.imooc.com/api3/articlelist","type=new&uid=0&page=1&token=1c34f7babe1f2bf4b40235b35bd9ae61");
//                        Log.d("tiny", "post response --" + response);
//                        Log.d("tiny", "post response --" +beginTime );
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//    }
////    timestamp=1471388238565&uid=0&page=1&token=7f75e24cb1f7e5c358f03a7b40a60976
//    private final static MediaType Json = MediaType.parse("application/json; charset=utf-8");
//
//    private static String post(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(Json, json);
//        FormEncodingBuilder localFormEncodingBuilder = new FormEncodingBuilder();
////        localFormEncodingBuilder.add("timestamp","1471388238563");
//            localFormEncodingBuilder.add("type","1");
//        localFormEncodingBuilder.add("page","5");
////        localFormEncodingBuilder.add("uid","0");
//        localFormEncodingBuilder.add("token","aec1e1fe6da2e8a4ba9d7b725d851f57");
//        Request request = new Request.Builder()
//                .addHeader("User-Agent", "mukewang/")
//                .url(url)
//                .post(localFormEncodingBuilder.build())
//                .build();
//        Response response = client.newCall(request).execute();
//
//        return response.body().string();
//    }
//
//}
//
//
