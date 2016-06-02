package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.model.user.User;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

        getMemoriesForUser(user, accessToken);

        return null;
    }

    private void getMemoriesForUser(User user, String accessToken)
    {
        try
        {
            // send the logged user's artifact link
            URL genericTreeUrl = new URL(user.getLinks().getArtifacts().getHref());
            HttpURLConnection connection = (HttpURLConnection) genericTreeUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // read the response of the logged user's artifact link
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // convert the inputStream response to String
                StringBuilder stringBuilder = new StringBuilder(inputStream.available());
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
                String responseString = stringBuilder.toString();

                // convert the response from String to JSONObject
                JSONObject responseJsonObject = new JSONObject(responseString);
                Log.i(Utilities.LOG_TAG, "MemoryServices.getMemoriesForUser() data: " + responseJsonObject.toString());
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
