package activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import modle.CoursesDetailsPageAdapter;
import modle.CoursesDetailsPinnedHeaderExpandableAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scroll.SwipeBackControllerLayout;
import ui.LoadingDialog;
import ui.PinnedHeaderExpandableListView;

public class CoursesDetails extends AppCompatActivity {
    private MyServerInterface serverInterface;
    private CoursesDetailsPinnedHeaderExpandableAdapter adapter;
    private LoadingDialog loaddingDialog;
    private ArrayList<entity.CoursesDetails.DataBean> list = new ArrayList<>();
    private Map<String, String> map;
    private Map<String, String> header;
    private PinnedHeaderExpandableListView listView;
    private SwipeBackControllerLayout layout;
    private ViewPager viewpager;
    private CoursesDetailsPageAdapter viewpageradapter;
    private ViewGroup viewgroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursesdetails);
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
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        viewgroup = (ViewGroup) findViewById(R.id.linearLayout);
        ImageView imageView = (ImageView) viewgroup.getChildAt(1);
        imageView.setEnabled(false);
        viewpageradapter = new CoursesDetailsPageAdapter(this);
        viewpager.setAdapter(viewpageradapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageView imageView;
                for (int i = 0; i < 2; i++) {
                    if (i == position) {
                        imageView = (ImageView) viewgroup.getChildAt(i);
                        imageView.setEnabled(true);
                    } else {
                        imageView = (ImageView) viewgroup.getChildAt(i);
                        imageView.setEnabled(false);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layout = (SwipeBackControllerLayout) findViewById(R.id.coursedetails1);
        layout.init(this);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });


    }

    /**
     * 获取豆瓣Api数据
     */
    private void LoadData() {
        loaddingDialog.show();
        serverInterface.postCoursesDetails(getMap(), getHeader()).enqueue(new Callback<entity.CoursesDetails>() {
            @Override
            public void onResponse(Call<entity.CoursesDetails> call, Response<entity.CoursesDetails> response) {
                list.addAll(response.body().getData());
                adapter = new CoursesDetailsPinnedHeaderExpandableAdapter(list, CoursesDetails.this,listView);
                listView.setAdapter(adapter);
                listView.expandGroup(0);

                loaddingDialog.cancel();
            }

            @Override
            public void onFailure(Call<entity.CoursesDetails> call, Throwable t) {
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
