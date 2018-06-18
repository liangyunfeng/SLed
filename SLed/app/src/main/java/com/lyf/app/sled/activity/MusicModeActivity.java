package com.lyf.app.sled.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

/**
 * Created by yunfeng.l on 2018/1/25.
 * <p>
 * http://blog.csdn.net/Qzhongwenze/article/details/52851297
 * http://blog.csdn.net/com6339663/article/details/42681103#
 */
public class MusicModeActivity extends Activity {
    private final static String TAG = "MusicModeActivity";

    private static final float VISUALIZER_HEIGHT_DIP = 150f;//频谱View高度

    private MediaPlayer mMediaPlayer;//音频
    private Visualizer mVisualizer;//频谱器
    private Equalizer mEqualizer; //均衡器

    private LinearLayout mLayout;//代码布局
    MusicModeView mBaseVisualizerView;

    ImageButton play;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setVolumeControlStream(AudioManager.STREAM_MUSIC);//设置音频流 - STREAM_MUSIC：音乐回放即媒体音量


        mLayout = new LinearLayout(this);//代码创建布局
        mLayout.setOrientation(LinearLayout.VERTICAL);//设置为线性布局-上下排列
//        mLayout.setBackgroundResource(R.drawable.ac88o);//设置界面背景
        mLayout.setGravity(Gravity.CENTER);
        setContentView(mLayout);//将布局添加到 Activity

        //mMediaPlayer = MediaPlayer.create(this, R.raw.aaaass);//实例化 MediaPlayer 并添加音频                 // compile error

        setupVisualizerFxAndUi();//添加频谱到界面
        setupEqualizeFxAndUi();//添加均衡器到界面
        setupPlayButton();//添加按钮到界面


        mVisualizer.setEnabled(true);//false 则不显示
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });

        mMediaPlayer.start();//开始播放
        mMediaPlayer.setLooping(true);//循环播放

    }

    /**
     * 通过mMediaPlayer返回的AudioSessionId创建一个优先级为0均衡器对象 并且通过频谱生成相应的UI和对应的事件
     */
    private void setupEqualizeFxAndUi() {

        TextView kongge = new TextView(this);
        kongge.setText("");
        kongge.setTextSize(10);
        mLayout.addView(kongge);

        mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
        mEqualizer.setEnabled(true);// 启用均衡器

        // 通过均衡器得到其支持的频谱引擎
        short bands = mEqualizer.getNumberOfBands();

        // getBandLevelRange 是一个数组，返回一组频谱等级数组，
        // 第一个下标为最低的限度范围
        // 第二个下标为最大的上限,依次取出
        final short minEqualizer = mEqualizer.getBandLevelRange()[0];
        final short maxEqualizer = mEqualizer.getBandLevelRange()[1];

        for (short i = 0; i < bands; i++) {
            final short band = i;

            TextView freqTextView = new TextView(this);
            freqTextView.setTextColor(Color.WHITE);
            freqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            freqTextView.setGravity(Gravity.CENTER_HORIZONTAL);

            // 取出中心频率
            freqTextView.setText((mEqualizer.getCenterFreq(band) / 1000) + "HZ");
            mLayout.addView(freqTextView);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView minDbTextView = new TextView(this);
            minDbTextView.setTextColor(Color.WHITE);
            minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            minDbTextView.setText((minEqualizer / 100) + " dB");

            TextView maxDbTextView = new TextView(this);
            maxDbTextView.setTextColor(Color.WHITE);
            maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextView.setText((maxEqualizer / 100) + " dB");

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 40);

            layoutParams.weight = 1;
            SeekBar seekbar = new SeekBar(this);
            seekbar.setLayoutParams(layoutParams);
            seekbar.setPadding(15, 0, 15, 0);
            //seekbar.setThumb(getResources().getDrawable(R.drawable.seek_bar_dian_selector));                      // compile error
            seekbar.setThumbOffset(20);
            seekbar.setMax(maxEqualizer - minEqualizer);
            seekbar.setProgress(mEqualizer.getBandLevel(band));


            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mEqualizer.setBandLevel(band, (short) (progress + minEqualizer));
                }
            });
            row.addView(minDbTextView);
            row.addView(seekbar);
            row.addView(maxDbTextView);

            mLayout.addView(row);

        }
        TextView eqTextView = new TextView(this);
        eqTextView.setTextColor(Color.WHITE);
        eqTextView.setText("均衡器");
        eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        eqTextView.setTextSize(20);

        mLayout.addView(eqTextView);

    }

    /**
     * 生成一个VisualizerView对象，使音频频谱的波段能够反映到 VisualizerView上
     */
    private void setupVisualizerFxAndUi() {
        mBaseVisualizerView = new MusicModeView(this);

        mBaseVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,//宽度
                (int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)//高度
        ));
        //将频谱View添加到布局
        mLayout.addView(mBaseVisualizerView);
        //实例化Visualizer，参数SessionId可以通过MediaPlayer的对象获得
        mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
        //采样 - 参数内必须是2的位数 - 如64,128,256,512,1024
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //设置允许波形表示，并且捕获它
        mBaseVisualizerView.setVisualizer(mVisualizer);
    }

    //播放按钮
    private void setupPlayButton() {
        play = new ImageButton(this);
        play.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //play.setBackgroundResource(R.drawable.new_main_activity_stop_up);                              // compile error
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) {
                    //play.setBackgroundResource(R.drawable.new_main_activity_play_up);                 // compile error
                    mMediaPlayer.pause();
                } else {
                    //play.setBackgroundResource(R.drawable.new_main_activity_stop_up);                 // compile error
                    mMediaPlayer.start();
                }
            }
        });

        mLayout.addView(play);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mMediaPlayer != null) {
            mVisualizer.release();
            mMediaPlayer.release();
            mEqualizer.release();
            mMediaPlayer = null;
        }
    }
}

