package scroll;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.xiao.cui.R;

import java.lang.reflect.Method;

public class SwipeBackControllerLayout extends FrameLayout {
    private static final String TAG = SwipeBackControllerLayout.class.getSimpleName();
    public static final int ANIMATION_DURATION = 300;//默认动画时间
    public static final int DEFAULT_TOUCH_THRESHOLD = 60;//默认开始滑动的位置距离左边缘的距离

//    private WeakReference<Activity> mActivityWR;

    private int mScreenWidth;
    private int mTouchSlop;

    private boolean isMoving = false;
    private float mInitX;
    private float mInitY;

    private ViewGroup decorView;//窗口根布局
    private ViewGroup contentView;//content布局
    private ViewGroup userView;//用户添加的布局

    private ArgbEvaluator evaluator;
    private ValueAnimator mAnimator;
    private VelocityTracker mVelTracker;
    private Context context;

    private Drawable mShadowDrawable;



    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i(TAG, "dispatchDraw:---------"+999);
        super.dispatchDraw(canvas);

//        canvas.save();
        if (mShadowDrawable != null && userView != null) {

            int left = userView.getLeft()
                    - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = userView.getTop();
            int bottom = userView.getBottom();
            Log.i(TAG, "dispatchDraw:---------"+left);
            Log.i(TAG, "dispatchDraw:---------"+top);
            Log.i(TAG, "dispatchDraw:---------"+bottom);
            Log.i(TAG, "dispatchDraw:---------"+right);
            Log.i(TAG, "dispatchDraw:---------"+userView.getTranslationX());
//            mShadowDrawable.setBounds(left,top,right,bottom);
            mShadowDrawable.setBounds
                    ( -100, top, ((int) userView.getTranslationX())-((int) userView.getTranslationX()), bottom);
            mShadowDrawable.draw(canvas);
        }

    }


    public SwipeBackControllerLayout(Context context) {
        super(context);
        this.context=context;
        mShadowDrawable = getResources().getDrawable(R.drawable.ic_launcher);
    }

    public SwipeBackControllerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mShadowDrawable = getResources().getDrawable(R.drawable.ic_launcher);
    }

    public SwipeBackControllerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        mShadowDrawable = getResources().getDrawable(R.drawable.ic_launcher);
    }


    public static void convertActivityToTranslucent(Activity activity) {
        try {
            Class[] t = Activity.class.getDeclaredClasses();
            Class translucentConversionListenerClazz = null;
            Class[] method = t;
            int len$ = t.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Class clazz = method[i$];
                if(clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                    break;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Method var8 = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class);
                var8.setAccessible(true);
                var8.invoke(activity, new Object[]{null, null});
            } else {
                Method var8 = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz);
                var8.setAccessible(true);
                var8.invoke(activity, new Object[]{null});
            }
        } catch (Throwable e) {
        }

    }

    public void attachToActivity(final Activity activity) {

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        evaluator = new ArgbEvaluator();
        this.activity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.windowBackground });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        decorView=decor;
		View decorChild =  decor.getChildAt(0);


        decor.setBackgroundResource(background);
//		decorChild.setBackgroundResource(R.color.transparent1);
//		decor.setBackgroundResource(R.color.transparent1);
		decor.removeView(decorChild);
		addView(decorChild);
//        setContentView(decorChild,activity,decor);
        userView = (ViewGroup) decorChild;
		decor.addView(this);
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(ANIMATION_DURATION);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int x = (Integer) valueAnimator.getAnimatedValue();
                if (x >= mScreenWidth) {
                    activity.finish();
                    activity.overridePendingTransition(0,0);
                }

                handleView(x);
                handleBackgroundColor(x);
            }
        });
    }
    public void init(final Activity activity) {

this.activity=activity;
//        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
//
//        activity.getWindow().setFormat(PixelFormat.TRANSPARENT);//实现渐变效果需要
//        activity.getWindow().setFormat(PixelFormat.RGBA_8888);//实现渐变效果需要
//        activity.getWindow().setBackgroundDrawable(
//                new BitmapDrawable(activity.getResources(),
//                        BitmapFactory.decodeResource(activity.getResources(), R.mipmap.blur_default_big)));//将窗口背景设置为空，这样就不会画窗口背景，能提高效率

//        activity.getWindow().setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 1);
//        activity.getWindow().setBackgroundDrawable(null);//将窗口背景设置为空，这样就不会画窗口背景，能提高效率

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        evaluator = new ArgbEvaluator();
        decorView = (ViewGroup) activity.getWindow().getDecorView();
//        decorView .setBackgroundDrawable(new BitmapDrawable(activity.getResources(),
//                BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher)));
//        decorView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff0000")));
//        decorView.setBackgroundDrawable(null);
//        decorView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//        decorView.setBackgroundResource(R.color.transparent1);
//        decorView.setBackgroundColor(Color.parseColor("#ffffff"));
//        decorView.setAlpha(0);
        contentView = (ViewGroup) activity.findViewById(android.R.id.content);

//        contentView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff000000")));
//        contentView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(),
//                        BitmapFactory.decodeResource(activity.getResources(), R.mipmap.job)));

//        File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");

//        contentView.setBackgroundDrawable(new GlideBitmapDrawable(activity.getResources(), BitmapFactory.decodeFile("sdcard/xx.png")));
//        decorView.setTranslationX(50f);
//        contentView.setTranslationX(-50f);

//        Glide.with(activity).load(file)
//                .dontAnimate()
////                .placeholder(R.mipmap.ic_launcher)
////                .skipMemoryCache(true)
////                .diskCacheStrategy(DiskCacheStrategy.NONE)
////                .transform(new BitmapCircleTransformation(mContext))
//                .error(R.mipmap.ic_launcher)
//                .into(contentView);
        userView = (ViewGroup) contentView.getChildAt(0);
//        userView.setTranslationX(50f);

        mAnimator = new ValueAnimator();
        mAnimator.setDuration(ANIMATION_DURATION);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int x = (Integer) valueAnimator.getAnimatedValue();
                if (x >= mScreenWidth) {
                    activity.finish();
                    activity.overridePendingTransition(0,0);
                }

                handleView(x);
                handleBackgroundColor(x);
            }
        });
    }

    public void handleView(int x) {
        userView.setTranslationX(x);

        Log.i(TAG, "handleView: x"+ x);
        Log.i(TAG, "handleView: userView.getX()"+ userView.getX());
        Log.i(TAG, "handleView: userView.getTranslationX()"+ userView.getTranslationX());
//        userView.scrollBy(mScreenWidth-x, 0);
        postInvalidate();
//        userView.scrollBy(-x, 0);
//        contentView.setTranslationX(-mScreenWidth/4+x/4);
//        contentView.setTranslationX(x);
//        decorView.setTranslationX(x);
    }
