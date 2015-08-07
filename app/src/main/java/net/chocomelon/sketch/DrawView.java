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
    private PointF mDownPointerPoint = new PointF(mPointerPoint.x, mPointerPoint.y);

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

    public void drawContinuity(boolean continuity) {
        synchronized (mLock) {
            mDrawContinuity = continuity;
            mDrawTrigger = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (mLock) {
            if (mPreviousEvent == null || event.getAction() == MotionEvent.ACTION_DOWN) {
                mPreviousEvent = MotionEvent.obtain(event);
            }

            float degreeX = event.getX() - mPreviousEvent.getX();
            float degreeY = event.getY() - mPreviousEvent.getY();

            float movedX = mPointerPoint.x + degreeX;
            float movedY = mPointerPoint.y + degreeY;

            mPointerPoint = new PointF(
                    movedX < 0 ? 0 : getWidth() < movedX ? getWidth() : movedX,
                    movedY < 0 ? 0 : getHeight() < movedY ? getHeight() : movedY
            );
            mPreviousEvent = MotionEvent.obtain(event);

            if (mDrawContinuity) {
                if (mDrawTrigger) {
                    mDrawPath.moveTo(mPointerPoint.x, mPointerPoint.y);
                    mDrawTrigger = false;
                } else {
                    mDrawPath.lineTo(mPointerPoint.x, mPointerPoint.y);
                }
            }
            invalidate();
        }
        return true;
    }
}
