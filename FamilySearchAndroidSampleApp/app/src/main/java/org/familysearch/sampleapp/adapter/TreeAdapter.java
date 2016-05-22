package org.familysearch.sampleapp.adapter;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.model.ancestry.Persons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        TextView ancestorName = (TextView) rowView.findViewById(R.id.tree_ancestor_name);
        TextView ancestorLifespan = (TextView) rowView.findViewById(R.id.tree_ancestor_lifespan);

        ancestorName.setText(person.getDisplay().getName());
        ancestorLifespan.setText(person.getDisplay().getLifespan());
        layout.setVisibility(View.VISIBLE);
        return rowView;
    }
}
