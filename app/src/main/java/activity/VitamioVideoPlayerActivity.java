package activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
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
import ui.ViewPagerIndicator;
import utils.Utils;

import static com.example.xiao.cui.R.id.control_player_vertical;

/**
 * 视频播放器
 *
 * @author 阿福
 */
public class VitamioVideoPlayerActivity extends AppCompatActivity {
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
    private Button btn_switch_player;
    private View control_player;

    private Utils utils;

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
    private long sec = 0;
    private boolean isfirst=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initGestures();
        if (!Vitamio.isInitialized(getApplicationContext()))
            return;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //去掉Activity上面的状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.radia_player);
            initviewLandScape();
            initView();

            uri = getIntent().getData();
            utils = new Utils();
            videoview.setVideoURI(uri);
            videoview.seekTo(sec);
            setListener();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //去掉Activity上面的状态栏
            setContentView(R.layout.activity_vitamiovideopalyer);
            initView();
            initViews(0,null);
            uri = getIntent().getData();
            utils = new Utils();
            videoview.setVideoURI(uri);
            videoview.seekTo(sec);
            setListener();
        }


    }

    private View control_playerLandScape = null;

    private void initviewLandScape() {
        control_playerLandScape = findViewById(R.id.control_playe);
    }

    private boolean isDestroy = false;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isDestroy = true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        sec = videoview.getCurrentPosition();
//        int pos = viewPager.getCurrentItem();
//        FragmentPagerAdapter mAdapter= (FragmentPagerAdapter) viewPager.getAdapter();
//        //切换为竖屏
//        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
//            //去掉Activity上面的状态栏
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setContentView(R.layout.activity_vitamiovideopalyer);
//            initView();
//            initViews(pos,mAdapter);
//            Log.d("TAG", "onConfigurationChanged: 1111111111111111111111111111---------" + "竖屏" + baseFragments.size());
//            uri = getIntent().getData();
//            utils = new Utils();
//            videoview.setVideoURI(uri);
//            setListener();
//            videoview.seekTo(sec);
//        }
//        //切换为横屏
//        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//            //去掉Activity上面的状态栏
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setContentView(R.layout.radia_player);
//            initView();
//            Log.d("TAG", "onConfigurationChanged: 1111111111111111111111111111---------" + "heng屏" + baseFragments.size());
//            initviewLandScape();
//            uri = getIntent().getData();
//            utils = new Utils();
//            videoview.setVideoURI(uri);
//            setListener();
//            videoview.seekTo(sec);
//        }
//
//    }

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("短信1", "收藏2", "推荐3");

    private void initViews(int pos,FragmentPagerAdapter mAdapter) {
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.videoviewpager);

        if (isfirst) {
            initDatas();
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
            isfirst = false;
//            viewPager.setOffscreenPageLimit(4);
            viewPager.setOffscreenPageLimit(0);
            mViewPagerIndicator.setViewPager(viewPager, 0);
//            mViewPagerIndicator.setViewPager(viewPager, pos);
            mViewPagerIndicator.setVisibleTabCount(3);
            mViewPagerIndicator.setTabItemTitles(mTitles);
        }else {
            initDatas();
            viewPager.setAdapter(mAdapter);
            viewPager.setOffscreenPageLimit(0);
            mViewPagerIndicator.setViewPager(viewPager, pos);
            mViewPagerIndicator.setVisibleTabCount(3);
            mViewPagerIndicator.setTabItemTitles(mTitles);
        }






    }

    private List<BaseFragment> baseFragments;

    private void initDatas() {
        if (baseFragments == null) {
            baseFragments = new ArrayList<>();
        } else {
            baseFragments.clear();
            baseFragments = new ArrayList<>();
        }
        for (int i = 0; i < 3; i++) {
            BaseFragment baseFragment = new VitamioVideoFirstFrament();
            baseFragments.add(baseFragment);
        }
    }


    /**
     * 初始化View
     */
    private void initView() {
        videoview = (VideoView) findViewById(R.id.video_view);
        tv_duration = (TextView) findViewById(R.id.total_time);
        seekbar = (SeekBar) findViewById(R.id.PlaybackProgressBar);
        tv_current_time = (TextView) findViewById(R.id.current_time);
        btn_play_pause = (Button) findViewById(R.id.btn_play_pause);
        control_player = findViewById(control_player_vertical);
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
                Toast.makeText(getApplicationContext(), "视频播放完成了", Toast.LENGTH_SHORT).show();
                finish();//退出播放器
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

        //设置控制视频的控制面板
//		videoview.setMediaController(new MediaController(this));

        //设置缓冲监听
        videoview.setOnBufferingUpdateListener(mOnBufferingUpdateListener);

//        videoview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return mGestureDetector.onTouchEvent(motionEvent);
//            }
//        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void showController(boolean isShow) {
        if (isShow) {
            control_player.setVisibility(View.VISIBLE);
            if (control_playerLandScape != null) {
                control_playerLandScape.setVisibility(View.VISIBLE);
            }
        } else {
            control_player.setVisibility(View.GONE);
            if (control_playerLandScape != null) {
                control_playerLandScape.setVisibility(View.GONE);
            }
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
