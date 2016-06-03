package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.listener.ImageDownloaderListener;
import org.familysearch.sampleapp.utils.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Eduardo Flores
 *
 * Image Downloader Task used to only download images from a given url to an ImageView
 */
public class ImageDownloaderServices extends AsyncTask<String, String, Bitmap> {

    private Context context;

    private ImageView imageView;

    private String imageCacheKey;

    private ImageDownloaderListener listener;

    public ImageDownloaderServices(Context context, ImageView imageView, String imageCacheKey)
    {
        this.context = context;
        this.imageView = imageView;
        this.imageCacheKey = imageCacheKey;
    }

    public void setOnImageDownloaderListener(ImageDownloaderListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        return downloadImage(params[0], accessToken);
    }

    private Bitmap downloadImage(String urlString, String accessToken) {
        String defaultImageUrl = "http://fsicons.org/wp-content/uploads/2014/10/gender-unknown-circle-2XL.png";
        String imageUrlString = urlString + "/portrait";
        imageUrlString = imageUrlString + "?access_token=" + accessToken;
        imageUrlString = imageUrlString + "&default=" + defaultImageUrl;

        Bitmap bitmap =null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try
        {
            // send the image url
            URL imageUrl = new URL(imageUrlString);
            connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            else if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP ||
                    connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ||
                    connection.getResponseCode() == HttpURLConnection.HTTP_SEE_OTHER ||
                    connection.getResponseCode() == 307)
            {
                // handle redirect, make a second call to get the default image
                imageUrl = new URL(defaultImageUrl);
                connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setRequestMethod("GET");
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        }
        catch(Exception e)
        {
            System.err.println("Exception downloading image. Exception: " + e);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null)
        {
            listener.onPictureDownloadSucceeded(imageCacheKey, bitmap, imageView);
        }
    }
}