private Activity activity;
    public void setBackGround(String backGround) {
        if(null==backGround){
            contentView.setBackgroundDrawable(null);
            return;
        }
        contentView.setBackgroundDrawable(new GlideBitmapDrawable(activity.getResources(), BitmapFactory.decodeFile("sdcard/xx.png")));
//        userView.setTranslationX(mScreenWidth/4);
    }
    /**
     * 控制背景颜色和透明度
     * @param x
     */
    private void handleBackgroundColor(float x) {


//        activity.getWindow().getExitTransition().se
//        convertActivityToTranslucent(activity);
        int colorValue = (int) evaluator.evaluate(x / mScreenWidth,
                Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
//        float colorValue1 = ((float) evaluator.evaluate(((float) (x / mScreenWidth)),
//                ((int) 1), ((int) 0)));
//        userView.setBackgroundColor(colorValue);
//        Log.i(TAG, "handleBackgroundColor: ---"+x / mScreenWidth);
//        contentView.setAlpha(((float) 0.5));
        decorView.setBackgroundColor(colorValue);
//        decorView.setBackgroundDrawable(new ColorDrawable(colorValue));
//        contentView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//        Log.i(TAG, "x is " + x);
    }

    public boolean processEvent(MotionEvent event) {
        getVelocityTracker(event);

        if (mAnimator.isRunning()) {
            return true;
        }

        int pointId = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = event.getRawX();
                mInitY = event.getRawY();
                pointId = event.getPointerId(0);
//                return false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMoving) {
                    float dx = Math.abs(event.getRawX() - mInitX);
                    float dy = Math.abs(event.getRawY() - mInitY);
                    if (dx > mTouchSlop && dx > dy && mInitX < DEFAULT_TOUCH_THRESHOLD) {
                        isMoving = true;
                    }
                }
                if (isMoving) {
//                    contentView.setTranslationX(-50f);
                    handleView((int) event.getRawX());
                    handleBackgroundColor(event.getRawX());

                }
//                return true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int distance = (int) (event.getRawX() - mInitX);

                mVelTracker.computeCurrentVelocity(1000);
                //获取x方向上的速度
                float velocityX = mVelTracker.getXVelocity(pointId);

                Log.d(TAG, "mVelocityX is " + velocityX);
                if (isMoving && Math.abs(userView.getTranslationX()) >= 0) {
                    if (velocityX > 1000f || distance >= mScreenWidth / 4) {
//                        contentView.setTranslationX( event.getRawX());
                        mAnimator.setIntValues((int) event.getRawX(), mScreenWidth);
                    } else {
//                        contentView.setTranslationX(0f);
                        mAnimator.setIntValues((int) event.getRawX(), 0);
                    }
                    mAnimator.start();
                    isMoving = false;
                }

                mInitX = 0;
                mInitY = 0;

                recycleVelocityTracker();
//                return true;
                break;
        }
        return true;
    }

    /**
     * 获取速度追踪器
     *
     * @return
     */
    private VelocityTracker getVelocityTracker(MotionEvent event) {
        if (mVelTracker == null) {
            mVelTracker = VelocityTracker.obtain();
        }
        mVelTracker.addMovement(event);
        return mVelTracker;
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker() {
        if (mVelTracker != null) {
            mVelTracker.clear();
            mVelTracker.recycle();
            mVelTracker = null;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = event.getRawX();
                mInitY = event.getRawY();
//                pointId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
//                if (!isMoving) {
                    float dx = Math.abs(event.getRawX() - mInitX);
                    float dy = Math.abs(event.getRawY() - mInitY);
                    if (dx > mTouchSlop && dx > dy && mInitX < DEFAULT_TOUCH_THRESHOLD) {
//                        isMoving = true;
                        return true;
                    }
                }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return processEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        processEvent(event);
        return super.dispatchTouchEvent(event);
    }
}