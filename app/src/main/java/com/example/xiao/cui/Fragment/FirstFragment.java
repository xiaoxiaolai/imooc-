package com.example.xiao.cui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiao.cui.R;
import com.example.xiao.cui.bananaView.AdLoopView;
import com.example.xiao.cui.bananaView.internal.BaseLoopAdapter;
import com.example.xiao.cui.bananaView.internal.LoopData;
import com.example.xiao.cui.bananaView.utils.JsonTool;
import com.example.xiao.cui.interface_.ToolBar;
import com.example.xiao.cui.utils.LocalFileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BaseFragment.BaseFragment;
import activity.CoursesType;
import activity.Position;
import activity.VitamioVideoPlayerActivity1;
import entity.CourseListModel;
import entity.MediaInfo;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.LoadListView;
import ui.MyAdapter;
import ui.MyScrollView;
import ui.PullToRefreshView;
import utils.ScreenShot;

import static com.example.xiao.cui.R.id.toolbar;


/**
 * Created by xiao on 2016/8/9.
 */

public class FirstFragment extends BaseFragment implements BaseLoopAdapter.OnItemClickListener,
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, LoadListView.ILoadListener, MyScrollView.ILoadListener {

    private AdLoopView mLoopView;
    private LoadListView listView;
    private MyAdapter adapter;
    private List<CourseListModel.ItemsEntity> lists = new ArrayList<CourseListModel.ItemsEntity>();
    private View tv;
    public Context context;
    private int page = 1, type = 1;
    private Map<String, String> map;
    private Map<String, String> header;
    private Call<CourseListModel> call_qiushi;
    private Call<MediaInfo> mediaInfoCall;
    private View viewempty;
    private PullToRefreshView pull_to_refresh_view;
    private MyScrollView myscrollview;
    private View footer;
    private boolean isfist = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRetrofit();
//        toolBar = (ToolBar) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        tv = inflater.inflate(R.layout.activity_main1, container, false);
        toolBar = (ToolBar) getActivity();
        // App Logo
        ((Toolbar) tv.findViewById(toolbar)).setLogo(R.mipmap.head_image_0);
// Title
        ((Toolbar) tv.findViewById(toolbar)).setTitle("My Title");
// Sub Title
//        ((Toolbar) tv.findViewById(toolbar)).setSubtitle("Sub title");
        toolBar.initToolbar((Toolbar) tv.findViewById(toolbar));
//         ((Toolbar) tv.findViewById(toolbar)).setNavigationIcon(R.mipmap.head_image_0);


        Log.d("TAG", "onCreateView: " + toolBar);
        initViews(inflater);
        initEvents();

        return tv;

    }


    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();

        map.clear();
        map.put("token", "7f75e24cb1f7e5c358f03a7b40a60976");
        map.put("page", String.valueOf(page));
        map.put("uid", String.valueOf(0));
        map.put("timestamp", "1471647902981");

//        map.put("type", String.valueOf(type));
        page++;
        return map;
    }

    private Map<String, String> getMap1(int cid) {
        if (map == null)
            map = new HashMap<>();
        map.clear();
        map.put("token", "9bb4b17710b70a920a1a121cba84c1c2");
        map.put("cid", String.valueOf(cid));
        map.put("uid", String.valueOf(0));
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

    //    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) tv.findViewById(R.id.toolbar);
//        if (toolbar != null) {
//
////             getActivity().setSupportActionBar(toolbar);
//        }
//    }
//gson解析
    private CourseListModel parseJsonToCourseListModel(String jsonString) {
        Gson gson = new Gson();
        CourseListModel model = gson.fromJson(jsonString,
                new TypeToken<CourseListModel>() {
                }.getType());
        return model;
    }

    private void initViews(LayoutInflater inflater) {
        mLoopView = (AdLoopView) tv.findViewById(R.id.adloop_act_adloopview);
        listView = (LoadListView) tv.findViewById(R.id.ListViewss);

        listView.setInterface(this);
        pull_to_refresh_view = (PullToRefreshView) tv.findViewById(R.id.pull_to_refresh_view);
        pull_to_refresh_view.setOnFooterRefreshListener(this);
        pull_to_refresh_view.setOnHeaderRefreshListener(this);
        myscrollview = (MyScrollView) tv.findViewById(R.id.myscrollview);
        myscrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (myscrollview.getChildAt(0).getMeasuredHeight() <= myscrollview
                        .getHeight() + myscrollview.getScrollY()) {

                    Log.i("Main", "滑动到底部");
                    Log.i("Main",
                            "scroll.getChildAt(0).getMeasuredHeight()="
                                    + myscrollview.getChildAt(0)
                                    .getMeasuredHeight()
                                    + "scroll,getHeight()="
                                    + myscrollview.getHeight()
                                    + "scroll.getScrollY()="
                                    + myscrollview.getScrollY());
//                            tv.append(getResources().getString(R.string.content));

                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        /**
                         * (1)getScrollY()————滚动条滑动的距离 (2)getMeasuredHeight()
                         * (3)getHeight()
                         */

                        // 顶部状态
                        if (myscrollview.getScrollY() <= 0) {
                            Log.i("Main", "滑动到顶部");
                        }

                        // 底部状态
                        // TextView的总高度<=一屏幕的高度+滚动条的滚动距离

                        break;
                    }
                }
                return false;
            }
        });

        myscrollview.setInterface(this);

        viewempty = tv.findViewById(R.id.View_empty);
