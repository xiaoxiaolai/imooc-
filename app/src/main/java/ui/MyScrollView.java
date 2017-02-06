package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.xiao.cui.R;

/**
 * Created by xiao on 2016/8/10.
 * <p>
 * 未成功
 */

public class MyScrollView extends ScrollView  {


    private LayoutInflater mInflater;
    private String TAG = "MyScrollVie";
    private ILoadListener iLoadListener;
    public boolean isRunIng = false;


    public MyScrollView(Context context) {

        super(context);
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mInflater = LayoutInflater.from(getContext());

    }


    /**
     * last y
     */
    private int mLastMotionY = 0;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        return super.onInterceptTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev)||isRefreshViewScroll(-1);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
//                Glide.with(getContext()).pauseRequests();
                Log.d(TAG, "onScrollStateChanged: ------------333-----------");

                break;}
//        if (isRefreshViewScroll(-1)&&!isRunIng) {
//            isRunIng=true;
////            iLoadListener.onLoad(this);
//            Log.d(TAG, "onInterceptTouchEvent: ---------cccccc----");
////            return true;
//            return false;
//        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // footer view 在此添加保证添加到linearlayout中的最后
//        addFooterView();
//        initContentAdapterView();

    }
    /**
     * footer view
     */
    private View mFooterView;
    /**
     * footer view height
     */
    private int mFooterViewHeight;

    private void addFooterView() {
        // footer view
        mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
        mFooterView.setVisibility(INVISIBLE);
//        mFooterImageView = (ImageView) mFooterView
//                .findViewById(R.id.pull_to_load_image);
//        mFooterTextView = (TextView) mFooterView
//                .findViewById(R.id.pull_to_load_text);
//        mFooterProgressBar = (ProgressBar) mFooterView
//                .findViewById(R.id.pull_to_load_progress);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin
        // =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
        // getHeight()什么时候会赋值,稍候再研究一下
        // 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏

        addView(mFooterView, params);
    }
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        int deltaY = 0;
//        deltaY = y - mLastMotionY;




        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaY = y - mLastMotionY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        deltaY = y - mLastMotionY;
        // deltaY > 0 是向下运动,< 0是向上运动
        if (isRefreshViewScroll(deltaY )) {
            return false;
//            return true;
//            isRunIng=true;
//            iLoadListener.onLoad(this);
//            mInflater = LayoutInflater.from(getContext());
//            View footer = mInflater.inflate(R.layout.footer_layout, null);
//		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
//            Log.d(TAG, "onInterceptTouchEvent: --------yyy-----");

        }
        return super.onInterceptTouchEvent(e);
//        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: --------yyy-----");
//        if (isRefreshViewScroll(-1)&&!isRunIng) {
//            Log.d(TAG, "onInterceptTouchEvent: ---------cccccc----");
//
//            isRunIng=true;
//            iLoadListener.onLoad(this);
//
////            return true;
////            return false;
//        }
        return super.dispatchTouchEvent(ev);
    }
    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        View child = mScrollView.getChildAt(0);
//        Log.d(TAG, "child.getMeasuredHeight() = [" + child.getMeasuredHeight() + "]");
//        Log.d(TAG, "getHeight() = [" + getHeight() + "]");
//        Log.d(TAG, "mScrollView.getScrollY() = [" + mScrollView.getScrollY() + "]");
////            if (isRefreshViewScroll(-1)) {
////                iLoadListener.onLoad();
////                Log.d(TAG, "onInterceptTouchEvent: -------------");
//////                return false;
////            }
//
//        int y = (int) e.getRawY();
//        int deltaY = 0;
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // 首先拦截down事件,记录y坐标
//                mLastMotionY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                deltaY = y - mLastMotionY;
//                // deltaY > 0 是向下运动,< 0是向上运动
//
//                if (isRefreshViewScroll(deltaY)) {
//                    iLoadListener.onLoad();
//                    Log.d(TAG, "onInterceptTouchEvent: -------------");
//                    return true;
//                }
//                if (deltaY != 0) {
//                    return true;
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                deltaY = y - mLastMotionY;
//                if (deltaY != 0) {
//                    return true;
//                }
//            case MotionEvent.ACTION_CANCEL:
//                deltaY = y - mLastMotionY;
//                if (deltaY != 0) {
//                    return true;
//                }
//                break;
//        }
//
//        return false;
//    }

    MyScrollView mScrollView = this;

    private boolean isRefreshViewScroll(int deltaY) {
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
//                mPullState = PULL_DOWN_STATE;
//                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= 8+getHeight()
                    + mScrollView.getScrollY()) {
                Log.d(TAG, "child.getMeasuredHeight() = [" + child.getMeasuredHeight() + "]");
                Log.d(TAG, "getHeight() = [" + getHeight() + "]");
//                Log.d(TAG, "mScrollView.getScrollY() = [" + mScrollView.getScrollY() + "]");
//                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }


    public void setInterface(ILoadListener iLoadListener) {
        this.iLoadListener = iLoadListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.d(TAG, "onScrollChanged: ---------onScrollChanged----");
        if (isRefreshViewScroll(-1)&&!isRunIng) {
            Log.d(TAG, "onInterceptTouchEvent: ---------cccccc----");

            isRunIng=true;
            iLoadListener.onLoad(this);

//            return true;
//            return false;
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }


    public interface ILoadListener {
        public void onLoad(MyScrollView myScrollView);
    }

}
