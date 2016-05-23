package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.adapter.TreeAdapter;
import org.familysearch.sampleapp.listener.TreeListener;
import org.familysearch.sampleapp.model.User;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.TreeServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class TreeActivity extends ListActivity implements TreeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = getIntent().getParcelableExtra(Utilities.KEY_USER);

        TreeServices task = new TreeServices(this, user);
        task.setOnTreeListener(this);
        task.execute();
    }

    @Override
    public void onGeneaologySucceeded(List<Persons> personsList) {
        Toast.makeText(this, "Found " + personsList.size() + " people in your geneaology", Toast.LENGTH_LONG).show();

        TreeAdapter treeAdapter = new TreeAdapter(this, personsList);
        setListAdapter(treeAdapter);
    }
}
