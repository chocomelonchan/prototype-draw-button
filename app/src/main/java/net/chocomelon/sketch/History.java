package net.chocomelon.sketch;

import android.graphics.Paint;
import android.graphics.Path;

public class History {

    public Path path;
    public Paint paint;

    public History(Path path, Paint paint) {
        this.path = new Path(path);
        this.paint = new Paint(paint);
    }
}
