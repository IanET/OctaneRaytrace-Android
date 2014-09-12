package ianet.octaneraytrace;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.graphics.*;

import ianet.octaneraytrace.Flog.Ray;
import ianet.octaneraytrace.Flog.RayTracer;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public class Data {
        long runs;
        long elapsed;
    }

    public void Measure(Data data, BitmapCanvas canvas) {
        // Run for a second
        long start = System.currentTimeMillis();
        long elapsed = 0;
        int i = 0;

        while (elapsed < 1000) {
            RayTracer.renderScene(canvas);
            i++;
            elapsed = System.currentTimeMillis() - start;
        }

        if (data != null) {
            data.runs += i;
            data.elapsed += elapsed;
        }
    }

    // Pulled from the Octane sources
    static int MIN_ITERATIONS = 32;
    static long REFERENCE_SCORE = 739989;
    static Thread thread;


    public boolean actionBenchmark() {
        final TextView tv = (TextView) findViewById(R.id.textview);
        final ImageView iv = (ImageView) findViewById(R.id.imageview);

        if (thread != null && thread.isAlive()) {
            return true;
        }

        tv.append("Raytrace...\r\n");
        thread = new Thread() { @Override public void run() {
            // Warmup
            Measure(null, null);
            // Benchmark
            Data data = new Data();
            while (data.runs < MIN_ITERATIONS) {
                Measure(data, null);
                final long elapsed = data.elapsed;
                final long runs = data.runs;
                tv.post(new Runnable() { @Override public void run() {
                    tv.append("Runs: " + runs + ", Elapsed: " + elapsed + "\r\n");
                }});
            }
            long usec = (data.elapsed * 1000) / data.runs;
            final long score = (REFERENCE_SCORE / usec) * 100;
            tv.post(new Runnable() { @Override public void run() {
                tv.append("Score: " + score + "\r\n");
                tv.append("Done\r\n\r\n");
            }});
        }};
        thread.start();
        return true;
    }

    public boolean actionRender() {
        final TextView tv = (TextView) findViewById(R.id.textview);
        final ImageView iv = (ImageView) findViewById(R.id.imageview);

        if (thread != null && thread.isAlive()) {
            return true;
        }

        final BitmapCanvas bc = new BitmapCanvas(iv.getWidth(), iv.getHeight());
        iv.setImageBitmap(bc.bmp);

        tv.append("Raytrace...\r\n");
        thread = new Thread() { @Override public void run() {
            RayTracer.renderScene(bc);
            tv.post(new Runnable() { @Override public void run() {
                tv.append("Done\r\n\r\n");
                iv.invalidate();
            }});
        }};
        thread.start();

        iv.postDelayed(new InvalidateRunnable(iv), 500);

        return true;
    }

    public class InvalidateRunnable implements Runnable {
        ImageView iv;

        public InvalidateRunnable(ImageView iv) {
            this.iv = iv;
        }

        public void run() {
            iv.invalidate();
            if (thread != null && thread.isAlive()) {
                iv.postDelayed(new InvalidateRunnable(iv), 500);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_benchmark) {
            return actionBenchmark();
        }

        if (id == R.id.action_render) {
            return actionRender();
        }

        return super.onOptionsItemSelected(item);
    }

}
