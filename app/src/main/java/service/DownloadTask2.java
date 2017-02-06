package service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.DownLoadChapterInfoDao;
import dao.ThreadInfoDao;
import entity.DownLoadChapterInfo;
import entity.ThreadInfo;
import httpClient.MyServerInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static ui.MyAdapter.TAG;

/**
 * create by luoxiaoke on 2016/4/29 16:45.
 * use for 下载任务类
 */
public class DownloadTask2 {
    private final Handler mHander;
    private final ThreadInfoDao mThreadDAO;
    private final MyServerInterface serverInterface;
    private Context mContext = null;
    private DownLoadChapterInfo mDownLoadChapterInfo = null;
    private DownLoadChapterInfoDao mDownLoadChapterInfoDao = null;
    private long mFinished = 0;
    private int mThreadCount = DownloadService2.runThreadCount;
    public boolean isPause = false;
    //线程池
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    private List<DownloadThread2> mThradList = null;
    private Map<String, String> map;

    public DownloadTask2(MyServerInterface serverInterface, Handler mHandler, Context mContext, DownLoadChapterInfo mDownLoadChapterInfo, int threadCount) {
        this.mContext = mContext;
        this.mDownLoadChapterInfo = mDownLoadChapterInfo;
        this.mDownLoadChapterInfoDao = new DownLoadChapterInfoDao(mContext);
        this.mThreadDAO = new ThreadInfoDao(mContext);
        this.mThreadCount = threadCount;
        this.mHander = mHandler;
        this.serverInterface = serverInterface;

    }

