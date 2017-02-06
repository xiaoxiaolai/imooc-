package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Project;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import modle.CoursesDetails1PinnedHeaderExpandableAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scroll.SwipeBackControllerLayout;
import ui.LoadingDialog;
import ui.PinnedHeaderExpandableListView;

public class CoursesDetails1 extends AppCompatActivity {
    private MyServerInterface serverInterface;
    private CoursesDetails1PinnedHeaderExpandableAdapter adapter;
    private LoadingDialog loaddingDialog;
    private ArrayList<Project.DataBean> list = new ArrayList<>();
    private Map<String, String> map;
    private Map<String, String> header;
    private PinnedHeaderExpandableListView listView;
    private SwipeBackControllerLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursesdetails1);
        init();
        LoadData();
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
        loaddingDialog = new LoadingDialog(this);
        //初始化listView
        listView = (PinnedHeaderExpandableListView) findViewById(R.id.list1);
        layout = (SwipeBackControllerLayout) findViewById(R.id.coursedetails1);
        layout.init(this);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });


    }

    /**
     * 获取豆瓣Api数据
     */
    private void LoadData() {
        loaddingDialog.show();
        serverInterface.postProgramtype(getMap(), getHeader()).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                list.addAll(response.body().getData());
                adapter = new CoursesDetails1PinnedHeaderExpandableAdapter(list, CoursesDetails1.this);
                listView.setAdapter(adapter);

                loaddingDialog.cancel();
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                loaddingDialog.cancel();
            }
        });
    }

    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();
        map.clear();

        map.put("pid", "32");
        map.put("token", "393f71cdc8bf31bc24aab55c19f459b8");
        map.put("uid", String.valueOf(2932390));
        return map;
    }

    private Map<String, String> getHeader() {
        if (header == null)
            header = new HashMap<>();
        header.put("User-Agent", "mukewang/");
        return header;
    }
}
