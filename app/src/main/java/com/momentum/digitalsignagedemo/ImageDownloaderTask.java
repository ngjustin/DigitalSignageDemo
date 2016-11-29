package com.momentum.digitalsignagedemo;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpStatus;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Justin on 11/5/2016.
 */

class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> ref;

    public ImageDownloaderTask(ImageView imageView) {
        ref = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        if (isCancelled()) {
            bm = null;
        }

        if (ref != null) {
            ImageView image = ref.get();
            if (image != null) {
                if (bm != null) {
                    image.setImageBitmap(bm);
                } else {
                    Drawable temp = image.getContext().getResources().getDrawable(R.drawable.placeholder);
                    image.setImageDrawable(temp);
                }
            }

        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();

            int statusCode = conn.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = conn.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            conn.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}