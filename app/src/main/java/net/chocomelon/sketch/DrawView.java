package net.chocomelon.sketch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {

    private Paint mDrawPaint;
    private Path mDrawPath;
    private Object mLock;
    private boolean mDrawContinuity;
    private boolean mDrawTrigger;
    private Paint mPointerPaint;
    private int POINTER_RADIUS = 10;
    private MotionEvent mPreviousEvent;
    private PointF mPointerPoint = new PointF(100, 100);
    private PointF mPointerDistance = new PointF(0, 150);
    private List<History> mDrawHistories;

    public DrawView(Context context) {
        super(context);
        setup();
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        mDrawHistories = new ArrayList<>();

        mLock = new Object();

        mDrawPath = new Path();

        mDrawPaint = new Paint();
        mDrawPaint.setColor(Color.BLACK);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStrokeWidth(4);

        mPointerPaint = new Paint();
        mPointerPaint.setColor(Color.RED);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (History history : mDrawHistories) {
            canvas.drawPath(history.path, history.paint);
        }
        canvas.drawPath(mDrawPath, mDrawPaint);
        canvas.drawCircle(mPointerPoint.x, mPointerPoint.y, POINTER_RADIUS, mPointerPaint);
    }

    public void setColor(int color) {
        // 暫定対応
        mDrawHistories.add(new History(mDrawPath, mDrawPaint));
        mDrawPath = new Path();

        mDrawPaint.setColor(color);
    }

    public void reset() {
        synchronized (mLock) {
            setup();
            invalidate();
        }
    }

    public void back() {
        synchronized (mLock) {
            if (mDrawHistories.size() != 0) {
                History history = mDrawHistories.remove(mDrawHistories.size() - 1);
                mDrawPath = history.path;
                mDrawPaint = history.paint;
                invalidate();
            }
        }
    }

    public void drawContinuity(boolean continuity) {
        synchronized (mLock) {
            mDrawContinuity = continuity;
            mDrawTrigger = true;
        }
    }

    public void setPointerDistance(PointF pointerDistance) {
        synchronized (mLock) {
            mPointerDistance = pointerDistance;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (mLock) {
            mPointerPoint = new PointF(event.getX() - mPointerDistance.x,
                    event.getY() - mPointerDistance.y);

            if (mDrawContinuity) {
                if (mDrawTrigger) {
                    mDrawHistories.add(new History(mDrawPath, mDrawPaint));
                    mDrawPath = new Path();
                    mDrawPath.moveTo(mPointerPoint.x, mPointerPoint.y);
                    mDrawTrigger = false;
                } else {
                    mDrawPath.lineTo(mPointerPoint.x, mPointerPoint.y);
                }
            }

            invalidate();
            return true;
        }
    }
}
