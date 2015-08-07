package net.chocomelon.sketch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ColorPalletView extends View {

    int[] COLORS = new int[]{
            Color.BLACK,
            Color.WHITE
    };

    List<Paint> mPaints;

    public ColorPalletView(Context context) {
        super(context);
        setup();
    }

    public ColorPalletView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPalletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        mPaints = new ArrayList<>();
        for (int color : COLORS) {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            mPaints.add(paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int oneColorWidth = width / mPaints.size();
        int radius = getHeight() / 2;
        for (int i = 0; i < mPaints.size(); i++) {
            canvas.drawCircle(i * oneColorWidth + oneColorWidth / 2, radius, radius, mPaints.get(i));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            int width = getWidth();
            int height = getHeight();
            if (x < 0 || width < x || y < 0 || height < y) {
                return true;
            }
            int oneColorWidth = width / mPaints.size();
            EventBus.getDefault().post(new Events.ColorChangeEvent(COLORS[(int) (x / oneColorWidth)]));
        }
        return true;
    }
}
