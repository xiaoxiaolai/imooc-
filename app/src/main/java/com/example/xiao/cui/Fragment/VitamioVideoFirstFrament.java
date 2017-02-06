package com.example.xiao.cui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.xiao.cui.R;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BaseFragment.BaseFragment;
import dao.DownLoadChapterInfoDao;
import dao.DownLoadLessonDao;
import entity.ChapterInfo;
import entity.DownLoadChapterInfo;
import entity.DownLoadLesson;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import io.vov.vitamio.widget.VideoView;
import modle.VitamioChapterInfoAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.PinnedHeaderExpandableListView;

/**
 * Created by xiao on 2016/9/5.
 */

public class VitamioVideoFirstFrament extends BaseFragment implements PinnedHeaderExpandableListView.OnHeaderUpdateListener {
    private View viewempty;

    public void setCid(long cid) {
        this.cid = cid;
    }

    private long cid = 530;
    private Context mContext;

    public VitamioVideoFirstFrament() {

    }

    public VitamioVideoFirstFrament(long cid, Context context) {
        this.cid = cid;

        this.mContext=context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videofirst, container, false);
        initViews(view);
        initRetrofit();

        return view;

    }

    private PinnedHeaderExpandableListView listView;
    private VitamioChapterInfoAdapter adapter;
    private MyServerInterface serverInterface;
    private Call<ChapterInfo> call_cpinfo_ver2;
    private List<ChapterInfo.DataBean> lists;
    private List<DownLoadChapterInfo> chapterInfos;
    private VideoView videoview;


    private void initViews(View view) {
        listView = (ui.PinnedHeaderExpandableListView) view.findViewById(R.id.expandablelist);
        videoview = (VideoView) getActivity().findViewById(R.id.video_view);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(getContext(), "" + i + l, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                x = i;
                y = i1;
                ChapterInfo.DataBean.MediaBean mediaBean = (ChapterInfo.DataBean.MediaBean) adapter.getChild(i, i1);
//                Toast.makeText(getContext(),""+mediaBean.getMedia_url(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onChildClick: " + mediaBean.getMedia_down_url());
                videoview.setVideoURI(Uri.parse(mediaBean.getMedia_url()));
                adapter.uodata(i, i1);
                listView.collapseGroup(0);
                listView.expandGroup(0);

                return false;
            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int x, long l) {
//                Toast.makeText(getContext(),""+x+l,Toast.LENGTH_SHORT).show();
//            }
//        });
        viewempty = view.findViewById(R.id.View_empty);

    }

    int x = 0;
    int y = 0;

    public boolean updatavideo() {
        ChapterInfo.DataBean.MediaBean mediaBean =getMediaBean(x,y);

        if (mediaBean != null) {
            Log.d("------", "updatavideo: ------------------------------------"+mediaBean.getMedia_url());
            videoview.setVideoURI(Uri.parse(mediaBean.getMedia_url()));

            return true;
        }
        return false;
    }

    private ChapterInfo.DataBean.MediaBean getMediaBean(int i, int i1) {
        if (adapter.getChildrenCount(i) > i1 + 1) {
            y = i1 + 1;
            adapter.uodata(i, y);
            listView.collapseGroup(0);
            listView.expandGroup(0);
            return (ChapterInfo.DataBean.MediaBean) adapter.getChild(i, i1 + 1);
        }else if (adapter.getGroupCount() > i + 1) {
            x = i + 1;
            y=0;
            adapter.uodata(x, 0);
            listView.collapseGroup(0);
            listView.expandGroup(0);
            return (ChapterInfo.DataBean.MediaBean) adapter.getChild(i + 1, 0);
        }
        return null;
    }

    @Override
    public View getPinnedHeader() {
        return null;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {

    }

    private void initRetrofit() {
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Log.i("----------", "---->initRetrofit: " + client.toString());

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
                    DownLoadLessonDao downLoadLessonDao = new DownLoadLessonDao(getContext());
                    List<DownLoadLesson> infos = downLoadLessonDao.query("cid", String.valueOf(cid));
                    if (infos.size() != 0 && infos != null) {
                        DownLoadChapterInfoDao downLoadChapterInfoDao = new DownLoadChapterInfoDao(getContext());
                        QueryBuilder builder = downLoadChapterInfoDao.getDao().queryBuilder();
                        builder.orderBy("order", true).where().in("downLoadLesson_id", infos.get(0));
                        chapterInfos = builder.query();
                        Log.d("TAG", "onResponse: " + chapterInfos.get(0).getChapterName());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                adapter = new VitamioChapterInfoAdapter(lists, chapterInfos, mContext);

                if (lists.size() != 0) {
                    listView.setAdapter(adapter);
                    listView.setOnHeaderUpdateListener(VitamioVideoFirstFrament.this);
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

    }

    private Map<String, String> map;
    private Map<String, String> header;

    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();

        map.clear();
        map.put("token", "5006b52f29dfabc5e29338610bba08d0");
        map.put("cid", String.valueOf(cid));
        map.put("uid", String.valueOf(2932390));
        return map;
    }

    private Map<String, String> geHeader() {
        if (header == null)
            header = new HashMap<>();
        header.put("User-Agent", "mukewang/");
        return header;
    }
}
