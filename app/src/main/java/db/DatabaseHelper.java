package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;

import entity.DownLoadChapterInfo;
import entity.DownLoadLesson;
import entity.ThreadInfo;
import utils.LogTool;
import utils.SDCardHelper;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something
    // appropriate for your app
    private static final String DATABASE_NAME = "IMOOC.db";

    // any time you make changes to your database objects, you may have to
    // increase the database version
    private static final int DATABASE_VERSION = 5;

    // the DAO object we use to access the SimpleData table

    //数据库默认路径SDCard
    public static String DATABASE_PATH = Environment.getExternalStorageDirectory()
            + "/IMOOC.db";

    private Context mContext;
    //数据库配置文件默认路径SDCard
    private static String DATABASE_PATH_JOURN = Environment.getExternalStorageDirectory()
            + "/IMOOC.db-journal";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        initDtaBasePath();
        try {

            File f = new File(DATABASE_PATH);
            if (!f.exists()) {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
                db.setVersion(DATABASE_VERSION);
                onCreate(db);
                db.close();
            }
        } catch (Exception e) {
        }
    }
    private static DatabaseHelper dbHelper = null;

    //单例获取数据库工具类
    public static DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
    }
    //如果没有SDCard 默认存储在项目文件目录下
    private void initDtaBasePath() {
        if (!SDCardHelper.isSDCardMounted()) {
            DATABASE_PATH = mContext.getFilesDir().getAbsolutePath() + "/IMOOC.db";
            DATABASE_PATH_JOURN = mContext.getFilesDir().getAbsolutePath() + "/IMOOC.db-journal";
        }
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * This is called when the database is first created. Usually you should
     * call createTable statements here to create the tables that will store
     * your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        LogTool.i(DatabaseHelper.class.getName(), "onCreate");
        try {
//            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, DownLoadLesson.class);
            TableUtils.createTableIfNotExists(connectionSource, DownLoadChapterInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, ThreadInfo.class);
        } catch (java.sql.SQLException e) {
            LogTool.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }



    /**
     * This is called when your application is upgraded and it has a higher
     * version number. This allows you to adjust the various data to match the
     * new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        LogTool.i(DatabaseHelper.class.getName(), "onUpgrade");
        try {
            db.setVersion(newVersion);
            TableUtils.dropTable(connectionSource, DownLoadLesson.class, true);
            TableUtils.dropTable(connectionSource, DownLoadChapterInfo.class, true);
            TableUtils.dropTable(connectionSource, ThreadInfo.class, true);

            onCreate(db, connectionSource);
        } catch (java.sql.SQLException e) {
            LogTool.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public void deleteDB() {
        if (mContext != null) {
            File f = mContext.getDatabasePath(DATABASE_NAME);
            if (f.exists()) {
                // mContext.deleteDatabase(DATABASE_NAME);
                LogTool.e("DB", "---delete SDCard DB---");
                f.delete();
            } else {
                LogTool.e("DB", "---delete App DB---");
                mContext.deleteDatabase(DATABASE_NAME);
            }

            File file = mContext.getDatabasePath(DATABASE_PATH);
            if (file.exists()) {
                LogTool.e("DB", "---delete SDCard DB 222---");
                file.delete();
            }

            File file2 = mContext.getDatabasePath(DATABASE_PATH_JOURN);
            if (file2.exists()) {
                LogTool.e("DB", "---delete SDCard DB 333---");
                file2.delete();
            }
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }



}