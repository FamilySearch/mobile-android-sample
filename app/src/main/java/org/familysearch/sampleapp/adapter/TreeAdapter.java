package org.familysearch.sampleapp.adapter;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.ImageDownloaderListener;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.ImageDownloaderServices;

import android.content.Context;
import android.graphics.Bitmap;
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
public class TreeAdapter extends ArrayAdapter<Persons>
{
    private Context context;

    private List<Persons> personsList;

    public TreeAdapter(Context context, List<Persons> objects) {
        super(context, R.layout.activity_tree, objects);

        this.context = context;
        this.personsList = objects;
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

        ImageDownloaderServices task = new ImageDownloaderServices(context, ancestorPicture);
        task.execute(person.getLinks().getPerson().getHref());

        ancestorName.setText(person.getDisplay().getName());
        ancestorLifespan.setText(person.getDisplay().getLifespan());
        layout.setVisibility(View.VISIBLE);
        return rowView;
    }
}
