package ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiao.cui.R;

import java.util.List;

import entity.CourseListModel;

/**
 * Created by xiao on 2016/8/9.
 */

public class MyAdapter extends BaseAdapter implements LoadListView.OnScrollListener{
    private LoadListView loadListView;
    public MyAdapter(List<CourseListModel.ItemsEntity> list, Context context,LoadListView loadListView) {
        this.list = list;
        this.mContext =context;
        this.inflater=LayoutInflater.from(context);
        this.loadListView=loadListView;
        loadListView.setOnScrollListener(this);
    }


    private List<CourseListModel.ItemsEntity> list;
    private Context mContext;
    private LayoutInflater inflater;
    public static final String TAG = "myadapter";


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        Log.e(TAG, "getView: ----------------------------------------");
//        if(list.size()==0){
//            view= inflater.inflate(R.layout.adapter_empty_view,viewGroup,false);
//            Log.e(TAG, "getView: ----------------------------------------");
//            return view;
//        }
        ViewHolder holder =null;
        if(view==null){
            holder=new ViewHolder();
            view= inflater.inflate(R.layout.listitem,viewGroup,false);
            holder.textView=(TextView) view.findViewById(R.id.textItem);
            holder.imageView= (ImageView) view.findViewById(R.id.ImageItem);
            view.setTag(holder);
        }else {
            holder=(ViewHolder) view.getTag();
        }
//        holder.imageView.setTag(list.get(i).getPic());
        holder.textView.setText(list.get(i).getName());
//        if( holder.imageView.getTag()==list.get(i).getPic())
        Glide.with(mContext).load(list.get(i).getPic())
                .dontAnimate()
//                .placeholder(R.mipmap.ic_launcher)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .transform(new BitmapCircleTransformation(mContext))
                .error(R.mipmap.ic_launcher)
                .into( holder.imageView);

        return view;
    }

    public void update(List<CourseListModel.ItemsEntity> list){
        this.list=list;
        this.notifyDataSetChanged();
    }

    public void reloadData(List data , boolean isClear) {
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
        return list.size()==0 ? 0 : i;
//        resId != 0 ? context.getText(resId) : "";
    }

    @Override
    public Object getItem(int i) {
        return list.size()==0 ? 0 :list.get(i);
    }

    @Override
    public int getCount() {

        return list.size()==0 ? 0 : list.size();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 1) {
            //如果在暂停或者触摸的情况下完成重置
//            Glide.with(mContext).pauseRequests();
            Log.d(TAG, "onScrollStateChanged: ---------------2222--------");
        } else {
            //停止更新
//            Glide.with(mContext).pauseRequests(); Log.d(TAG, "onScrollStateChanged: ------------333-----------");
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


    }

    class ViewHolder {
        private TextView textView;
        private ImageView imageView;

    }
}
