package org.familysearch.sampleapp.adapter;

import org.familysearch.sampleapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public class MemoriesAdapter extends ArrayAdapter<Bitmap>
{
    private Context context;

    private List<Bitmap> bitmapList;

    public MemoriesAdapter(Context context, List<Bitmap> bitmapList)
    {
        super(context, R.layout.memories_item, bitmapList);

        this.context = context;
        this.bitmapList = bitmapList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.memories_item, parent, false);

        Bitmap bitmap = bitmapList.get(position);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.memories_image);
        imageView.setImageBitmap(bitmap);

        return rowView;
    }
}
