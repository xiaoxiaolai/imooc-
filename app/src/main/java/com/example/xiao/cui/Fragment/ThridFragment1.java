package com.example.xiao.cui.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiao.cui.MainActivity;
import com.example.xiao.cui.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseFragment.BaseFragment;
import activity.DownLoad;
import dao.DownLoadLessonDao;
import entity.DownLoadLesson;
import ui.DownLoadLessonAdapter;


/**
 * Created by xiao on 2016/8/9.
 */

public class ThridFragment1 extends BaseFragment implements MainActivity.upDataList{

    private ListView listView;
    private DownLoadLessonAdapter adapter;
    private List<DownLoadLesson> lists = new ArrayList<DownLoadLesson>();
    private View tv;
    public Context context;
    private LayoutInflater inflater;
    public static String UPDATAE = "ThridFragment1UPDATAE";
    public static String UPDATAE1 = "ThridFragment1UPDATAE1";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tv = inflater.inflate(R.layout.download_list_main_lesson, container, false);
        listView = (ListView) tv.findViewById(R.id.download_list_main_lesson);
        initDatas();
//        if (lists.size() != 0) {
            adapter = new DownLoadLessonAdapter(lists, getContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!isStart) {
                        isStart=true;
                        Intent intent = new Intent();
                        List list = new ArrayList(lists.get(i).getChapterInfos());
                        Toast.makeText(getActivity(), "" + lists.get(i).getChapterInfos().size(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("listobj", lists.get(i));
                        intent.setClass(getActivity(), DownLoad.class);
                        startActivity(intent);
                    }
                }
            });
//        }
//        aBoolean = true;


        return tv;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity=   (MainActivity)getActivity();
        activity.setUpDataList(this);
    }

    private void initDatas() {

        try {
            DownLoadLessonDao downLoadLessonDao = new DownLoadLessonDao(getContext());
            lists = downLoadLessonDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
    }

    private void initRegister() {
        //注册广播接收器
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATAE1);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (UPDATAE1.equals(intent.getAction())) {
                    Log.d("TAG", "onReceive:------sss------------sss---ss- ");
                    adapter.updateList();
                    //progressBar.setProgress(finished);
                }
            }
        };
        broadcastManager.registerReceiver(mReceiver, filter);
    }

    LocalBroadcastManager broadcastManager;


    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver;




//    private Boolean aBoolean = false;
private boolean isStart=false;
    @Override
    public void onResume() {
//        initRegister();
        super.onResume();
        this.isStart=false;
//        if (aBoolean) {
//
            adapter.updateList();
//
//    }
         }

    @Override
    public void upDataListItem() {
        if(adapter!=null)
        adapter.updateList();
    }
}
