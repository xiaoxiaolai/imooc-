package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import entity.DownLoadChapterInfo;
import entity.DownLoadLesson;

/**
 * Created by StevenWang on 16/3/20.
 */
public class MySQLiteOpenHelper extends OrmLiteSqliteOpenHelper {
    //定义数据库名称
    private static final String DB_NAME = "db_DownLoadLessons.db";
    //定义数据库版本号
    private static final int DB_VERSION = 1;
    private static MySQLiteOpenHelper dbHelper = null;
    private Dao<DownLoadLesson, Long> DownLoadLessonDao = null;
    private Dao<DownLoadChapterInfo, Long> DownLoadChapterInfoDao = null;

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //单例获取数据库工具类
    public static MySQLiteOpenHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new MySQLiteOpenHelper(context);
        }
        return dbHelper;
    }

    //获取表的访问控制对象
    public Dao<DownLoadLesson, Long> getDownLoadLessonDao() throws SQLException {
        if (DownLoadLessonDao == null) {
            DownLoadLessonDao = getDao(DownLoadLesson.class);
        }
        return DownLoadLessonDao;
    }
    //获取表的访问控制对象
    public Dao<DownLoadChapterInfo, Long> getLoadChapterInfoDao() throws SQLException {
        if (DownLoadChapterInfoDao == null) {
            DownLoadChapterInfoDao = getDao(DownLoadChapterInfo.class);
        }
        return DownLoadChapterInfoDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            database.setVersion(DB_VERSION);
            TableUtils.createTableIfNotExists(connectionSource, DownLoadLesson.class);
            TableUtils.createTableIfNotExists(connectionSource, DownLoadChapterInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                TableUtils.dropTable(connectionSource, DownLoadLesson.class, true);
                TableUtils.dropTable(connectionSource, DownLoadChapterInfo.class, true);
                onCreate(database, connectionSource);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
