package net.chocomelon.sketch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private DrawView mDrawView;
    private Button mDrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawButton = findViewById(R.id.button_draw);
        mDrawView = findViewById(R.id.view_draw);

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
        mDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mDrawView.drawContinuity(true);
            }
        });
    }
}
