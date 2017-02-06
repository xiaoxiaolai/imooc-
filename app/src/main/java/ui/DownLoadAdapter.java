package ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiao.cui.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;
import java.util.Map;

import entity.DownLoadChapterInfo;
import service.DownloadService2;
import utils.Utils;

/**
 * Created by xiao on 2016/8/9.
 */

public class DownLoadAdapter extends BaseAdapter {
    //    Map<Long,DownLoadChapterInfo> list
    public DownLoadAdapter(Map<Long, DownLoadChapterInfo> map, List<DownLoadChapterInfo> list, Context context) {
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.map = map;

    }

    private Map<Long, DownLoadChapterInfo> map;
    private List<DownLoadChapterInfo> list;
    private Context mContext;
    private LayoutInflater inflater;
    public static final String TAG = "DownLoadAdapter";


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final DownLoadChapterInfo info = list.get(i);

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.download_list_item_main_child, viewGroup, false);
            holder.textView = (TextView) view.findViewById(R.id.group_child);
            holder.progress = (DonutProgress) view.findViewById(R.id.donut_progress);
            holder.size= (TextView) view.findViewById(R.id.size);
//            holder.progress = (ProgressBar) view.findViewById(R.id.donut_progress);
            holder.imageView = (ImageView) view.findViewById(R.id.checkBox);
//            holder.imageView= (ImageView) view.findViewById(R.id.ImageItem);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (list.get(i).isDownLoad()) {
            holder.progress.setVisibility(View.GONE);


            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.progress.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }
        holder.textView.setText(list.get(i).getChapterName());
        holder.progress.setProgress((int) (list.get(i).getFinish()* 100 / list.get(i).getSize() ));
        if(info.getIsPause()==0){
            holder.progress.getBackground().setLevel(0);
            holder.progress.setTextSize(0);
        }else if (info.getIsPause()==1){
            holder.progress.getBackground().setLevel(1);
            holder.progress.setTextSize(0);
        }else {
            holder.progress.getBackground().setLevel(2);
            holder.progress.setTextSize(Utils.sp2px(mContext,10));
        }
//        holder.progress.setBackground(R.mipmap.download_waiting_icon);
        holder.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "o-----------------------nClick: "+info.getIsPause());
                if(info.getIsPause()==0){
//                    info.setIsPause(2);
                Intent intent = new Intent(mContext, DownloadService2.class);
                intent.setAction(DownloadService2.ACTION_START);
                intent.putExtra("fileinfo", info);
                mContext.startService(intent);



                }else if (info.getIsPause()==1){
//                    info.setIsPause(2);
                    Intent intent = new Intent(mContext, DownloadService2.class);
                    intent.setAction(DownloadService2.ACTION_START);
                    intent.putExtra("fileinfo", info);
                    mContext.startService(intent);

                }else {
//                    info.setIsPause(1);
                    Intent intent = new Intent(mContext, DownloadService2.class);
                    intent.setAction(DownloadService2.ACTION_PAUSE);

                    intent.putExtra("fileinfo", info);
                    mContext.startService(intent);
                }
            }
        });

        holder.size.setText(String.valueOf(list.get(i).getFinish()));


        return view;
    }

    public void update(Map list) {
        this.map = list;
        this.notifyDataSetChanged();
    }

    public void reloadData(Map data, boolean isClear) {
        if (list != null) {
            if (isClear) {
                list.clear();
            }
            map.putAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int i) {
        return list.size() == 0 ? 0 : i;
    }

    @Override
    public Object getItem(int i) {
        return list.size() == 0 ? 0 : list.get(i);
    }

    @Override
    public int getCount() {

        return list.size() == 0 ? 0 : list.size();
    }



    public void updateProgress(DownLoadChapterInfo fileinfo, long progress) {


        for (int i = 0; i < list.size(); i++) {
//            Log.d(TAG, "updateProgress: -------------------"+fileinfo.getUrl());
//            Log.d(TAG, "updateProgress: -------------------"+list.get(i).getUrl());
            if (fileinfo.getId_().equals(list.get(i).getId_())) {
//                list.set(i,fileInfo);
                list.get(i).setFinish(progress);
                list.get(i).setIsPause(fileinfo.getIsPause());
                list.get(i).setDownLoad(fileinfo.isDownLoad());
                notifyDataSetChanged();
//        Log.d(TAG, "updateProgress: -------------------"+progress);
                return;
            }
        }



        Log.d(TAG, "updateProgress: ----------xxxx---------"+progress);
    }


    class ViewHolder {
        private TextView textView;
        private TextView size;
        private ImageView imageView;
        private DonutProgress progress;
//        private ProgressBar progress;

    }
}
