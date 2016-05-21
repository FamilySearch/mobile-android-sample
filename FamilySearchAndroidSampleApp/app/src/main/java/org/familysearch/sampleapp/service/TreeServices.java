package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.model.User;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Eduardo Flores
 */
public class TreeServices extends AsyncTask<String, String, String> {

    private Context context;

    private User user;

    public TreeServices(Context context, User user)
    {
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(String... params) {

        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        String genericFamilyTreeUrlString = Utilities.getUrlsFromCollections().getFamilyTreeUrlString();

        getAncestryTree(getAncestryQueryUrlAsString(genericFamilyTreeUrlString), user.getPersonId(), accessToken);
        return null;
    }

    private String getAncestryQueryUrlAsString(String genericTreeUrl) {
        String ancestryQueryString = null;

        try {
            // send the collection url
            URL collectionUrl = new URL(genericTreeUrl);
            HttpURLConnection connection = (HttpURLConnection) collectionUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // read the response of the collection url
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
                if (responseJsonObject.has("collections"))
                {
                    JSONArray collectionsArray = responseJsonObject.getJSONArray("collections");
                    JSONObject collectionObject = collectionsArray.getJSONObject(0);

                    if (collectionObject.has("links"))
                    {
                        JSONObject ancestryLinks = collectionObject.getJSONObject("links");

                        if (ancestryLinks.has("ancestry-query"))
                        {
                            JSONObject ancestryQueryObject = ancestryLinks.getJSONObject("ancestry-query");

                            if (ancestryQueryObject.has("template"))
                            {
                                String[] templateElements = ancestryQueryObject.getString("template").split("\\{");
                                ancestryQueryString = templateElements[0];
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ancestryQueryString;
    }

    private void getAncestryTree(String ancestryUrlString, String userPersonId, String accessToken)
    {
        try {
            ancestryUrlString = ancestryUrlString + "?" + "person=" + userPersonId;
            ancestryUrlString = ancestryUrlString + "&" + "generations=" + "4";

            // send the collection url
            URL collectionUrl = new URL(ancestryUrlString);
            HttpURLConnection connection = (HttpURLConnection) collectionUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // read the response of the collection url
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
                if (responseJsonObject.has("persons"))
                {
                    JSONArray personsJsonArray = responseJsonObject.getJSONArray("persons");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}