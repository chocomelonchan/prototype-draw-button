package net.chocomelon.sketch;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.SeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.view_draw)
    DrawView mDrawView;

    @InjectView(R.id.button_draw)
    Button mDrawButton;

    @InjectView(R.id.seekbar_kankaku_updown)
    SeekBar mKankakuUpDownSeekBar;

    @InjectView(R.id.seekbar_kankaku_leftright)
    SeekBar mKankakuLeftRightSeekBar;

    @InjectView(R.id.container_seekbar)
    View mSeekbarContainer;

    private float ONE_PROGRESS_DISTANCE = 70f;

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
                        (mKankakuLeftRightSeekBar.getMax() / 2 - mKankakuLeftRightSeekBar.getProgress()) * ONE_PROGRESS_DISTANCE, progress * ONE_PROGRESS_DISTANCE));
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
                        (mKankakuLeftRightSeekBar.getMax() / 2 - progress) * ONE_PROGRESS_DISTANCE, mKankakuUpDownSeekBar.getProgress() * ONE_PROGRESS_DISTANCE));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @OnClick(R.id.button_reload)
    public void onReloadButtonClick() {
        mDrawView.reset();
    }

    @OnClick(R.id.button_position)
    public void onPositionButtonClick() {
        int height = getResources().getDimensionPixelSize(R.dimen.buttons_container_height);
        if (mSeekbarContainer.getVisibility() == View.VISIBLE) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -height);
            anim.setDuration(300);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSeekbarContainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mSeekbarContainer.startAnimation(anim);
        } else {
            TranslateAnimation anim = new TranslateAnimation(0, 0, -height, 0);
            anim.setDuration(300);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mSeekbarContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mSeekbarContainer.startAnimation(anim);
        }
    }
}
