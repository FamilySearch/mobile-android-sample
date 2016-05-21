package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.model.User;
import org.familysearch.sampleapp.service.TreeServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        User user = getIntent().getParcelableExtra(Utilities.KEY_USER);

        TreeServices task = new TreeServices(this, user);
        task.execute();
    }
}
