//package activity;
//
///**
// * Created by xiao on 2016/9/4.
// */
//
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.AudioManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.format.DateFormat;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewPropertyAnimator;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.xiao.cui.R;
//
//import httpClient.Constant;
//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.widget.VideoView;
//
///**
// * Vitamio 万能视频播放器
// * 直接从VideoPlayerActivity修改Copy过来,关于VideoView的api完全一样,只是导包不一样.
// * 1.多了这一句 if (!LibsChecker.checkVitamioLibs(this)) return; 以及内部的VideoView对全屏和按比例缩放的代码处理方式不一样
// * 2.布局文件换成了activity_video_player_vitamio
// * Created by mChenys on 2016/1/17.
// */
//public class myplayer extends Activity {
//
//    private VideoView mVideoView;
//    private int mPosition;
//    private List<Video> mVideoList;
//    private ImageButton mBackIb, mPreIb, mNextIb, mPlayIb, mFullScreenIb, mVolumeIb;
//    private TextView mTitleTv, mSysTimeTv, mCurrVideoPositionTv, mVideoDurationTv;
//    private ImageView mBatteryIv;
//    private View mBrightnessView;
//    private LinearLayout mTopControlLl, mBottomControlLl;
//    private Handler mHandler;
//    private AudioManager mAudioManager;
//    private int mMaxVolume, mCurrVolume;
//    private SeekBar mVolumeSb, mVideoSb;
//    private GestureDetector mGestureDetector;
//    private float mVolumeScreenHeightScale;
//    private float mCurrBrightness;
//    private boolean mIsLeftDown;
//    private float mBrightnessScreenHeightScale;
//    private Video mVideo;
//
//    @Override
//    protected void setTitleBar(TitleBar titleBar) {
//        titleBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    protected boolean isHomePage() {
//        return false;
//    }
//
//    @Override
//    public Integer getLayoutResId() {
//        return R.layout.activity_video_player_vitamio;
//    }
//
//    @Override
//    public void initView() {
//        mVideoView = findView(R.id.videoView);
//        mTitleTv = findView(R.id.tv_title);//标题
//        mSysTimeTv = findView(R.id.tv_system_time);//系统时间
//        mCurrVideoPositionTv = findView(R.id.tv_current_position);//当前播放位置
//        mVideoDurationTv = findView(R.id.tv_duration);//视频时长
//        mVolumeIb = findView(R.id.ib_voice);//静音
//        mBackIb = findView(R.id.ib_back);//返回
//        mPreIb = findView(R.id.ib_pre);//上一首
//        mPlayIb = findView(R.id.ib_play);//播放
//        mNextIb = findView(R.id.ib_next);//下一首
//        mFullScreenIb = findView(R.id.ib_fullScreen);//全屏
//        mBatteryIv = findView(R.id.iv_battery);//电量
//        mVolumeSb = findView(R.id.sb_voice);//音量进度seekBar
//        mVideoSb = findView(R.id.sb_video);//视频进度seekBar
//        mBrightnessView = findView(R.id.view_brightness);//通过透明度改变屏幕亮度的view
//        //默认隐藏控制面板
//        mTopControlLl = findView(R.id.ll_top_ctrl);//顶部控制面板
//        mBottomControlLl = findView(R.id.ll_bottom_ctrl);//底部控制面板
//    }
//
//    @Override
//    public void initListener() {
//        //设置视频准备监听器
//        mVideoView.setOnPreparedListener(mOnPreparedListener);
//        //设置视频播放完毕的监听器
//        mVideoView.setOnCompletionListener(mOnCompletionListener);
//        //设置音量改变的seekBar监听器
//        mVolumeSb.setOnSeekBarChangeListener(mVolumeSeekBarChangeListener);
//        //设置视频进度改变的seekBar监听器
//        mVideoSb.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
//        //设置缓冲监听
//        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
//        //设置视频卡顿监听器
//        mVideoView.setOnInfoListener(mOnInfoListener);
//    }
//
//    @Override
//    public void initData() {
//        //注册电量接收器
//        registerBatteryReceiver();
//        // 检查Vitamio插件是否安装
//        if (!LibsChecker.checkVitamioLibs(this)) return;
//        mHandler = new Handler();
//        Uri videoUri = getIntent().getData();
//        if (null != videoUri) {
//            // 说明是从第三方应用跳转过来的
//            mLoadView.setProgressBarVisiable(true);
//            mVideoView.setVideoURI(videoUri);
//            mTitleTv.setText(videoUri.getPath());
//            mPreIb.setEnabled(false);
//            mNextIb.setEnabled(false);
//        } else {
//            //说明是从播放列表进入的
//            Bundle bundle = getIntent().getExtras();
//            if (null == bundle || bundle.size() < 0) {
//                mLoadView.setExceptionViewVisible(true);
//                return;
//            }
//            mPosition = bundle.getInt(Constant.KEY_POSITION);
//            mVideoList = bundle.getParcelableArrayList(Constant.KEY_LIST);
//            //设置当前播放的视频
//            setCurrPlayVideo();
//        }
//        //初始化音量
//        initVolume();
//        //初始化手势监听器
//        initGestures();
//        //初始化亮度值
//        initBrightness();
//    }
//
//    @Override
//    public void reLoadData() {
//
//    }
//
//    /**
//     * 设置当前播放的视频
//     */
//    private void setCurrPlayVideo() {
//        if (mVideoList == null || mVideoList.isEmpty() || mPosition == -1) {
//            return;
//        }
//        mLoadView.setProgressBarVisiable(true);
//        LogUtils.i(this, "mPosition:" + mPosition + " mVideoList:" + mVideoList);
//        //设置上一个/下一个按钮不可用时的背景图片
//        mPreIb.setEnabled(mPosition != 0);//当前不是第一个,则可点击上一个
//        mNextIb.setEnabled(mPosition != mVideoList.size() - 1);//当前不是最后一个,则可以点击下一个
//        //从列表中取出要播放的视频
//        mVideo = mVideoList.get(mPosition);
//        //设置播放的地址
//        mVideoView.setVideoPath(mVideo.path);
//    }
//
//    /**
//     * 初始化亮度值
//     */
//    private void initBrightness() {
//        mBrightnessView.setVisibility(View.VISIBLE);
//        ViewHelper.setAlpha(mBrightnessView, 0f);//一开始完全透明
//        mBrightnessScreenHeightScale = 1.0f / UIUtils.getScreenHeight();//透明度和屏幕高度的比例
//    }
//
//    /**
//     * 初始化手势监听器
//     */
//    private void initGestures() {
//        //手势监听器
//        mGestureDetector = new GestureDetector(this, mOnGestureListener);
//        //最大音量和屏幕高度的比例
//        mVolumeScreenHeightScale = mMaxVolume * 1.0f / UIUtils.getScreenHeight();
//        LogUtils.i(VitamioVideoPlayerActivity.this, "mVolumeScreenHeightScale:" + mVolumeScreenHeightScale + " mMaxVolume:" + mMaxVolume
//                + " Env.screenHeight:" + UIUtils.getScreenHeight());
//
//    }
//
//    /**
//     * 注册电量变化的接收器
//     */
//    private void registerBatteryReceiver() {
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(mBatteryChangeReceiver, filter);
//    }
//
//    /**
//     * 初始化音量控制
//     */
//    private void initVolume() {
//        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//最大音量
//        mCurrVolume = getStreamVolume();//当前音量
//        mVolumeSb.setMax(mMaxVolume);
//        mVolumeSb.setProgress(mCurrVolume);
//    }
//
//    /**
//     * 获取当前音量
//     *
//     * @return
//     */
//    private int getStreamVolume() {
//        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//    }
//
//    /**
//     * 更新系统音量进度
//     */
//    private void updateVolumePosition(int progress) {
//        int streamType = AudioManager.STREAM_MUSIC;
//        int flags = 0; //0:不显示系统音量面板,1:显示
//        mAudioManager.setStreamVolume(streamType, progress, flags);
//    }
//
//    /**
//     * 切换音量
//     */
//    private void toggleVolume() {
//        if (getStreamVolume() > 0) {
//            //设置为静音
//            mCurrVolume = getStreamVolume();
//            updateVolumePosition(0);
//            mVolumeSb.setProgress(0);
//        } else {
//            //恢复之前音量
//            updateVolumePosition(mCurrVolume);
//            mVolumeSb.setProgress(mCurrVolume);
//        }
//    }
//
//    /**
//     * 通过手势改变音量大小
//     *
//     * @param deltaY
//     */
//    private void changeGestureVolume(float deltaY) {
//        // 1、触摸（onTouchEvent方法中处理）
//        // 2、识别手势（GuestureDetactor）
//        // 3、计算音量最大值与屏幕高的比例
//        // 4、计算移动的距离等于多少对应的音量值
//
//        //最终的音量=当前音量+滑动距离*比例
//        int changeVolume = (int) (deltaY * mVolumeScreenHeightScale);
//        int finalVolume = mCurrVolume + changeVolume;
//
//        // 预防超出范围
//        if (finalVolume > mMaxVolume) {
//            finalVolume = mMaxVolume;
//        } else if (finalVolume < 0) {
//            finalVolume = 0;
//        }
//        updateVolumePosition(finalVolume);
//        mVolumeSb.setProgress(finalVolume);
//        LogUtils.i(VitamioVideoPlayerActivity.this, "当前音量:" + finalVolume);
//    }
//
//    /**
//     * 改变亮度
//     *
//     * @param deltaY
//     */
//    private void changeGestureBrightness(float deltaY) {
//        // 1、触摸（onTouchEvent方法中处理）
//        // 2、识别手势（GuestureDetactor）
//        // 3、计算亮度最大值与屏幕高的比例
//        // 4、计算移动的距离等于多少对应的亮度值
//        float changeBrightness = deltaY * mBrightnessScreenHeightScale;
//        // 5、在原来亮度的基础上加上移动对应的亮度值
//        //这里取changeBrightness的相反值,是因为往上移动需要屏幕变亮,所以透明度值要变小
//        float finalBrightness = mCurrBrightness - changeBrightness;
//        if (finalBrightness < 0f) {
//            finalBrightness = 0f;
//        } else if (finalBrightness > 0.8f) {
//            finalBrightness = 0.8f;//这里不能太接近1,1的话就什么都看不到了.
//        }
//        ViewHelper.setAlpha(mBrightnessView, finalBrightness);
//    }
//
//    /**
//     * 显示系统时间
//     */
//    private void updateSysTime() {
//        mHandler.postDelayed(mUpdateSysTimeRunnable, 1000);
//    }
//
//    /**
//     * 更新视频的播放进度
//     */
//    private void updateVideoPosition() {
//        mHandler.postDelayed(mUpdateVideoPositionRunnable, 300); //更新的频率可以调快一点
//
//    }
//
//    /**
//     * 更新视频的二级缓冲
//     *
//     * @param percent
//     */
//    private void updateSecondaryProgress(int percent) {
//        int secondaryProgress = (int) (mVideoView.getDuration() * percent / 100f);
//        mVideoSb.setSecondaryProgress(secondaryProgress);
//    }
//
//    /**
//     * 播放/暂停视频
//     */
//    private void play() {
//        if (mVideoView.isPlaying()) {
//            mVideoView.pause();
//        } else {
//            mVideoView.start();
//        }
//        setPlayBgSelector();
//    }
//
//    /**
//     * 设置播放按钮的背景选择器
//     */
//    private void setPlayBgSelector() {
//        //如果当前是播放,则显示暂停背景图片选择器
//        //如果当前是暂停,则显示播放背景图片选择器
//        mPlayIb.setImageResource(mVideoView.isPlaying() ? R.drawable.selector_ib_pause : R.drawable.selector_ib_play);
//    }
//
//    /**
//     * 播放上一个
//     */
//    private void pre() {
//        if (mPosition > 0) {
//            mPosition--;
//            setCurrPlayVideo();
//        }
//    }
//
//    /**
//     * 播放下一个
//     */
//    private void next() {
//        if (mPosition != mVideoList.size() - 1) {
//            mPosition++;
//            setCurrPlayVideo();
//        }
//    }
//
//    /**
//     * 在全屏和默认大小之间进行切换
//     */
//    private void toggleFullscreen() {
//        mVideoView.toggleFullscreen();
//        updateFullscreenBtnBg();
//    }
//
//    /**
//     * 更新全屏按钮的背景选择器
//     */
//    private void updateFullscreenBtnBg() {
//        // 如果当前是全屏的，则显示一个恢复默认大小的按钮
//        // 如果当前是默认大小的，则显示一个全屏的按钮
//        mFullScreenIb.setImageResource(mVideoView.isFullscreen() ? R.drawable.selector_ib_default_screen
//                : R.drawable.selector_ib_full_screen);
//    }
//
//    /**
//     * 隐藏控制面板
//     */
//    private void hideControlLayout() {
//        //瞬时执行隐藏动画
////        ViewHelper.setTranslationY(mTopControlLl, -mTopControlLl.getMeasuredHeight());//向上移动是负数
////        ViewHelper.setTranslationY(mBottomControlLl, mBottomControlLl.getMeasuredHeight());//向下移动是正数
//        //带动画效果的隐藏控制面板
//        ViewPropertyAnimator.animate(mTopControlLl).translationY(-mTopControlLl.getHeight());
//        ViewPropertyAnimator.animate(mBottomControlLl).translationY(mBottomControlLl.getHeight());
//    }
//
//    /**
//     * 单击切换控制面板的显示/隐藏
//     */
//    private void toggleHideControlLayout() {
//        //获取mTopControlLl当前相对控件Y坐标的位置
//        float currTranslateY = ViewHelper.getTranslationY(mTopControlLl);
//        if (currTranslateY == 0f) {
//            //当前是显示控制面板的,需要隐藏
//            hideControlLayout();
//        } else {
//            //显示控制面板
//            ViewPropertyAnimator.animate(mTopControlLl).translationY(0f);
//            ViewPropertyAnimator.animate(mBottomControlLl).translationY(0f);
//            //5秒后自动隐藏控制面板
//            HideControlLayoutDelay();
//        }
//    }
//
//    /**
//     * 5秒后自动隐藏控制面板
//     */
//    private void HideControlLayoutDelay() {
//        mHandler.removeCallbacks(mHideControlLayoutRunnable);
//        mHandler.postDelayed(mHideControlLayoutRunnable, 5000);
//    }
//
//    /**
//     * 取消5秒后自动隐藏控制面板,在界面有用户操作(按钮单击、SeekBar手动、Touch事件)的时候取消
//     */
//    private void cancelHideControlLayout() {
//        mHandler.removeCallbacks(mHideControlLayoutRunnable);
//    }
//
//    /**
//     * 更新电量的图标
//     *
//     * @param level
//     */
//    private void updateBatteryByLevel(int level) {
//        int resId;
//        if (level == 0) {
//            resId = R.drawable.ic_battery_0;
//        } else if (level <= 10) {
//            resId = R.drawable.ic_battery_10;
//        } else if (level <= 20) {
//            resId = R.drawable.ic_battery_20;
//        } else if (level <= 40) {
//            resId = R.drawable.ic_battery_40;
//        } else if (level <= 60) {
//            resId = R.drawable.ic_battery_60;
//        } else if (level <= 80) {
//            resId = R.drawable.ic_battery_80;
//        } else {
//            resId = R.drawable.ic_battery_100;
//        }
//        mBatteryIv.setImageResource(resId);
//    }
//
//    @Override
//    public void onClick(View v) {
//        //操作前先取消隐藏控制版面
//        cancelHideControlLayout();
//        switch (v.getId()) {
//            case R.id.ib_voice:
//                toggleVolume();
//                break;
//            case R.id.ib_back:
//                onBackPressed();
//                break;
//            case R.id.ib_pre:
//                pre();
//                break;
//            case R.id.ib_play:
//                play();
//                break;
//            case R.id.ib_next:
//                next();
//                break;
//            case R.id.ib_fullScreen:
//                toggleFullscreen();
//                break;
//        }
//        //延迟隐藏控制版面
//        HideControlLayoutDelay();
//    }
//
//
//    /**
//     * 视频准备完毕的监听
//     */
//    MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
//        @Override
//        public void onPrepared(MediaPlayer mp) {
//            LogUtils.i(VitamioVideoPlayerActivity.this, ">>>>>>>>>onPrepared");
//            //隐藏加载圈
//            mLoadView.setHideProgressBarWidthAlpha();
//            //开始播放视频
//            mVideoView.start();
//            if (null != mVideo) {
//                //显示视频名称
//                mTitleTv.setText(mVideo.title);
//            }
//            hideControlLayout();
//            //设置视频进度的最大值
//            mVideoSb.setMax((int) mVideoView.getDuration());
//            //设置视频总时长
//            mVideoDurationTv.setText(TimeUtils.format(mVideoView.getDuration()));
//            //更新播放的进度
//            updateVideoPosition();
//            //设置播放按钮的背景选择器
//            setPlayBgSelector();
//        }
//    };
//    /**
//     * 视频播放完毕的监听
//     */
//    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
//        @Override
//        public void onCompletion(MediaPlayer mp) {
//            //播放完后,将当前播放进度设置为0
//            // mHandler.removeCallbacks(mUpdateVideoPositionRunnable);
//            mLoadView.setProgressBarVisiable(false);
//            mCurrVideoPositionTv.setText(TimeUtils.format(0));
//            mVideoSb.setProgress(0);
//            mVideoView.seekTo(0);
//            mPlayIb.setImageResource(R.drawable.selector_ib_play);//恢复为播放按钮
//        }
//    };
//
//    /**
//     * 电量改变的接收器
//     */
//    BroadcastReceiver mBatteryChangeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            PrintUtils.printIntent(intent);
//            int level = intent.getIntExtra("level", 0);//电量的等级
//            updateBatteryByLevel(level);
//        }
//    };
//    /**
//     * 更新系统时间的Runnable
//     */
//    Runnable mUpdateSysTimeRunnable = new Runnable() {
//        @Override
//        public void run() {
//            mSysTimeTv.setText(DateFormat.format("HH:mm:ss", System.currentTimeMillis()));
//            updateSysTime();
//        }
//    };
//    /**
//     * 更新视频播放进度的Runnable
//     */
//    Runnable mUpdateVideoPositionRunnable = new Runnable() {
//        @Override
//        public void run() {
//            //获取当前进度,是一个时间毫秒值
//            int currPosition = (int) mVideoView.getCurrentPosition();
//            //更新时间
//            mCurrVideoPositionTv.setText(TimeUtils.format(currPosition));
//            //更新seekBar
//            mVideoSb.setProgress(currPosition);
//            updateVideoPosition();
//        }
//    };
//    /**
//     * 定时隐藏控制面板的Runnable
//     */
//    Runnable mHideControlLayoutRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hideControlLayout();
//        }
//    };
//    /**
//     * 音量改变的SeekBar监听器
//     */
//    SeekBar.OnSeekBarChangeListener mVolumeSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            if (fromUser) {
//                //表示是用户拖动改变的
//                updateVolumePosition(progress);
//            }
//        }
//
//        //开始拖动
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            cancelHideControlLayout();
//        }
//
//        //停止拖动
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            HideControlLayoutDelay();
//        }
//    };
//    /**
//     * 视频进度改变的SeekBar监听器
//     */
//    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            if (fromUser) {
//                //表示是用户拖动改变的
//                mVideoView.seekTo(progress);
//            }
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            cancelHideControlLayout();
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            HideControlLayoutDelay();
//        }
//    };
//    /**
//     * 视频缓冲监听器
//     */
//    MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
//        @Override
//        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            updateSecondaryProgress(percent);
//        }
//    };
//    /**
//     * 视频卡顿监听器
//     */
//    MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
//        @Override
//        public boolean onInfo(MediaPlayer mp, int what, int extra) {
//            switch (what) {
//                case MediaPlayer.MEDIA_INFO_BUFFERING_START: //开始缓冲
//                    mLoadView.setProgressBarVisiable(true);
//                    break;
//                case MediaPlayer.MEDIA_INFO_BUFFERING_END://停止缓冲:
//                    mLoadView.setHideProgressBarWidthAlpha();
//                    break;
//
//            }
//            return true;
//        }
//    };
//    /**
//     * 手势监听器
//     */
//    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
//        //长按
//        @Override
//        public void onLongPress(MotionEvent e) {
//            play();
//        }
//
//        //滚动
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            //获取是指滑动的距离,deltaY>0表示向上滑动,否则表示向下滑动
//            float deltaY = e1.getY() - e2.getY();
//            if (mIsLeftDown) {
//                //左侧按下,改变亮度,即mBrightnessView的透明度
//                changeGestureBrightness(deltaY);
//            } else {
//                //右侧按下,改变音量
//                changeGestureVolume(deltaY);
//            }
//            return true;
//        }
//
//        //按下
//        @Override
//        public boolean onDown(MotionEvent e) {
//            mCurrVolume = getStreamVolume();//获取当前音量
//            mCurrBrightness = mBrightnessView.getAlpha();//获取当前的亮度view的透明度
//            mIsLeftDown = e.getX() < UIUtils.getScreenWidth() / 2; //是否在屏幕左侧按下
//            return super.onDown(e);
//        }
//
//        //双击
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            //3种方式执行全屏
//            //toggleFullscreen();
//            //onClick(mFullScreenIb);
//            mFullScreenIb.performClick();    // 当过代码的方式去单击这个按钮
//            return true;
//        }
//
//        //单击
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            //5秒后自动隐藏
//            toggleHideControlLayout();
//            return true;
//        }
//    };
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //传递触摸事件给手势监听器
//        mGestureDetector.onTouchEvent(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                HideControlLayoutDelay();//5秒后自动隐藏
//                break;
//            default:
//                cancelHideControlLayout();//取消自动隐藏
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //显示系统时间
//        updateSysTime();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mHandler.removeCallbacks(mUpdateSysTimeRunnable);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (null != mBatteryChangeReceiver) {
//            unregisterReceiver(mBatteryChangeReceiver);
//        }
//    }
//}
