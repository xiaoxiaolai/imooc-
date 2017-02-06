package activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiao.cui.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.CoursesPosition;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import modle.RaiseRecyclerAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.LoadMoreRecyclerView;
import ui.LoadingDialog;
import ui.PullToRefreshView;

public class DummyFragment extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{
    private Context mContext = null;
    private LoadMoreRecyclerView recyclerView_fragment;
    private int tabindex = 0;
    //每个tab页的数据集合
    private List<Map<String, Object>> totalList = new ArrayList<>();
    private MyServerInterface serverInterface;
    private LoadingDialog loaddingDialog;
    private ArrayList<CoursesPosition.DataBean> list = new ArrayList<>();
    private ui.PullToRefreshView pullToRefreshView;


    public static DummyFragment getInstance(int tabindex) {
        DummyFragment fragment = new DummyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabindex", tabindex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mContext = getActivity();
        Bundle bundle = getArguments();
        tabindex = bundle.getInt("tabindex");
        switch (tabindex) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                LoadData();
                break;
        }
    }
private SwipeRefreshLayout pullToRefreshScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.raisechild, container, false);
        recyclerView_fragment = (LoadMoreRecyclerView) rootView.findViewById(R.id.recyclerView_fragment);
        pullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh_view);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        recyclerView_fragment.setAutoLoadMoreEnable(true);

        recyclerView_fragment.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView_fragment.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                        myItemRecyclerViewAdapter.addDatas(DummyContent.generyData(++page));
                        recyclerView_fragment.notifyMoreFinish(true);
                    }
                }, 1000);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView_fragment.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

    /**
     * 获取豆瓣Api数据
     */
    private void LoadData() {
        loaddingDialog.show();
        serverInterface.postProgram(getMap(), getHeader()).enqueue(new Callback<CoursesPosition>() {
            @Override
            public void onResponse(Call<CoursesPosition> call, Response<CoursesPosition> response) {
                list.addAll(response.body().getData());
                recyclerView_fragment.setAdapter(new RaiseRecyclerAdapter(mContext, list));
                loaddingDialog.cancel();
            }

            @Override
            public void onFailure(Call<CoursesPosition> call, Throwable t) {
                loaddingDialog.cancel();
            }
        });
    }

    private void init() {
        //获取一个Api实例
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_imooc)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverInterface = retrofit.create(MyServerInterface.class);
        loaddingDialog = new LoadingDialog(getContext());
    }

    private Map<String, String> map;
    private Map<String, String> header;
    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();
        map.clear();

        map.put("typeid", "2");
        map.put("page", "1");
        map.put("token", "922fbe1b4313f6329be6d106493b4406");
        map.put("uid", String.valueOf(2932390));
        return map;
    }

    private Map<String, String> getHeader() {
        if (header == null)
            header = new HashMap<>();
        header.put("User-Agent", "mukewang/");
        return header;
    }
    private static final String TAG = DummyFragment.class.getSimpleName();
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Log.d(TAG, "onHeaderRefresh: " + df.format(new Date()));
        view.onHeaderRefreshComplete("" + df.format(new Date()));
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }
}
