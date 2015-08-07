package net.chocomelon.sketch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.view_draw)
    DrawView mDrawView;

    @InjectView(R.id.button_draw)
    Button mDrawButton;

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
                        mDrawView.drawContinuity(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        mDrawView.drawContinuity(false);
                        break;
                }
                return true;
            }
        });
    }

    @OnClick(R.id.button_draw)
    public void onDrawButtonClick() {
        mDrawView.drawContinuity(true);
    }
}
