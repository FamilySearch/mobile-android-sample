package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.model.user.User;
import org.familysearch.sampleapp.utils.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * @author Eduardo Flores
 */
public class MemoryServices extends AsyncTask<String, String, String>
{
    private Context context;

    private User user;

    public MemoryServices(Context context, User user)
    {
        this.context = context;

        this.user = user;
    }

    @Override
    protected String doInBackground(String... params) {

        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        return null;
    }
}
