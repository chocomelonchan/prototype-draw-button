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
    private boolean mDrawTrigger;
    private Paint mPointerPaint;
    private int POINTER_RADIUS = 10;
    private PointF POINTER_DISTANCE_POINT = new PointF(0, 150);
    private PointF mPointerPoint = new PointF(100, 100);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        mPointerPoint = new PointF(event.getX() - POINTER_DISTANCE_POINT.x,
                event.getY() - POINTER_DISTANCE_POINT.y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(mPointerPoint.x, mPointerPoint.y);
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(mPointerPoint.x, mPointerPoint.y);
                break;
            case MotionEvent.ACTION_UP:
                mDrawPath.lineTo(mPointerPoint.x, mPointerPoint.y);
                break;
        }
        invalidate();
        return true;
    }
}
