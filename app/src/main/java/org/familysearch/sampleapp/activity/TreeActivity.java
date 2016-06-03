package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.adapter.TreeAdapter;
import org.familysearch.sampleapp.listener.TreeListener;
import org.familysearch.sampleapp.model.user.User;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.TreeServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TreeActivity extends AppCompatActivity implements TreeListener {

    private LruCache<String, Bitmap> memoryCache;

    private List<Persons> personsList;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        setTitle(getString(R.string.tree_title));

        // setup memory for image cache
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        user = getIntent().getParcelableExtra(Utilities.KEY_USER);

        TreeServices task = new TreeServices(this, user);
        task.setOnTreeListener(this);
        task.execute();
    }

    @Override
    public void onGeneaologySucceeded(List<Persons> personsList) {
        Toast.makeText(this, "Found " + personsList.size() + " people in your geneaology", Toast.LENGTH_LONG).show();

        ListView listView = (ListView)findViewById(R.id.tree_listview);

        this.personsList = personsList;
        TreeAdapter treeAdapter = new TreeAdapter(this, personsList, memoryCache);
        listView.setAdapter(treeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.menu_home:
                finish();
                break;
            case R.id.menu_tree:
                Toast.makeText(this,
                        String.format("%s %s", getString(R.string.current_activity), getClass().getSimpleName()),
                        Toast.LENGTH_LONG)
                        .show();
                break;
            case R.id.menu_memories:
                Intent intentMemories = new Intent(this, MemoriesActivity.class);
                intentMemories.putExtra(Utilities.KEY_USER, user);
                startActivity(intentMemories);
                break;
        }
        return true;
    }
}
