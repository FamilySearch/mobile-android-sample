package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.ImageDownloaderListener;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.service.ImageDownloaderServices;
import org.familysearch.sampleapp.service.PersonDetailsServices;
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

        ImageDownloaderServices imageDownloaderTask = new ImageDownloaderServices(this, personPicture, null);
        imageDownloaderTask.setOnImageDownloaderListener(this);
        imageDownloaderTask.execute(persons.getLinks().getPerson().getHref());

        // since we can't implement 2 listeners, we need to pass the textviews to the task
        // and make the task display the downloaded elements
        PersonDetailsServices personsTask = new PersonDetailsServices(this, personBirth, personDeath);
        personsTask.execute(persons.getLinks().getPerson().getHref());

        personName.setText(persons.getDisplay().getName());
    }

    @Override
    public void onPictureDownloadSucceeded(String key, Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }
}
