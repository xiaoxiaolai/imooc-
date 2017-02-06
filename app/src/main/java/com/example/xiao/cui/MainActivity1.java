//package com.example.xiao.cui;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.example.xiao.cui.bananaView.AdLoopView;
//import com.example.xiao.cui.bananaView.internal.BaseLoopAdapter;
//import com.example.xiao.cui.bananaView.internal.LoopData;
//import com.example.xiao.cui.bananaView.utils.JsonTool;
//import com.example.xiao.cui.utils.LocalFileUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import entity.Course;
//import ui.MyAdapter;
//import ui.MyListView;
//
//public class MainActivity1 extends AppCompatActivity implements BaseLoopAdapter.OnItemClickListener{
//
//    private AdLoopView mLoopView;
//    private MyListView listView;
//    private BaseAdapter adapter;
//    private List lists = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main1);
//        initToolbar();
//        initViews();
//        initEvents();
//    }
//
//    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//        }
//    }
//
//    private void initViews() {
//        mLoopView = (AdLoopView) this.findViewById(R.id.adloop_act_adloopview);
//        initRotateView();
//        initDates();
//        listView =(MyListView)findViewById(R.id.ListViewss);
//        adapter=new MyAdapter(lists,getApplicationContext(),listView);
//
//        listView.setAdapter(adapter);
////        fixListViewHeight(listView);
//    }
//
//    public void fixListViewHeight(ListView listView) {
//        // 如果没有设置数据适配器，则ListView没有子项，返回。
//        ListAdapter listAdapter = listView.getAdapter();
//        int totalHeight = 0;
//        if (listAdapter == null) {
//            return;
//        }
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            View listViewItem = listAdapter.getView(i , null, listView);
//            // 计算子项View 的宽高
//            listViewItem.measure(0, 0);
//            // 计算所有子项的高度和
//            totalHeight += listViewItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        // listView.getDividerHeight()获取子项间分隔符的高度
//        // params.height设置ListView完全显示需要的高度
//        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
//
//    private void initDates() {
//        for (int i = 0; i <20 ; i++) {
//            Course course = new Course("nihaoma"+ i);
//            lists.add(course);
//        }
//    }
//
//    /**
//     * 初始化LoopView
//     *
//     * @return void
//     * @date 2015-10-9 21:32:12
//     */
//    private void initRotateView() {
//        // 设置自定义布局
////        mLoopView.setLoopLayout(R.layout.ad_loopview_layout);
//        // 设置数据
//        String json = LocalFileUtils.getStringFormAsset(this, "loopview_date.json");
//        LoopData loopData = JsonTool.toBean(json, LoopData.class);
//        if(null != loopData) {
//            mLoopView.refreshData(loopData);
//        }
//        // 设置页面切换过度事件
//        mLoopView.setScrollDuration(2000);
//        // 设置页面切换时间间隔
//        mLoopView.setInterval(3000);
//    }
//
//    /**
//     * 初始化事件
//     *
//     * @return void
//     * @date 2015-10-20 14:05:47
//     */
//    private void initEvents() {
//        mLoopView.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
//        LoopData loopData = mLoopView.getLoopData();
//        String url = loopData.items.get(position).link;
//
//        Intent intent = new Intent();
//        intent.setData(Uri.parse(url));
//        intent.setAction(Intent.ACTION_VIEW);
//        startActivity(intent);
//    }
//}
