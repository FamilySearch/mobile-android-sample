package org.familysearch.sampleapp.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author Eduardo Flores
 */
public interface ImageDownloaderListener
{
    void onPictureDownloadSucceeded(String key, Bitmap bitmap, ImageView imageView);

}
