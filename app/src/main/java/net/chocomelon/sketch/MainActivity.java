package net.chocomelon.sketch;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

    @InjectView(R.id.view_draw)
    DrawView mDrawView;

    @InjectView(R.id.button_draw)
    Button mDrawButton;

    @InjectView(R.id.seekbar_kankaku_updown)
    SeekBar mKankakuUpDownSeekBar;

    @InjectView(R.id.seekbar_kankaku_leftright)
    SeekBar mKankakuLeftRightSeekBar;

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mDrawButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mKankakuUpDownSeekBar.setAlpha(0.2f);
                        mKankakuLeftRightSeekBar.setAlpha(0.2f);
                        mDrawView.drawContinuity(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        mKankakuUpDownSeekBar.setAlpha(1f);
                        mKankakuLeftRightSeekBar.setAlpha(1f);
                        mDrawView.drawContinuity(false);
                        break;
                }
                return true;
            }
        });

        mKankakuUpDownSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDrawView.setPointerDistance(new PointF(
                        mKankakuLeftRightSeekBar.getMax() / 2 - mKankakuLeftRightSeekBar.getProgress(), progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mKankakuLeftRightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDrawView.setPointerDistance(new PointF(
                        mKankakuLeftRightSeekBar.getMax() / 2 - progress, mKankakuUpDownSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
