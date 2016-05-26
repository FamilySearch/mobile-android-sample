package org.familysearch.sampleapp.adapter;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.ImageDownloaderListener;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.ImageDownloaderServices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public class TreeAdapter extends ArrayAdapter<Persons> implements ImageDownloaderListener
{
    private Context context;

    private List<Persons> personsList;

    private LruCache<String, Bitmap> memoryCache;

    public TreeAdapter(Context context, List<Persons> objects, LruCache<String, Bitmap> memoryCache) {
        super(context, R.layout.activity_tree, objects);

        this.context = context;
        this.personsList = objects;
        this.memoryCache = memoryCache;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Persons person = personsList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_tree, parent, false);
        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.tree_ancestor_layout);
        ImageView ancestorPicture = (ImageView) rowView.findViewById(R.id.tree_ancestor_picture);
        TextView ancestorName = (TextView) rowView.findViewById(R.id.tree_ancestor_name);
        TextView ancestorLifespan = (TextView) rowView.findViewById(R.id.tree_ancestor_lifespan);

        Bitmap fsLogoBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_familysearchlogo);
        ancestorPicture.setImageBitmap(fsLogoBitmap);
        loadBitmapForPersons(context, memoryCache, person.getId(), ancestorPicture, person);

        ancestorName.setText(person.getDisplay().getName());
        ancestorLifespan.setText(person.getDisplay().getLifespan());
        layout.setVisibility(View.VISIBLE);
        return rowView;
    }

    // image memory cache to retain images during scrolling
    // Save the image to the LruCache cache as they download
    // use the position of the list item as the key in the cache
    // If the picture already exists in the cache, display the image from cache
    // If not, download the picture
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public void loadBitmapForPersons(Context context, LruCache<String, Bitmap> memoryCache,
            String imageKey, ImageView imageView, Persons person) {
        //final String imageKey = String.valueOf(position);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        } else
        {
            ImageDownloaderServices task = new ImageDownloaderServices(context, imageView, imageKey);
            task.setOnImageDownloaderListener(this);
            task.execute(person.getLinks().getPerson().getHref());
        }
    }

    @Override
    public void onPictureDownloadSucceeded(String key, Bitmap bitmap, ImageView imageView) {
        addBitmapToMemoryCache(key, bitmap);
        imageView.setImageBitmap(bitmap);
    }
}



















