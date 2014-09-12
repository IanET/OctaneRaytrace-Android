package ianet.octaneraytrace;

import android.graphics.Bitmap;

import ianet.octaneraytrace.Flog.Canvas;

public class BitmapCanvas extends Canvas {
    Bitmap bmp;

    public BitmapCanvas(int w, int h) {
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {
        // Ignoring w and h for now, just writing a single pixel
        int color = (0xFF << 24) | ((int)(this.fillStyle.red * 255)) << 16 | ((int)(this.fillStyle.green * 255)) << 8 | ((int)(this.fillStyle.blue * 255));
        bmp.setPixel(x, y, color);
    }

}
