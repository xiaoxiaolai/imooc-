package modle;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by leo on 16/5/7.
 */
public class CoursesDetailsPageAdapter extends PagerAdapter {
    private ArrayList<entity.CoursesDetails.DataBean> subjectsInfos;
    private Context context;
    private View imageView;
    private boolean isStart=false;

    public CoursesDetailsPageAdapter(Context context, ArrayList<entity.CoursesDetails.DataBean> subjectsInfos, View view) {
        this.context = context;
        this.subjectsInfos = subjectsInfos;
        this.imageView = view;
    }

    public CoursesDetailsPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        TextView view =new TextView(context);
        view.setText("ssshhafjhgabkjfgkafgjkahfkjahfjkahfk");
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
