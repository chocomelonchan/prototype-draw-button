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
    private List<Path> mDrawPathes;

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
        mDrawPathes = new ArrayList<>();

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
        canvas.drawPath(mDrawPath, mDrawPaint);
        canvas.drawCircle(mPointerPoint.x, mPointerPoint.y, POINTER_RADIUS, mPointerPaint);
    }

    public void reset() {
        synchronized (mLock) {
            setup();
            invalidate();
        }
    }

    public void back() {
        synchronized (mLock) {
            if (mDrawPathes.size() != 0) {
                mDrawPath = mDrawPathes.remove(mDrawPathes.size() - 1);
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
                    mDrawPathes.add(new Path(mDrawPath));
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
