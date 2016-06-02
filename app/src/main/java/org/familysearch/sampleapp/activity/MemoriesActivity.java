package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.MemoriesListener;
import org.familysearch.sampleapp.model.user.User;
import org.familysearch.sampleapp.service.MemoryServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MemoriesActivity extends AppCompatActivity implements MemoriesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        setTitle(getString(R.string.memories_title));

        User user = getIntent().getParcelableExtra(Utilities.KEY_USER);

        MemoryServices task = new MemoryServices(this, user);
        task.setOnMemoriesListener(this);
        task.execute();
    }

    @Override
    public void onMemoriesBitmapSucceeded(List<Bitmap> bitmapList) {

    }
}
