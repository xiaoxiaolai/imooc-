package httpClient;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.File;
import java.io.InputStream;

import db.DatabaseHelper;
import okhttp3.OkHttpClient;


/**
 *
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //初始化单例的OkHttpClient对象
        initOkHttpUtils();

        //初始化单例的Picasso对象
        initGlide();

//        initOkHttpUtils();

//        initPicasso();
        initDatabase(DatabaseHelper.getInstance(this), DatabaseHelper.DATABASE_PATH,36);
    }


    private void initGlide() {
        //设置Glide网络访问方式
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(OkHttp3Utils.getOkHttpSingletonInstance()));

        //获取默认分配的磁盘缓存空间的大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(this);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        Log.d(TAG, "-->>defaultMemoryCacheSize: " + defaultMemoryCacheSize);
        Log.d(TAG, "-->>defaultBitmapPoolSize: " + defaultBitmapPoolSize);

    }



//    private void initPicasso() {
//        Picasso picasso = new Picasso.Builder(this)
//                .memoryCache(new LruCache(10<< 20 ))
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
//                .downloader(new MyDownLoader())
//                .indicatorsEnabled(true)
//                .build();
//    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpSingletonInstance();
        Log.i(TAG, "---->initOkHttpUtils: "  + okHttpClient.toString());
    }

//    /**
//     * 初始化单例OkHttpClient对象
//     */
//    private void initOkHttpUtils() {
//        OkHttpClient okHttpClient = OkHttpClientUtils.getOkHttpSingletonInstance();
//        Log.d(TAG, "---->initOkHttpUtils(): " + okHttpClient.toString());
//    }

    /**
     * 初始化单例Picasso对象
     */
//    private void initPicasso() {
//        //配置Picasso
//        Picasso picasso = new Picasso.Builder(this)
//                //设置内存缓存大小，10M
//                .memoryCache(new LruCache(10 << 20))
//                        //下载图片的格式，这样可以节省一半的内存
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
//                        //配置下载器，这里用的是OkHttp，必需单独加OkHttp，同时设置了磁盘缓存的位置和大小
//                //.downloader(new UrlConnectionDownloader())
//                //.downloader(new OkHttpDownloader())
//                .downloader(new MyDownLoader(getCacheDir(), 20 << 20))
//                        //设置图片左上角的标记
//                        //红色：代表从网络下载的图片
//                        //蓝色：代表从磁盘缓存加载的图片
//                        //绿色：代表从内存中加载的图片
//                .indicatorsEnabled(true)
//                //.loggingEnabled(true)
//                .build();
//        Picasso.setSingletonInstance(picasso);
//    }

    public void initDatabase(DatabaseHelper helper, String DATABASE_PATH, int newVersionCode){
        File f = new File(DATABASE_PATH);
        if (!f.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    DATABASE_PATH,null);
            db.setVersion(newVersionCode);
            helper.onCreate(db);
            db.close();
        }else{
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    DATABASE_PATH,null);
            int oldVersionCode=db.getVersion();
            Log.d(TAG, "initDatabase: "+oldVersionCode +"---------------"+ newVersionCode +"");
            if(newVersionCode>oldVersionCode){
                db.setVersion(newVersionCode);
                helper.onUpgrade(db, oldVersionCode, newVersionCode);
            }
            db.close();
        }
    }

}