//        listView.setEmptyView(LayoutInflater.from(context).inflate(R.layout.adapter_empty_view,null));
//        listView.setInterface(this);
        initRotateView();
        if (lists.size() == 0 || lists == null) {
//            loadDates();
        }
        adapter = new MyAdapter(lists, context, listView);
//        footer=LayoutInflater.from(context).inflate(R.layout.footer_layout,null);
//        footer.setVisibility(View.GONE);
//        listView.addFooterView(footer);
        listView.setAdapter(adapter);
//        adapter.reloadData(lists, true);

//        fixListViewHeight(listView);
        View view = tv.findViewById(R.id.jobline);
        View view2 = tv.findViewById(R.id.jobline2);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStart) {
                    isStart = true;
                    Intent intent = new Intent(getContext(), CoursesType.class);
                    startActivity(intent);

                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                httpClient.postToServer();

                initDates();

            }
        });
//        footer = tv.findViewById(R.id.footer);
//        footer.setVisibility(View.GONE);
//        LayoutInflater.from(context).inflate(R.layout.footer_layout,null).setVisibility(View.GONE);
    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private static final String TAG = "FirstFragment";
    private MyServerInterface serverInterface = null;


    private void initRetrofit() {
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Log.i(TAG, "---->initRetrofit: " + client.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_imooc)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverInterface = retrofit.create(MyServerInterface.class);
        call_qiushi = serverInterface.postCourselist_ver2(getMap(), geHeader());
        call_qiushi.enqueue(new Callback<CourseListModel>() {
            @Override
            public void onResponse(Call<CourseListModel> call, Response<CourseListModel> response) {
//                List<CourseListModel.ItemsEntity> list = response.body().getItems();
                lists = response.body().getItems();
                // adapter执行刷新ListView
                adapter.reloadData(lists, false);
                if (lists.size() != 0) {
                    viewempty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CourseListModel> call, Throwable t) {

            }
        });
    }

    private void initDates() {
        if (!isStart) {
            isStart = true;
            Intent intent = new Intent(getContext(), Position.class);
            startActivity(intent);

        }

    }

    //
    private void loadDates() {
        for (int i = 0; i < 15; i++) {
            CourseListModel.ItemsEntity course = new CourseListModel.ItemsEntity();
            course.setName("nihaoma" + i);
            course.setPic("text");
            lists.add(course);
        }
    }

    /**
     * 初始化LoopView
     *
     * @return void
     * @date 2015-10-9 21:32:12
     */
    private void initRotateView() {
        // 设置自定义布局
//        mLoopView.setLoopLayout(R.layout.ad_loopview_layout);
        // 设置数据
        String json = LocalFileUtils.getStringFormAsset(getActivity(), "loopview_date.json");
        LoopData loopData = JsonTool.toBean(json, LoopData.class);
        if (null != loopData) {
            mLoopView.refreshData(loopData);
        }
        // 设置页面切换过度事件
        mLoopView.setScrollDuration(2000);
        // 设置页面切换时间间隔
        mLoopView.setInterval(3000);
    }

    /**
     * 初始化事件
     *
     * @return void
     * @date 2015-10-20 14:05:47
     */

    private boolean isStart = false;

    private void initEvents() {

        mLoopView.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private String url = null;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!isStart) {
                    isStart = true;
                    mediaInfoCall = serverInterface.postMediainfo_ver2(getMap1(lists.get(i).getId()), geHeader());
                    Log.d(TAG, "onItemClick: --------------------------------" + getMap1(lists.get(i).getId()));
                    mediaInfoCall.enqueue(new Callback<MediaInfo>() {
                        @Override
                        public void onResponse(Call<MediaInfo> call, Response<MediaInfo> response) {
                            url = response.body().getData().getMedia().getMedia_url();
                            Intent intent = new Intent(getContext(), VitamioVideoPlayerActivity1.class);
                            intent.putExtra("cid", (long) response.body().getData().getCourse().getId());
                            Log.d(TAG, "onResponse: --------------------------" + response.body().getData().getCourse().getId());
                            intent.setData(Uri.parse(url));
                            if (url == null) {
                                Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onItemClick: --------------------------------" + url);

                                startActivity(intent);
//                                getActivity().overridePendingTransition(0,0);

                            }
                        }

                        @Override
                        public void onFailure(Call<MediaInfo> call, Throwable t) {
                            Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();

                        }
                    });
                    ScreenShot.shoot(getActivity());
                }

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        this.isStart = false;

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        this.isStart = false;
    }

    @Override
    public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
        LoopData loopData = mLoopView.getLoopData();
        String url = loopData.items.get(position).link;

        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }


    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Log.d(TAG, "onHeaderRefresh: " + df.format(new Date()));
        view.onHeaderRefreshComplete("" + df.format(new Date()));

    }

    @Override
    public void onFooterRefresh(final PullToRefreshView view) {


        call_qiushi = serverInterface.postCourselist_ver2(getMap(), geHeader());
        call_qiushi.enqueue(new Callback<CourseListModel>() {
            @Override
            public void onResponse(Call<CourseListModel> call, Response<CourseListModel> response) {
                List<CourseListModel.ItemsEntity> list = response.body().getItems();
                lists.addAll(response.body().getItems());

                // adapter执行刷新ListView
                adapter.reloadData(list, false);
                view.onFooterRefreshEnd();
                view.onFooterRefreshComplete();
                tv.invalidate();
//                if (page * 20 - 21 > 0) {
//                    listView.setSelection(1);
//                }
//                if (lists.size()!=0) {
//                    viewempty.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<CourseListModel> call, Throwable t) {
                view.onFooterRefreshComplete();
            }
        });


    }


    @Override
    public void onLoad(final LoadListView view) {
        call_qiushi = serverInterface.postCourselist_ver2(getMap(), geHeader());
        call_qiushi.enqueue(new Callback<CourseListModel>() {
            @Override
            public void onResponse(Call<CourseListModel> call, Response<CourseListModel> response) {
                List<CourseListModel.ItemsEntity> list = response.body().getItems();
                lists.addAll(response.body().getItems());

                // adapter执行刷新ListView
                adapter.reloadData(list, false);
                view.loadComplete();
//                if (page * 20 - 21 > 0) {
//                    listView.setSelection(page * 20 - 21);
//                }
//                if (lists.size()!=0) {
//                    viewempty.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<CourseListModel> call, Throwable t) {
//                view.loadComplete();
            }
        });

    }

    Handler handle = new Handler();

    @Override
    public void onLoad(final MyScrollView myScrollView) {
        if (isfist) {
            footer = LayoutInflater.from(context).inflate(R.layout.footer_layout, null);
//        footer.setVisibility(View.GONE);
            listView.addFooterView(footer);
            isfist = false;
        } else {
            footer.setVisibility(View.VISIBLE);
        }
        call_qiushi = serverInterface.postCourselist_ver2(getMap(), geHeader());
        call_qiushi.enqueue(new Callback<CourseListModel>() {
            @Override
            public void onResponse(Call<CourseListModel> call, Response<CourseListModel> response) {
                List<CourseListModel.ItemsEntity> list = response.body().getItems();
                lists.addAll(response.body().getItems());

                // adapter执行刷新ListView
                adapter.reloadData(list, false);
//                tv.invalidate();
                myScrollView.isRunIng = false;
                footer.setVisibility(View.GONE);
//                listView.removeFooterView(footer);

//                handle.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        myScrollView.isRunIng = false;
////                        footer.setVisibility(View.GONE);
////                        myScrollView.isRunIng = false;
//                        listView.removeFooterView(footer);
//                    }
//                }, 100);
//
            }

            @Override
            public void onFailure(Call<CourseListModel> call, Throwable t) {
//                footer.setVisibility(View.GONE);
                listView.removeFooterView(footer);
            }
        });

    }
}
