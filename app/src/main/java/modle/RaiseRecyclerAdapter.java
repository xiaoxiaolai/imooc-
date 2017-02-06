package modle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiao.cui.R;

import java.util.ArrayList;

import entity.CoursesPosition;

public class RaiseRecyclerAdapter extends RecyclerView.Adapter<RaiseRecyclerAdapter.ViewHolder> {
    private static final String TAG = "MainRecyclerAdapter";
    private Context mContext;
    private LayoutInflater mInflater = null;
    private ArrayList<CoursesPosition.DataBean> list = null;

    public RaiseRecyclerAdapter(Context mContext, ArrayList<CoursesPosition.DataBean>  list) {
        this.list = list;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview_dummyfragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.mContext = mContext;
        viewHolder.textView_item_title.setText(list.get(position).getName());
        viewHolder.textView_item_teacher.setText("讲师：" );
        viewHolder.textView_item_desc.setText("---");
        viewHolder.textView_item_star.setText("难度系数：" );
//        viewHolder.imageView_item_icon.setImageDrawable((Drawable) list.get(position).get("image"));
        Glide.with(mContext).load(list.get(position).getPath_pic_fmt())
                .dontAnimate()
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.imageView_item_icon);
        viewHolder.ratingBar_item.setRating(2);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_item_icon;
        public RatingBar ratingBar_item;
        public TextView textView_item_title;
        public TextView textView_item_desc;
        public TextView textView_item_teacher;
        public TextView textView_item_star;
        public Button button_item_subscribe;
        public Context mContext;

        public ViewHolder(View view) {
            super(view);
            imageView_item_icon = (ImageView) view.findViewById(R.id.imageView_item_icon);
            textView_item_title = (TextView) view.findViewById(R.id.textView_item_title);
            textView_item_desc = (TextView) view.findViewById(R.id.textView_item_desc);
            textView_item_teacher = (TextView) view.findViewById(R.id.textView_item_teacher);
            textView_item_star = (TextView) view.findViewById(R.id.textView_item_star);
            button_item_subscribe = (Button) view.findViewById(R.id.button_item_subscribe);
            ratingBar_item = (RatingBar) view.findViewById(R.id.ratingBar_item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((MainActivity) mContext).startActivity(v, getPosition());
                }
            });
        }
    }
}
