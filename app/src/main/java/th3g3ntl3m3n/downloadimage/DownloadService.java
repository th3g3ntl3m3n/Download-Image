package th3g3ntl3m3n.downloadimage;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by th3g3ntl3m3n on 6/9/17.
 */

public class DownloadService extends IntentService{
    Bitmap bmap;
    private int result = 0;
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        String dataString = intent.getStringExtra("url");
        Bitmap bitmap = downloadImage(dataString);
        bmap = bitmap;
        Intent intent1 = new Intent("th3g3ntl3m3n.downloadimage");
        intent1.putExtra("result", result);
        intent1.putExtra("bitmap", bitmap);
        sendBroadcast(intent1);
    }

    private Bitmap downloadImage(String url) {
        URL url1 = null;
        try {
            url1 = getUrlFromString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream inputStream = getInputStream(url1);
        return decodeBitmap(inputStream);
    }

    private Bitmap decodeBitmap(InputStream inputStream) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(inputStream);
            result = 1;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private InputStream getInputStream(URL url) {
        InputStream inputStream = null;
        URLConnection urlConnection;
        try {
            urlConnection = url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private URL getUrlFromString(String url) throws MalformedURLException {
        URL url1;
        url1 = new URL(url);
        return url1;
    }
}
