package modle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiao.cui.R;

import java.util.ArrayList;

import activity.CoursesDetails;
import entity.CoursesPosition;

/**
 * Created by leo on 16/5/7.
 */
public class PageAdapter extends PagerAdapter {
    private ArrayList<CoursesPosition.DataBean> subjectsInfos;
    private Context context;
    private View imageView;
   public static boolean isStart=false;

    public PageAdapter(Context context, ArrayList<CoursesPosition.DataBean> subjectsInfos, View view) {
        this.context = context;
        this.subjectsInfos = subjectsInfos;
        this.imageView = view;
    }
    @Override
    public int getCount() {
        return subjectsInfos.size();
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_view_position, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_movie);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        CoursesPosition.DataBean subjectsInfo = subjectsInfos.get(position);
        title.setText(subjectsInfo.getName());
        Glide.with(context).load(subjectsInfos.get(position).getPath_pic_fmt())
                .dontAnimate()
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStart) {
                    isStart = true;
                    Intent intent = new Intent(context, CoursesDetails.class);
                    context.startActivity(intent);
                }
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
