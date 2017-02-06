package service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.xiao.cui.Fragment.ThridFragment1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import broadcastReceiver.ConnectionChangeReceiver;
import dao.DownLoadLessonDao;
import entity.DownLoadChapterInfo;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * create by luoxiaoke on 2016/4/29 15:25.
 * use for 多线程 下载服务
 */
public class DownloadService2 extends Service implements ConnectionChangeReceiver.ReConnection{

    public static final int runThreadCount = 3;

    private static final String TAG = "DownloadService2";
    //初始化
    private static final int MSG_INIT = 0x2;
    //初始化
    public static final int MSG_FINSH = 0x4;
    //初始化
    public static final int MSG_PAUSE = 0x6;
    //开始下载
    public static final String ACTION_START = "ACTION_START_2";
    //开始下载
    public static final String ACTION_START_LIST = "ACTION_START_LIST";
    //暂停下载
    public static final String ACTION_PAUSE = "ACTION_PAUSE_2";
    //结束下载
    public static final String ACTION_FINISHED = "ACTION_FINISHED_2";
    //更新UI
    public static final String ACTION_UPDATE = "ACTION_UPDATE_2";
    //下载路径
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/imooc/";

    //下载任务集合
    private Map<Long, DownloadTask2> tasks = new LinkedHashMap<>();
    private List<DownloadTask2> tasksOrder = new ArrayList<>();
    private List<DownLoadChapterInfo> DownLoadChapterInfos = new ArrayList<>();
    private MyServerInterface serverInterface;
    public static boolean isReconnetion=false;

    private DownLoadLessonDao downLoadLessonDao;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        ConnectionChangeReceiver.registeredReConnection(this);
        downLoadLessonDao = new DownLoadLessonDao(this);
    }

    private void initRetrofit() {
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Log.i(TAG, "---->initRetrofit: " + client.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_imooc)
                .client(client)
                .build();
        serverInterface = retrofit.create(MyServerInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得Activity传来的参数
        if (ACTION_START.equals(intent.getAction())) {
            DownLoadChapterInfo mDownLoadChapterInfo = (DownLoadChapterInfo) intent.getSerializableExtra("fileinfo");
            removeTask(mDownLoadChapterInfo);
            initThead(mDownLoadChapterInfo);
            if (tasks.size() >= 2) {
                tasksOrder.get(0).isPause = true;
            }
        } else if (ACTION_PAUSE.equals(intent.getAction())) {
            DownLoadChapterInfo DownLoadChapterInfo = (DownLoadChapterInfo) intent.getSerializableExtra("fileinfo");
            //从集合在取出下载任务
            DownloadTask2 task2 = tasks.get(DownLoadChapterInfo.getId_());
            if (task2 != null) {
                //停止下载任务
                task2.isPause = true;
            }
            Intent mintent = new Intent(DownloadService2.ACTION_UPDATE);
            DownLoadChapterInfo.setIsPause(1);
            mintent.putExtra("fileinfo", DownLoadChapterInfo);
            sendBroadcast(mintent);
        } else if (ACTION_START_LIST.equals(intent.getAction())) {
            DownLoadChapterInfos.addAll((List<DownLoadChapterInfo>) intent.getSerializableExtra("fileinfo"));
            if (tasks.size() < 1) {
                initThead(DownLoadChapterInfos.get(0));
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void removeTask(DownLoadChapterInfo mDownLoadChapterInfo) {
        for (DownLoadChapterInfo info : DownLoadChapterInfos
                ) {
            if (info.getId_().equals(mDownLoadChapterInfo.getId_())) {
                mDownLoadChapterInfo = info;
            }
        }
        DownLoadChapterInfos.remove(mDownLoadChapterInfo);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINSH:

                    DownLoadChapterInfo Info = (DownLoadChapterInfo) msg.obj;
                    try {
                        List<entity.DownLoadLesson> lessons = downLoadLessonDao.getDao().queryForEq("_id",""+Info.getDownLoadLesson().getId_());
                        lessons.get(0).setDownloadnum(lessons.get(0).getDownloadnum()+1);
                        downLoadLessonDao.getDao().update(lessons.get(0));
                        Intent intent = new Intent(ThridFragment1.UPDATAE);
                        intent.putExtra("test",111);
                        Log.d(TAG, "handleMessage: -------------tasksOrder.size()-------" + "");
                       sendBroadcast(intent);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "handleMessage: --------------------" + tasksOrder.size());
                    Log.d(TAG, "handleMessage: --------------------" + tasks.size());
                    tasksOrder.remove(tasks.get(Info.getId_()));
                    tasks.remove(Info.getId_());
                    Log.d(TAG, "handleMessage: --------------------" + tasksOrder.size());
                    Log.d(TAG, "handleMessage: --------------------" + tasks.size());

                    removeTask(Info);
                    if (checkAllTaskOver()) {
                        closeSelf();
                    } else {
                        runTask();
                    }

                    break;
                case MSG_PAUSE:
                    DownLoadChapterInfo Info1 = (DownLoadChapterInfo) msg.obj;
                    Log.d(TAG, "handleMessage: --------------------" + tasksOrder.size());
                    Log.d(TAG, "handleMessage: --------------------" + tasks.size());
                    tasksOrder.remove(tasks.get(Info1.getId_()));
                    tasks.remove(Info1.getId_());
                    Log.d(TAG, "handleMessage: --------------------" + tasksOrder.size());
                    Log.d(TAG, "handleMessage: --------------------" + tasks.size());
                    removeTask(Info1);
                    if (checkAllTaskOver()) {
                        closeSelf();
                    } else {
                        runTask();
                    }
                    break;
            }
        }
    };

    private void runTask() {
        if (tasks.size() < 1) {
            initThead(DownLoadChapterInfos.get(0));
        }
    }

    private void closeSelf() {

//        stopSelf();
    }

    /**
     * @return
     */
    private boolean checkAllTaskOver() {
        if (DownLoadChapterInfos.size() == 0) {
            return true;
        } else {
            for (DownLoadChapterInfo info : DownLoadChapterInfos
                    ) {
                if (!info.isDownLoad() && info.getIsPause() != 1) {
                    return false;
                }
            }
            return true;
        }
    }

    private void initThead(DownLoadChapterInfo tDownLoadChapterInfo) {
        Intent intent = new Intent(DownloadService2.ACTION_UPDATE);
        tDownLoadChapterInfo.setIsPause(2);
        intent.putExtra("fileinfo", tDownLoadChapterInfo);
        sendBroadcast(intent);
        RandomAccessFile raf;
        try {
            File dir = new File(DOWNLOAD_PATH);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    return;
                }
            }
            //在本地创建文件
            File file = new File(dir,
                    tDownLoadChapterInfo.getChapterName() +
                            tDownLoadChapterInfo.getUrl().substring(tDownLoadChapterInfo.getUrl().lastIndexOf(".")));
            if (!file.exists()) {
                file.createNewFile();
            }
            raf = new RandomAccessFile(file, "rwd");
//                //设置本地文件长度
            raf.setLength(tDownLoadChapterInfo.getSize());
            raf.close();
            DownloadTask2 downloadTask2 = new DownloadTask2(serverInterface, mHandler, DownloadService2.this, tDownLoadChapterInfo, runThreadCount);
            tasks.put(tDownLoadChapterInfo.getId_(), downloadTask2);
            tasksOrder.add(downloadTask2);
            downloadTask2.download();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void connectionIng() {
       if(isReconnetion){
           isReconnetion=false;
           if (tasksOrder!=null&&tasksOrder.size()>0) {
            tasksOrder.get(0).download();
           }
       }
    }
}
