package org.familysearch.sampleapp.utils;

import org.familysearch.sampleapp.model.Links;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Eduardo Flores
 */
public class Utilities
{
    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String KEY_SHARED_PREFERENCES = "shared_preferences";

    public static Links getUrlsFromCollections()
    {
        final String collectionUrlString = "https://familysearch.org/platform/collection";

        Links links = null;

        try {
            // send the collection url
            URL collectionUrl = new URL(collectionUrlString);
            HttpURLConnection connection = (HttpURLConnection) collectionUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
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
                    // traverse the 'collections' json array
                    JSONArray collectionsArray = responseJsonObject.getJSONArray("collections");

                    for (int i = 0; i < collectionsArray.length(); i++) {
                        JSONObject object = collectionsArray.getJSONObject(i);
                        if (object.has("links")) {
                            links = new Links();
                            JSONObject linksObject = object.getJSONObject("links");
                            JSONObject tokenUrlObject = linksObject.getJSONObject("http://oauth.net/core/2.0/endpoint/token");
                            JSONObject currentUserObject = linksObject.getJSONObject("current-user");
                            JSONObject familyTreeObject = linksObject.getJSONObject("family-tree");

                            links.setTokenUrlString(tokenUrlObject.getString("href"));
                            links.setCurrentUserString(currentUserObject.getString("href"));
                            links.setFamilyTreeUrlString(familyTreeObject.getString("href"));
                        }
                    }
                }

            }
            else
            {
                System.err.println("Error getting the collection urls");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return links;
    }

}