class MusicModeView extends View implements Visualizer.OnDataCaptureListener {

    private static final int DN_W = 470;//view宽度与单个音频块占比 - 正常480 需微调
    private static final int DN_H = 360;//view高度与单个音频块占比
    private static final int DN_SL = 15;//单个音频块宽度
    private static final int DN_SW = 5;//单个音频块高度

    private int hgap = 0;
    private int vgap = 0;
    private int levelStep = 0;
    private float strokeWidth = 0;
    private float strokeLength = 0;

    protected final static int MAX_LEVEL = 30;//音量柱·音频块 - 最大个数

    protected final static int CYLINDER_NUM = 26;//音量柱 - 最大个数

    protected Visualizer mVisualizer = null;//频谱器

    protected Paint mPaint = null;//画笔

    protected byte[] mData = new byte[CYLINDER_NUM];//音量柱 数组

    boolean mDataEn = true;

    //构造函数初始化画笔
    public MusicModeView(Context context) {
        super(context);

        mPaint = new Paint();//初始化画笔工具
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.WHITE);//画笔颜色

        mPaint.setStrokeJoin(Paint.Join.ROUND); //频块圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); //频块圆角
    }

    //执行 Layout 操作
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float w, h, xr, yr;

        w = right - left;
        h = bottom - top;
        xr = w / (float) DN_W;
        yr = h / (float) DN_H;

        strokeWidth = DN_SW * yr;
        strokeLength = DN_SL * xr;
        hgap = (int) ((w - strokeLength * CYLINDER_NUM) / (CYLINDER_NUM + 1));
        vgap = (int) (h / (MAX_LEVEL + 2));//频谱块高度

        mPaint.setStrokeWidth(strokeWidth); //设置频谱块宽度
    }

    //绘制频谱块和倒影
    protected void drawCylinder(Canvas canvas, float x, byte value) {
        if (value == 0) {
            value = 1;
        }//最少有一个频谱块
        for (int i = 0; i < value; i++) { //每个能量柱绘制value个能量块
            float y = (getHeight() / 2 - i * vgap - vgap);//计算y轴坐标
            float y1 = (getHeight() / 2 + i * vgap + vgap);
            //绘制频谱块
            mPaint.setColor(Color.WHITE);//画笔颜色
            canvas.drawLine(x, y, (x + strokeLength), y, mPaint);//绘制频谱块

            //绘制音量柱倒影
            if (i <= 6 && value > 0) {
                mPaint.setColor(Color.WHITE);//画笔颜色
                mPaint.setAlpha(100 - (100 / 6 * i));//倒影颜色
                canvas.drawLine(x, y1, (x + strokeLength), y1, mPaint);//绘制频谱块
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        int j = -4;
        for (int i = 0; i < CYLINDER_NUM / 2 - 4; i++) { //绘制25个能量柱

            drawCylinder(canvas, strokeWidth / 2 + hgap + i * (hgap + strokeLength), mData[i]);
        }
        for (int i = CYLINDER_NUM; i >= CYLINDER_NUM / 2 - 4; i--) {
            j++;
            drawCylinder(canvas, strokeWidth / 2 + hgap + (CYLINDER_NUM / 2 + j - 1) * (hgap + strokeLength), mData[i - 1]);
        }
    }

    /**
     * It sets the visualizer of the view. DO set the viaulizer to null when exit the program.
     *
     * @parma visualizer It is the visualizer to set.
     */
    public void setVisualizer(Visualizer visualizer) {
        if (visualizer != null) {
            if (!visualizer.getEnabled()) {
                visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
            }
            levelStep = 230 / MAX_LEVEL;
            visualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate() / 2, false, true);

        } else {

            if (mVisualizer != null) {
                mVisualizer.setEnabled(false);
                mVisualizer.release();
            }
        }
        mVisualizer = visualizer;
    }

    //这个回调应该采集的是快速傅里叶变换有关的数据
    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
        byte[] model = new byte[fft.length / 2 + 1];
        if (mDataEn) {
            model[0] = (byte) Math.abs(fft[1]);
            int j = 1;
            for (int i = 2; i < fft.length; ) {
                model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
                i += 2;
                j++;
            }
        } else {
            for (int i = 0; i < CYLINDER_NUM; i++) {
                model[i] = 0;
            }
        }
        for (int i = 0; i < CYLINDER_NUM; i++) {
            final byte a = (byte) (Math.abs(model[CYLINDER_NUM - i]) / levelStep);

            final byte b = mData[i];
            if (a > b) {
                mData[i] = a;
            } else {
                if (b > 0) {
                    mData[i]--;
                }
            }
        }
        postInvalidate();//刷新界面
    }

    //这个回调应该采集的是波形数据
    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
        // Do nothing...
    }
}