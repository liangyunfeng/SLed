package com.lyf.app.sled.spark;

import java.util.Random;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by yunfeng.l on 2018/2/6.
 */
public class SparkManager {

    // 火花数组
    private int mWidth, mHeight;

    private int[][] sparks = new int[500][11];

    private final int CENTER_X_RANGE = 30;

    private final int CENTER_Y_RANGE = 30;

    private int MAX_DISTANCE;

    private int MAX_BEZIER_RADIUS;

    private int mCenterX, mCenterY;

    // 画笔对象
    private Paint mSparkPaint;

    // 当前触摸位置
    private int X, Y;

    // 花火半径
    private float radius = 0;

    // 火花喷射距离
    private float mDistance = 0;

    // 当前喷射距离
    private float mCurDistance = 0;

    // 火花半径
    private static final float SPARK_RADIUS = 4.0F;

    // 火花外侧阴影大小
    private static final float BLUR_SIZE = 12F;

    // 每帧速度
    private static final float PER_SPEED_SEC = 3.0F;        //5.0F

    // 随机数
    private Random mRandom = new Random();

    // 火花的起始点，终点，塞贝儿曲线拐点1，塞贝儿曲线拐点2
    private Point start, end, c1, c2;
    private int r;

    // 是否是激活状态
    private boolean isActive = true;

    private int[] ColorIds = new int[]{
            Color.BLUE, Color.GREEN, Color.RED, Color.DKGRAY, Color.CYAN,
            Color.WHITE, Color.YELLOW, Color.LTGRAY, Color.MAGENTA
    };

    private int DISTANCE[] = new int[5];

    public SparkManager(int width, int height) {
        mWidth = width;
        mHeight = height;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        MAX_DISTANCE = mWidth / 2;
        MAX_BEZIER_RADIUS = mWidth / 16;
        DISTANCE[0] = MAX_DISTANCE / 3;
        DISTANCE[1] = MAX_DISTANCE / 2;
        DISTANCE[2] = MAX_DISTANCE;
        DISTANCE[3] = (int) (MAX_DISTANCE * 1.1f);
        DISTANCE[4] = (int) (MAX_DISTANCE * 1.2f);
        setSparkPaint();
    }

    public void drawSpark(Canvas canvas) {
        for (int[] n : sparks) {
            drawSpark(canvas, mCenterX + getRandomPNValue(mRandom.nextInt(CENTER_X_RANGE)), mCenterY + getRandomPNValue(mRandom.nextInt(CENTER_Y_RANGE)), n);
        }
    }

    public void drawSpark(Canvas canvas, int x, int y, int[] store) {
        this.X = x;
        this.Y = y;
        this.mCurDistance = store[0];
        this.mDistance = store[1];

        if (mCurDistance == mDistance && isActive) {
            mDistance = getRandom(mRandom.nextInt(15)) + 30;
            mCurDistance = 0;

            start = new Point(X, Y);
            end = getRandomPoint(start.x, start.y, (int) mDistance);
            c1 = getRandomPoint(start.x, start.y, mRandom.nextInt(MAX_BEZIER_RADIUS));
            c2 = getRandomPoint(end.x, end.y, mRandom.nextInt(MAX_BEZIER_RADIUS));
            r = mRandom.nextInt(4) + 4;
        } else {
            start.set(store[2], store[3]);
            end.set(store[4], store[5]);
            c1.set(store[6], store[7]);
            c2.set(store[8], store[9]);
            r = store[10];
        }

        updateSparkPath();
        Point bezierPoint = CalculateBezierPoint(mCurDistance / mDistance, start, c1, c2, end);
        mSparkPaint.setColor(ColorIds[mRandom.nextInt(9)]);
        canvas.drawCircle(bezierPoint.x, bezierPoint.y, radius, mSparkPaint);

        if (mCurDistance == mDistance) {
            store[0] = 0;
            store[1] = 0;
        } else {
            store[0] = (int) mCurDistance;
            store[1] = (int) mDistance;
            store[2] = (int) start.x;
            store[3] = (int) start.y;
            store[4] = (int) end.x;
            store[5] = (int) end.y;
            store[6] = (int) c1.x;
            store[7] = (int) c1.y;
            store[8] = (int) c2.x;
            store[9] = (int) c2.y;
            store[10] = r;
        }
    }

    private void updateSparkPath() {
        mCurDistance += PER_SPEED_SEC;
        if (mCurDistance < (mDistance / 2) && (mCurDistance != 0)) {
            radius = r * (mCurDistance / (mDistance / 2));
        } else if (mCurDistance > (mDistance / 2) && (mCurDistance < mDistance)) {
            radius = r - r * ((mCurDistance / (mDistance / 2)) - 1);
        } else if (mCurDistance >= mDistance) {
            mCurDistance = 0;
            mDistance = 0;
            radius = 0;
        }
    }

    private Point getRandomPoint(int baseX, int baseY, int r) {
        if (r <= 0) {
            r = 30;
        }
        int x = mRandom.nextInt(r + 1);
        int y = (int) Math.sqrt(r * r - x * x);
        x = baseX + getRandomPNValue(x);
        y = baseY + getRandomPNValue(y);
        return new Point(x, y);
    }

    private int getRandom(int chance) {
        int num = 0;
        switch (chance) {
            case 0:
            case 1:
                num = mRandom.nextInt(DISTANCE[0]);
                break;
            case 2:
            case 3:
            case 4:
                num = mRandom.nextInt(DISTANCE[1]);
                break;
            case 5:
            case 6:
                num = mRandom.nextInt(DISTANCE[3]);
                break;
            case 7:
                num = mRandom.nextInt(DISTANCE[4]);
                break;
            default:
                num = mRandom.nextInt(DISTANCE[2]);
                break;
        }

        return num;
    }

    /**
     * 获取随机正负数
     */
    private int getRandomPNValue(int value) {
        return mRandom.nextBoolean() ? value : 0 - value;
    }

    /**
     * 计算塞贝儿曲线
     *
     * @param t  时间，范围0-1
     * @param s  起始点
     * @param c1 拐点1
     * @param c2 拐点2
     * @param e  终点
     * @return 塞贝儿曲线在当前时间下的点
     */
    private Point CalculateBezierPoint(float t, Point s, Point c1, Point c2, Point e) {
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        Point p = new Point((int) (s.x * uuu), (int) (s.y * uuu));
        p.x += 3 * uu * t * c1.x;
        p.y += 3 * uu * t * c1.y;
        p.x += 3 * u * tt * c2.x;
        p.y += 3 * u * tt * c2.y;
        p.x += ttt * e.x;
        p.y += ttt * e.y;

        return p;
    }

    private void setSparkPaint() {
        mSparkPaint = new Paint();
        mSparkPaint.setAntiAlias(true);
        mSparkPaint.setDither(true);
        mSparkPaint.setStyle(Paint.Style.FILL);
        mSparkPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}