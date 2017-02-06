package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiao.cui.R;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DownLoadChapterInfoDao;
import dao.DownLoadLessonDao;
import entity.ChapterInfo;
import entity.CourseInfor;
import entity.DownLoadChapterInfo;
import entity.DownLoadLesson;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import modle.DownLoadChapterInfoAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.DownloadService2;
import ui.PinnedHeaderExpandableListView;

/**
 * Created by xiao on 2016/8/29.
 */

public class DownLoadChapter extends AppCompatActivity implements PinnedHeaderExpandableListView.OnHeaderUpdateListener, View.OnClickListener {
    private Toolbar toolbar;
    private PinnedHeaderExpandableListView listView;
    private MyServerInterface serverInterface;
    private String TAG = "DownLoadChapter";
    private Call<ChapterInfo> call_cpinfo_ver2;
    private Call<CourseInfor> courseInfor;
    private Map<String, String> map;
    private Map<String, String> header;
    private List<ChapterInfo.DataBean> lists;
    private View viewempty;
    private Button checkAllBox;
    private Button checkDownLoad;
    private boolean isCheckAll = false;
    private DownLoadChapterInfoAdapter adapter;
    private CourseInfor mCourseInfor = null;
    private DownLoadLesson downLoadLesson;
    private List list;
    private List<DownLoadChapterInfo> chapterInfos;
    private DownLoadLessonDao downLoadLessonDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        setContentView(R.layout.download_list_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initRetrofit();
        initEvents();
    }

    private void initEvents() {
        checkAllBox.setOnClickListener(this);
        checkDownLoad.setOnClickListener(this);
    }

    private int cid;

    private void initIntent() {
        cid = getIntent().getIntExtra("id", 200);

    }

    private void initView() {
        listView = (PinnedHeaderExpandableListView) findViewById(R.id.download_list_main);
        viewempty = findViewById(R.id.View_empty);
        checkAllBox = (Button) findViewById(R.id.check_all_box);
        checkDownLoad = (Button) findViewById(R.id.check_download);

    }

    private void initRetrofit() {
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Log.i(TAG, "---->initRetrofit: " + client.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_imooc)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverInterface = retrofit.create(MyServerInterface.class);
        call_cpinfo_ver2 = serverInterface.postCpinfo_ver2(getMap(), geHeader());
        call_cpinfo_ver2.enqueue(new Callback<ChapterInfo>() {
            @Override
            public void onResponse(Call<ChapterInfo> call, Response<ChapterInfo> response) {
//                List<CourseListModel.ItemsEntity> list = response.body().getItems();
                lists = response.body().getData();
                // adapter执行刷新ListView

                try {
                    DownLoadLessonDao downLoadLessonDao = new DownLoadLessonDao(DownLoadChapter.this);
                    List<entity.DownLoadLesson> infos=downLoadLessonDao.query("cid",String.valueOf(cid));
                    if(infos.size()!=0&&infos!=null){
                    DownLoadChapterInfoDao downLoadChapterInfoDao = new DownLoadChapterInfoDao(DownLoadChapter.this);
                    QueryBuilder builder = downLoadChapterInfoDao.getDao().queryBuilder();
                    builder.orderBy("order",true).where().in("downLoadLesson_id", infos.get(0));
//                chapterInfos = downLoadChapterInfoDao.query("downLoadLesson_id", String.valueOf(downLoadLesson.getId_()));
//                chapterInfos = downLoadChapterInfoDao.queryAll();

                    chapterInfos = builder.query();
                        Log.d(TAG, "onResponse: "+chapterInfos.get(0).getChapterName());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                adapter = new DownLoadChapterInfoAdapter(lists, chapterInfos,DownLoadChapter.this);

//                adapter.reloadData(lists, false);
                if (lists.size() != 0) {
                    listView.setAdapter(adapter);
                    listView.setOnHeaderUpdateListener(DownLoadChapter.this);
                    for (int i = 0, count = listView.getCount(); i < count; i++) {
                        listView.expandGroup(i);
                    }
                    viewempty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChapterInfo> call, Throwable t) {

            }
        });
        courseInfor = serverInterface.postCourseInfor(getMap1(), geHeader());
        courseInfor.enqueue(new Callback<CourseInfor>() {
            @Override
            public void onResponse(Call<CourseInfor> call, Response<CourseInfor> response) {
                mCourseInfor = response.body();
            }

            @Override
            public void onFailure(Call<CourseInfor> call, Throwable t) {

            }
        });

    }

    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();

        map.clear();
        map.put("token", "5006b52f29dfabc5e29338610bba08d0");
        map.put("cid", String.valueOf(cid));
        map.put("uid", String.valueOf(2932390));
        return map;
    }


    private Map<String, String> getMap1() {
        if (map == null)
            map = new HashMap<>();

        map.clear();
        map.put("token", " eb70172dd3bf80e5ff13ef23dee9daec");
        map.put("cid", String.valueOf(cid));
        map.put("uid", String.valueOf(2932390));
        return map;
    }

    private Map<String, String> geHeader() {
//        builder.addHeader("User-Agent", "mukewang/");
        if (header == null)
            header = new HashMap<>();


        header.put("User-Agent", "mukewang/");
//        map.put("page", String.valueOf(page));
//        map.put("uid", String.valueOf(0));
//        map.put("timestamp", "1471647902981");
//
////        map.put("type", String.valueOf(type));
//        page++;
        return header;
    }

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.group, null);
//        headerView.setPadding(10,10,10,10);
        headerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        String firstVisibleGroup = ((ChapterInfo.DataBean.ChapterBean) adapter.getGroup(firstVisibleGroupPos)).getName();
        Log.d(TAG, "updatePinnedHeader: " + firstVisibleGroup);
//        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
//        textView.setPadding(20,20,20,20);
        Log.e(TAG, "updatePinnedHeader: " + textView.getText());

        textView.setText(firstVisibleGroup);
        Log.e(TAG, "updatePinnedHeader: " + textView.getText());
//        textView.setTextColor(Color.GREEN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_all_box:
                if (listView.getCount() != 0) {
                    if (!isCheckAll) {
                        adapter.checkAllBox();
                        isCheckAll = true;
                        checkAllBox.setText("全不选");
                        Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.size());
                    } else {
                        adapter.checkAllBoxCancle();
                        isCheckAll = false;
                        checkAllBox.setText("全选");
                        Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.size());
                    }

