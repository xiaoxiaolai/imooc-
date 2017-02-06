package com.example.xiao.cui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiao.cui.R;
import com.example.xiao.cui.interface_.ToolBar;

import java.util.ArrayList;
import java.util.List;

import BaseFragment.BaseFragment;
import activity.DownLoadChapter;
import entity.Course;
import modle.PinnedHeaderExpandableAdapter;
import ui.PinnedHeaderExpandableListView;

import static com.example.xiao.cui.R.id.toolbar;
import static ui.MyAdapter.TAG;


/**
 * Created by xiao on 2016/8/9.
 */

public class ThridFragment extends BaseFragment implements PinnedHeaderExpandableListView.OnHeaderUpdateListener, ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener {

    private ui.PinnedHeaderExpandableListView listView;
    private ExpandableListAdapter adapter;
    private List<Course> lists = new ArrayList<Course>();
    private View tv;
    public Context context;
    private LayoutInflater inflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        context = getActivity();
        tv = inflater.inflate(R.layout.activity_main_thrid, container, false);
        toolBar = (ToolBar) getActivity();
        // App Logo
        ((Toolbar) tv.findViewById(toolbar)).setLogo(R.mipmap.head_image_0);
// Title
        ((Toolbar) tv.findViewById(toolbar)).setTitle("Title");
// Sub Title
        toolBar.initToolbar((Toolbar) tv.findViewById(toolbar));


        initViews();
        return tv;

    }


    private void initViews() {
        listView = (ui.PinnedHeaderExpandableListView) tv.findViewById(R.id.expandablelist);
        adapter = new PinnedHeaderExpandableAdapter(getContext());
        listView.setAdapter(adapter);
        for (int i = 0, count = listView.getCount(); i < count; i++) {
            listView.expandGroup(i);
        }
        listView.setOnHeaderUpdateListener(this);
        tv.findViewById(R.id.download_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DownLoadChapter.class);
                int id = 630;
                intent.putExtra("id",id);
                if (id == 0) {
                    Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public View getPinnedHeader() {
       View headerView = (ViewGroup) inflater.inflate(R.layout.group, null);
//        headerView.setPadding(10,10,10,10);
        headerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        String firstVisibleGroup = (String) adapter.getGroup(firstVisibleGroupPos);
//        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
//        textView.setPadding(20,20,20,20);
        Log.e(TAG, "updatePinnedHeader: " + firstVisibleGroup);
        textView.setText(firstVisibleGroup);
//        textView.setTextColor(Color.GREEN);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return false;
    }
}
