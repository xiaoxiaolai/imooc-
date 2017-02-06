package activity;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiao.cui.Fragment.VitamioVideoFirstFrament;
import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BaseFragment.BaseFragment;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import scroll.SwipeBackControllerLayout;
import ui.ViewPagerIndicator;
import utils.Utils;

import static com.example.xiao.cui.R.id.control_player_vertical;

/**
 * 视频播放器
 *
 * @author 阿福
 */
public class VitamioVideoPlayerActivity1 extends AppCompatActivity {
    /**
     * 更新进度
     */
    protected static final int PROGRESS = 0;
    private VideoView videoview;
    /**
     * 视频播放地址
     */
    private Uri uri;
    private TextView tv_current_time;
    private SeekBar seekbar;
    private TextView tv_duration;
    private Button btn_play_pause;
    private View control_player;
    private Utils utils;
    private View control_playertop;
    private boolean isDestroy = false;
    private long sec = 0;
    private boolean isfirst = true;
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("短信1", "收藏2", "推荐3");
    private View top;
    private boolean islandscape = false;
    private VitamioVideoFirstFrament firstFrament;


    private List<BaseFragment> baseFragments;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PROGRESS:
                    int currentPosition = (int) videoview.getCurrentPosition();
                    tv_current_time.setText(utils.stringForTime(currentPosition));
                    int duration = (int) videoview.getDuration();
                    tv_duration.setText(utils.stringForTime(duration));
                    seekbar.setProgress(currentPosition);
                    if (!isDestroy) {
                        handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private View bottom;
    private String TAG = "ssss";
    private long cid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//       getWindow().setFormat(PixelFormat.RGBA_8888);//实现渐变效果需要
        initGestures();
        if (!Vitamio.isInitialized(getApplicationContext()))
            return;
        setContentView(R.layout.activity_vitamiovideopalyer);

        uri = getIntent().getData();
        cid=getIntent().getLongExtra("cid",0);
        Log.d(TAG, "onResponse: --------------------------"+cid);
        initView();
        utils = new Utils();
        videoview.setVideoURI(uri);
        videoview.seekTo(sec);
        setListener();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //去掉Activity上面的状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            videoview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            islandscape = true;
            top.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
        }
        initDatas();
//        layout = (SwipeBackControllerLayout) LayoutInflater.from(this).inflate(
//                R.layout.activity_vitamiovideopalyer, null);
        layout = (SwipeBackControllerLayout) findViewById(R.id.xxx);
        layout.init(this);
        layout.setBackGround("sss");
    }



    protected SwipeBackControllerLayout layout;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isDestroy = true;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //切换为竖屏
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {

            ViewGroup.LayoutParams params = mViewPagerIndicator.getLayoutParams();

            params.width = getScreenWidth();
//            Log.d("TAG", "onConfigurationChanged: "+getScreenWidth());
            mViewPagerIndicator.setLayoutParams(params);
            mViewPagerIndicator.updata();
            //恢复Activity上面的状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            islandscape = false;
            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            control_playertop.setVisibility(View.GONE);
//            initDatas();
        }
        //切换为横屏
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            //去掉Activity上面的状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            videoview.setLayoutParams(
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            islandscape = true;
            top.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            layout = (SwipeBackControllerLayout) LayoutInflater.from(this).inflate(
                    R.layout.activity_vitamiovideopalyer, null);

        }

    }


    private void initDatas() {
        if (baseFragments == null) {
            baseFragments = new ArrayList<>();
        } else {
            baseFragments.clear();
            baseFragments = new ArrayList<>();
        }
        for (int i = 0; i < 3; i++) {
            Log.d(TAG, "onResponse:-----------------: "+cid);
            VitamioVideoFirstFrament baseFragment = new VitamioVideoFirstFrament(cid,this);
            if (i == 0) {
                firstFrament = (VitamioVideoFirstFrament) baseFragment;
//                firstFrament.setCid(cid);
            }

            baseFragments.add(baseFragment);
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return baseFragments.get(position);
            }

            @Override
            public int getCount() {
                return baseFragments.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        mViewPagerIndicator.setVisibleTabCount(3);
        mViewPagerIndicator.setTabItemTitles(mTitles);
        mViewPagerIndicator.setViewPager(viewPager, 0);
    }


    /**
     * 初始化View
     */
    private void initView() {
        control_playertop = findViewById(R.id.control_playertop);
        top = findViewById(R.id.top);
        bottom = findViewById(R.id.bottom);
        videoview = (VideoView) findViewById(R.id.video_view);
        tv_duration = (TextView) findViewById(R.id.total_time);
        seekbar = (SeekBar) findViewById(R.id.PlaybackProgressBar);
        tv_current_time = (TextView) findViewById(R.id.current_time);
        btn_play_pause = (Button) findViewById(R.id.btn_play_pause);
        control_player = findViewById(control_player_vertical);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.videoviewpager);

    }

    private void setListener() {
        //设置点击事件
        btn_play_pause.setOnClickListener(mClickListener);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            //手指离开的时候回调
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //手指触屏的时候回调
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //状态发生变化的时候回调
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    sec = progress;

                    if (isPlaying) {
                        videoview.seekTo(progress);
                        Log.d("TAG", "onProgressChanged: " + !isPlaying + "--------------------------");
                    }
                }
            }
        });


        //设置一下监听：播放完成的监听，播放准备好的监听，播放出错的监听

        videoview.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                //开始播放
                Toast.makeText(getApplicationContext(), "维他命开始播放", Toast.LENGTH_SHORT).show();
                videoview.start();
                isPlaying = videoview.isPlaying();
                if (videoview.isPlaying()) {
                    btn_play_pause.setBackgroundResource(R.drawable.pause_bg);
                } else {
                    btn_play_pause.setBackgroundResource(R.drawable.play_bg);
                }

                //关联起来
                int duration = (int) videoview.getDuration();
                seekbar.setMax(duration);

                //发消息更新
                handler.sendEmptyMessage(PROGRESS);

            }
        });

        videoview.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                if (!firstFrament.updatavideo()){
                    Toast.makeText(getApplicationContext(), "视频播放完成了", Toast.LENGTH_SHORT).show();
                    finish();//退出播放器//

                }
            }
        });

        videoview.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "视频播放出错了", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        });

        //设置缓冲监听
        videoview.setOnBufferingUpdateListener(mOnBufferingUpdateListener);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return  mGestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void showController(boolean isShow) {
        if (isShow) {
            control_player.setVisibility(View.VISIBLE);
            if (islandscape) {
                control_playertop.setVisibility(View.VISIBLE);
            }
        } else {
            control_player.setVisibility(View.GONE);
            control_playertop.setVisibility(View.GONE);

        }
    }

    private boolean isShow = true;
    /**
     * true:播放状态
     * false:暂停状态
     */
    private boolean isPlaying = false;

    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_play_pause:
                    if (isPlaying) {
                        sec = videoview.getCurrentPosition();
                        videoview.pause();
                        btn_play_pause.setBackgroundResource(R.drawable.play_bg);
                    } else {
                        videoview.seekTo(sec);
                        btn_play_pause.setBackgroundResource(R.drawable.pause_bg);
                    }
                    isPlaying = !isPlaying;
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 视频缓冲监听器
     */
    MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            updateSecondaryProgress(percent);
        }
    };

    /**
     * 更新视频的二级缓冲
     *
     * @param percent
     */
    private void updateSecondaryProgress(int percent) {
        int secondaryProgress = (int) (videoview.getDuration() * percent / 100f);
        seekbar.setSecondaryProgress(secondaryProgress);
    }

    private GestureDetector mGestureDetector;

    /**
     * 初始化手势监听器
     */
    private void initGestures() {
        //手势监听器
        mGestureDetector = new GestureDetector(this, mOnGestureListener);


    }

    /**
     * 手势监听器
     */
    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        //长按
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("", "onSingleTapConfirmed: ----------------------");
//            play();
        }

        //滚动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("", "onSingleTapConfirmed: ----------------------");
            //获取是指滑动的距离,deltaY>0表示向上滑动,否则表示向下滑动
//            float deltaY = e1.getY() - e2.getY();
//            if (mIsLeftDown) {
//                //左侧按下,改变亮度,即mBrightnessView的透明度
//                changeGestureBrightness(deltaY);
//            } else {
//                //右侧按下,改变音量
//                changeGestureVolume(deltaY);
//            }
            return true;
        }

        //按下
        @Override
        public boolean onDown(MotionEvent e) {
//            mCurrVolume = getStreamVolume();//获取当前音量
//            mCurrBrightness = mBrightnessView.getAlpha();//获取当前的亮度view的透明度
//            mIsLeftDown = e.getX() < UIUtils.getScreenWidth() / 2; //是否在屏幕左侧按下
            return super.onDown(e);
        }

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //3种方式执行全屏
            //toggleFullscreen();
            //onClick(mFullScreenIb);
//            mFullScreenIb.performClick();    // 当过代码的方式去单击这个按钮
            return true;
        }

        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("xxxxxxxxx", "onSingleTapConfirmed: ----------------------");
            //5秒后自动隐藏
//            toggleHideControlLayout();
            isShow = !isShow;
            showController(isShow);
            return true;
        }
    };


}
