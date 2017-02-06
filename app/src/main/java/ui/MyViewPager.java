package ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import second.SecondFragment;

/**
 * Created by xiao on 2016/8/11.
 */

public class MyViewPager extends ViewPager implements SecondFragment.intercept{
    private boolean intercept;
    private Boolean is ;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public Boolean isLast(Boolean boolen) {
        is = !boolen;
        return !boolen;
    }
}
