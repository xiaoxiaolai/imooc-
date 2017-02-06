package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiao.cui.R;

import java.sql.SQLException;
import java.util.List;

import dao.DownLoadLessonDao;
import entity.DownLoadLesson;

/**
 * Created by xiao on 2016/8/9.
 */

public class DownLoadLessonAdapter extends BaseAdapter {

    public DownLoadLessonAdapter(List<DownLoadLesson> list, Context context) {
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);

    }


    private List<DownLoadLesson> list;
    private Context mContext;
    private LayoutInflater inflater;
    public static final String TAG = "DownLoadLessonAdapter";


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.download_list_item, viewGroup, false);
            holder.textView = (TextView) view.findViewById(R.id.textItem);
            holder.imageView = (ImageView) view.findViewById(R.id.ImageItem);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            holder.number = (TextView) view.findViewById(R.id.number);
            holder.downloadnumber = (TextView) view.findViewById(R.id.downloadnumber);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(i).getLessonName());

        Glide.with(mContext).load(list.get(i).getPic())
                .dontAnimate()
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);
        if (list.get(i).getDownloadnum() == list.get(i).getNum()) {
            holder.number.setText("" + list.get(i).getNum());
            holder.downloadnumber.setVisibility(View.GONE);

            holder.progressBar.setVisibility(View.GONE);
        }else {
            holder.downloadnumber.setVisibility(View.VISIBLE);

            holder.progressBar.setVisibility(View.VISIBLE);
            holder.number.setText("/" + list.get(i).getNum());
            holder.downloadnumber.setText(list.get(i).getDownloadnum() + "");
        }

        return view;
    }

    public void update(List<DownLoadLesson> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void reloadData(List data, boolean isClear) {
        if (list != null) {
            if (isClear) {
                list.clear();
            }
            list.addAll(data);
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

    public void updateList() {
        DownLoadLessonDao downLoadLessonDao = new DownLoadLessonDao(mContext);
        try {
            List<DownLoadLesson> lists = downLoadLessonDao.getDao().queryForAll();
            if (lists.size() != 0) {
                list.clear();
                list.addAll(lists);
                notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class ViewHolder {
        private TextView textView;
        private TextView downloadnumber;
        private TextView number;
        private ImageView imageView;
        private ProgressBar progressBar;


    }
}
