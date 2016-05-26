package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.ImageDownloaderListener;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.ImageDownloaderServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonDetailsActivity extends AppCompatActivity implements ImageDownloaderListener
{

    private Persons persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        persons = getIntent().getParcelableExtra(Utilities.KEY_PERSONS);

        ImageView personPicture = (ImageView) findViewById(R.id.person_details_picture);
        TextView personName = (TextView) findViewById(R.id.person_details_name);
        TextView personBirth = (TextView) findViewById(R.id.person_details_birth);
        TextView personDeath = (TextView) findViewById(R.id.person_details_death);

        ImageDownloaderServices task = new ImageDownloaderServices(this, personPicture, null);
        task.setOnImageDownloaderListener(this);
        task.execute(persons.getLinks().getPerson().getHref());


        personName.setText(persons.getDisplay().getName());
        personBirth.setText(persons.getDisplay().getLifespan());
    }

    @Override
    public void onPictureDownloadSucceeded(String key, Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }
}
