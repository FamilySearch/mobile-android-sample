package org.familysearch.sampleapp.listener;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public interface MemoriesListener
{
    void onMemoriesBitmapSucceeded(List<Bitmap> bitmapList);
}
