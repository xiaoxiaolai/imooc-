package ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by xiao on 2016/8/11.
 */

public class MyViewPager1 extends ViewPager {


    private final int mTouchSlop;
    private float mInitX;
    private float mInitY;
    private boolean isMoving=false;

    public MyViewPager1(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyViewPager1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(isMoving){
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = event.getRawX();
                mInitY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMoving) {
                    float dx = Math.abs(event.getRawX() - mInitX);
                    float dy = Math.abs(event.getRawY() - mInitY);
                    if (dx > mTouchSlop && dx > dy && mInitX > DEFAULT_TOUCH_THRESHOLD) {
                        isMoving = true;
                        return true;
                    }
                }
        }
       return false;
    }
    public static final int DEFAULT_TOUCH_THRESHOLD = 600;//默认开始滑动的位置距离左边缘的距离
}
