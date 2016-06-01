package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.activity.TreeActivity;
import org.familysearch.sampleapp.listener.TreeListener;
import org.familysearch.sampleapp.model.User;
import org.familysearch.sampleapp.model.ancestry.Display;
import org.familysearch.sampleapp.model.ancestry.Links;
import org.familysearch.sampleapp.model.ancestry.LinksPersons;
import org.familysearch.sampleapp.model.ancestry.Person;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class TreeServices extends AsyncTask<String, String, List<Persons>> {

    private Context context;

    private User user;

    private TreeListener treeListener;

    private TreeActivity activity;

    private ProgressDialog progressDialog;

    public TreeServices(Context context, User user)
    {
        this.context = context;
        this.user = user;
        activity = (TreeActivity) context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(context.getString(R.string.tree_downloading_geneaology));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void setOnTreeListener(TreeListener treeListener)
    {
        this.treeListener = treeListener;
    };

    @Override
    protected List<Persons> doInBackground(String... params) {

        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        String genericFamilyTreeUrlString = Utilities.getUrlsFromCollections().getFamilyTreeUrlString();

        return getAncestryTree(getAncestryQueryUrlAsString(genericFamilyTreeUrlString), user.getPersonId(), accessToken);
    }

    private String getAncestryQueryUrlAsString(String genericTreeUrlString) {
        String ancestryQueryString = null;

        try {
            // send the tree url
            URL genericTreeUrl = new URL(genericTreeUrlString);
            HttpURLConnection connection = (HttpURLConnection) genericTreeUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // read the response of the tree url
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

    private List<Persons> getAncestryTree(String ancestryUrlString, String userPersonId, String accessToken)
    {
        try {
            ancestryUrlString = ancestryUrlString + "?" + "person=" + userPersonId;
            ancestryUrlString = ancestryUrlString + "&" + "generations=" + "4";

            // send the ancestry url
            URL ancestryUrl = new URL(ancestryUrlString);
            HttpURLConnection connection = (HttpURLConnection) ancestryUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // read the response of the ancestry url
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
                return Utilities.getListOfPersonsFromPersonsJsonObject(responseJsonObject);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Persons> personsList) {
        super.onPostExecute(personsList);

        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        treeListener.onGeneaologySucceeded(personsList);
    }
}