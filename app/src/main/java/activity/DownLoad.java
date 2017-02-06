package activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiao.cui.R;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DownLoadChapterInfoDao;
import entity.CourseInfor;
import entity.DownLoadChapterInfo;
import entity.DownLoadLesson;
import scroll.SwipeBackControllerLayout;
import service.DownloadService2;
import ui.DownLoadAdapter;

/**
 * Created by xiao on 2016/8/29.
 */

public class DownLoad extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ListView listView;
    private Button checkAllBox;
    private Button checkDownLoad;
    private boolean isCheckAll = false;
    private DownLoadAdapter adapter;
    private CourseInfor mCourseInfor = null;
    private List<DownLoadChapterInfo> chapterInfos;
    private DownLoadLesson downLoadLesson;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=(SwipeBackControllerLayout) LayoutInflater.from(this).inflate(R.layout.activity_second_1,null);
        layout.attachToActivity(this);
        setContentView(R.layout.download_list_main_child);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initView();
        initEvents();
        initRegister();
//        layout = (SwipeBackControllerLayout) findViewById(R.id.xxx);
//        layout.init(this);
//        layout.setBackGround(null);
    }

    protected SwipeBackControllerLayout layout;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


            return super.onTouchEvent(ev);

    }
    private void initEvents() {
//        checkAllBox.setOnClickListener(this);
//        checkDownLoad.setOnClickListener(this);
    }


    private void initView() {
        listView = (ListView) findViewById(R.id.download_list_main_lesson);
        listView.setItemsCanFocus(true);
        downLoadLesson = (DownLoadLesson) getIntent().getSerializableExtra("listobj");
        if (downLoadLesson != null) {

            try {
                DownLoadChapterInfoDao downLoadChapterInfoDao = new DownLoadChapterInfoDao(this);
                QueryBuilder builder = downLoadChapterInfoDao.getDao().queryBuilder();
                builder.orderBy("order",true).where().in("downLoadLesson_id", downLoadLesson);
//                chapterInfos = downLoadChapterInfoDao.query("downLoadLesson_id", String.valueOf(downLoadLesson.getId_()));
//                chapterInfos = downLoadChapterInfoDao.queryAll();
                chapterInfos = builder.query();
                Map<Long,DownLoadChapterInfo> map =new HashMap<>();
                if(chapterInfos.size()!=0){
                    for (DownLoadChapterInfo info:chapterInfos
                         ) {
                        map.put(info.getId_(),info);
                    }
                }
                Toast.makeText(this, "----"+chapterInfos.get(0).getChapterName(), Toast.LENGTH_SHORT)
                        .show();
                adapter = new DownLoadAdapter(map,chapterInfos, this);
                listView.setAdapter(adapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "lllll", Toast.LENGTH_SHORT)
                    .show();
        }

//        checkAllBox = (Button) findViewById(R.id.check_all_box);
//        checkDownLoad = (Button) findViewById(R.id.check_download);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.check_all_box:
//
//                break;
//            case R.id.check_download:
//
//                break;
        }
    }

    private void initRegister() {
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService2.ACTION_UPDATE);
        filter.addAction(DownloadService2.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    private String TAG="download";
    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService2.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                long id = intent.getLongExtra("id", 0);
                Log.e(TAG, "finished==" + finished);
                Log.e(TAG, "id==" + id);
                DownLoadChapterInfo fileinfo = (DownLoadChapterInfo) intent.getSerializableExtra("fileinfo");
                adapter.updateProgress(fileinfo, fileinfo.getFinish());
                //progressBar.setProgress(finished);
            } else if (DownloadService2.ACTION_FINISHED.equals(intent.getAction())) {
                DownLoadChapterInfo fileinfo = (DownLoadChapterInfo) intent.getSerializableExtra("fileinfo");
                //更新进度为100
                adapter.updateProgress(fileinfo, fileinfo.getFinish());
                Toast.makeText(DownLoad.this,
                        fileinfo.getChapterName() + "下载完成",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