                    listView.collapseGroup(0);
                    listView.expandGroup(0);
                }
                break;
            case R.id.check_download:
                Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.size());
                if (adapter.isCheckMap.size() == 0) {
                    Toast.makeText(this, "未选中下载项", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.size());
                    if (mCourseInfor != null) {
                        try {

                            downLoadLessonDao= new DownLoadLessonDao(this);
                           downLoadLesson=  new DownLoadLesson();
                            downLoadLesson.setLessonName(mCourseInfor.getData().getName());
                            downLoadLesson.setPic(mCourseInfor.getData().getPic());
                            downLoadLesson.setCid(cid);
                            downLoadLessonDao.getDao().create(downLoadLesson);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        DownLoadChapterInfoDao downLoadChapterInfoDao = new DownLoadChapterInfoDao(this);
                        int y=0;
                        Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.size());
                        List<DownLoadChapterInfo> infos= new ArrayList<>();
                        for (int x = 0; x < adapter.isCheckMap.size(); x++) {
                            y++;
                            long i=  Long.valueOf(adapter.isCheckMap.get(x))/100;
                            long j=  Long.valueOf(adapter.isCheckMap.get(x))%100;
                            DownLoadChapterInfo info =new DownLoadChapterInfo();
                            Log.d(TAG, "onClick: ------------------------------------"+adapter.isCheckMap.get(x));
                            info.setOrder(adapter.isCheckMap.get(x));
                            info.setChapterName((i+1)+"-"+(j+1)+" "+lists.get((int)i).getMedia().get((int)j).getName());
                            info.setDownLoadLesson(downLoadLesson);
                            info.setSize(lists.get((int)i).getMedia().get((int)j).getMedia_down_size());
                            info.setUrl(lists.get((int)i).getMedia().get((int)j).getMedia_down_url());
//                            if(y%2==0){
//                            info.setDownLoad(true);}
//                            info.setIsread(true);
                            try {
                                downLoadChapterInfoDao.save(info);
                                infos.add(info);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        downLoadLesson.setNum(y);
                        try {
                            downLoadLessonDao.update(downLoadLesson);
                            if (infos.size()>0){
                                Intent intent = new Intent(this, DownloadService2.class);
                                intent.setAction(DownloadService2.ACTION_START_LIST);
                                intent.putExtra("fileinfo", (Serializable) infos);
                                startService(intent);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        finish();
                        Log.d(TAG, "onClick: ------------------------------------"+y);

                    }
                }
                break;
        }
    }
}