    public void download() {
        //读取数据库的线程信息
        List<ThreadInfo> ThreadInfos = new ArrayList<>();


        try {
            List<entity.DownLoadChapterInfo> infos = mDownLoadChapterInfoDao.query("_id", String.valueOf(mDownLoadChapterInfo.getId_()));
            mFinished += infos.get(0).getFinish();
            ThreadInfos = mThreadDAO.query("url", mDownLoadChapterInfo.getUrl());
            Log.e("threadsize==", ThreadInfos.size() + "");
            if (ThreadInfos.size() == 0) {
                //获得每个线程下载的长度
                long length = mDownLoadChapterInfo.getSize() / mThreadCount;
                for (int i = 0; i < mThreadCount; i++) {
                    ThreadInfo mThreadInfo = new ThreadInfo(mDownLoadChapterInfo.getUrl(), length * i, (i + 1) * length - 1, 0);
                    if (i + 1 == mThreadCount) {
                        mThreadInfo.setEnd(mDownLoadChapterInfo.getSize());
                    }
//                添加到线程信息集合中
                    ThreadInfos.add(mThreadInfo);
//                向数据库插入线程信息
                    mThreadDAO.save(mThreadInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mThradList = new ArrayList<>();
        //启动多个线程进行下载
        for (ThreadInfo thread : ThreadInfos) {
            DownloadThread2 downloadThread = new DownloadThread2(thread);
            DownloadTask2.sExecutorService.execute(downloadThread);
            //添加线程到集合中
            mThradList.add(downloadThread);
        }
    }


    /**
     * 下载线程
     */
    class DownloadThread2 extends Thread {
        private ThreadInfo threadInfo;
        public boolean isFinished = false;
        private Call<ResponseBody> call_bm = null;

        public DownloadThread2(ThreadInfo DownLoadChapterInfo) {
            this.threadInfo = DownLoadChapterInfo;
        }

        private Map<String, String> getMap(long start, long end) {
            if (map == null)
                map = new HashMap<>();
            map.clear();
            map.put("Range", "bytes=" + start + "-" + end);
            return map;
        }

        @Override
        public void run() {
            //向数据库插入线程信息
//            Log.e("isExists==", mDownLoadChapterInfoDao.isExists(threadInfo.getUrl(), threadInfo.getId()) + "");
//            if (!mDownLoadChapterInfoDao.isExists(threadInfo.getUrl(), threadInfo.getId())) {
//                mDownLoadChapterInfoDao.insertThread(threadInfo);
//            }
            HttpURLConnection connection;
            Response<ResponseBody> response = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
//                URL url = new URL(threadInfo.getUrl());
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setConnectTimeout(3000);
//                connection.setRequestMethod("GET");
                //设置下载位置
                long start = threadInfo.getStart() + threadInfo.getFinish();
//                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
//                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService2.DOWNLOAD_PATH, mDownLoadChapterInfo.getChapterName() +
                        threadInfo.getUrl().substring(threadInfo.getUrl().lastIndexOf(".")));
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent(DownloadService2.ACTION_UPDATE);

                Log.e("DownLoad.getFinish==", threadInfo.getFinish() + "");
                call_bm = serverInterface.getNetworkDataAsync(threadInfo.getUrl(), getMap(start, threadInfo.getEnd()));
                response = call_bm.execute();

                //开始下载
//                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                if (response.code() == HttpURLConnection.HTTP_PARTIAL) {
//                    Log.e("getContentLength==", connection.getContentLength() + "");

                    //读取数据
                    is = response.body().byteStream();
//                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    while ((len = is.read(buffer)) != -1) {

                        if (isPause) {
                            Log.e("mfinished==pause===", mFinished + "");
                            //下载暂停时，保存进度到数据库
                            mDownLoadChapterInfo.setIsPause(1);
                            mDownLoadChapterInfoDao.update(mDownLoadChapterInfo);
                            mThreadDAO.update(threadInfo);
                            intent.putExtra("fileinfo", mDownLoadChapterInfo);
                            mHander.obtainMessage(DownloadService2.MSG_PAUSE, mDownLoadChapterInfo).sendToTarget();
                            mContext.sendBroadcast(intent);
                            return;
                        }

                        //写入文件
                        raf.write(buffer, 0, len);
                        //累加整个文件下载进度
                        mFinished += len;

                        //累加每个线程完成的进度
                        threadInfo.setFinish(threadInfo.getFinish() + len);
                        mDownLoadChapterInfo.setFinish(mFinished);
                        //每隔1秒刷新UI
                        if (System.currentTimeMillis() - time > 1000) {//减少UI负载
                            time = System.currentTimeMillis();
                            //把下载进度发送广播给Activity
                            mDownLoadChapterInfo.setIsPause(2);
                            intent.putExtra("id", mDownLoadChapterInfo.getId_());
//                            intent.putExtra("finished", mFinished * 100 / mDownLoadChapterInfo.getSize());
                            intent.putExtra("finished", mFinished);
                            intent.putExtra("fileinfo", mDownLoadChapterInfo);
                            mContext.sendBroadcast(intent);
                            Log.e(" mFinished==update==", mFinished * 100 / mDownLoadChapterInfo.getSize() + "");
                        }

                    }
                    //标识线程执行完毕
                    isFinished = true;
                    //检查下载任务是否完成
                    checkAllThreadFinished();
//                    //删除线程信息
                    mThreadDAO.delete(threadInfo);
                    is.close();
                }
                raf.close();
                response.body().close();
//                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (is != null)
                        is.close();
                    if (raf != null)
                        raf.close();
                    if (response != null)
                        response.body().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }finally {
                try {
                    DownloadService2.isReconnetion = true;
                    if(!isPause){
                    mDownLoadChapterInfo.setIsPause(0);
                    Intent intent = new Intent(DownloadService2.ACTION_UPDATE);
                    mDownLoadChapterInfoDao.update(mDownLoadChapterInfo);
                    mThreadDAO.update(threadInfo);
                    intent.putExtra("fileinfo", mDownLoadChapterInfo);
//                      mHander.obtainMessage(DownloadService2.MSG_PAUSE, mDownLoadChapterInfo).sendToTarget();
                    mContext.sendBroadcast(intent);}
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        }

    }

    /**
     * 判断所有线程是否都执行完毕
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;
        //编辑线程集合 判断是否执行完毕
        for (DownloadThread2 thread : mThradList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if (allFinished) {
            //删除线程信息
//            mDownLoadChapterInfoDao.deleteThread(mDownLoadChapterInfo.getUrl());
            //发送广播给Activity下载结束
            Intent intent = new Intent(DownloadService2.ACTION_FINISHED);
            intent.putExtra("fileinfo", mDownLoadChapterInfo);
            try {
                mDownLoadChapterInfo.setDownLoad(true);
                mDownLoadChapterInfoDao.update(mDownLoadChapterInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mContext.sendBroadcast(intent);
            Log.d(TAG, "checkAllThreadFinished: ----------------------------");
            mHander.obtainMessage(DownloadService2.MSG_FINSH, mDownLoadChapterInfo).sendToTarget();
        }
    }

}

