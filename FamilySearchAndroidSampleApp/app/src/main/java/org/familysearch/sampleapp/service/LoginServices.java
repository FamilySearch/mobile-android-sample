package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.AppKeys;
import org.familysearch.sampleapp.model.Token;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Eduardo Flores
 */
public class LoginServices extends AsyncTask<String, String, String> {

    private final String collectionUrlString = "https://familysearch.org/platform/collection";

    private String tokenUrl;

    private String currentUserUrl;

    @Override
    protected String doInBackground(String... params) {

        // the params we expect are username and password, in that order
        String username = params[0];
        String password = params[1];

        // begin by getting the token url
        // this network call is a GET call without authentication
        getUrlsFromCollections();

        System.out.println("tokenUrl = " + tokenUrl);
        System.out.println("currentUserUrl = " + currentUserUrl);

        // call the token url with username, password and client_id/api_key to get a token
        Token token = getToken(tokenUrl, username, password, AppKeys.API_KEY);

        System.out.println("token = " + token);

        return null;
    }

    private void getUrlsFromCollections() {
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

                // traverse the 'collections' json array
                JSONArray collectionsArray = responseJsonObject.getJSONArray("collections");
                for (int i = 0; i < collectionsArray.length(); i++) {
                    JSONObject object = collectionsArray.getJSONObject(i);
                    if (object.has("links")) {
                        JSONObject links = object.getJSONObject("links");
                        JSONObject tokenUrlObject = links.getJSONObject("http://oauth.net/core/2.0/endpoint/token");
                        tokenUrl = tokenUrlObject.getString("href");

                        JSONObject currentUserObject = links.getJSONObject("current-user");
                        currentUserUrl = currentUserObject.getString("href");
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
    }

    private Token getToken(String tokenUrlAsString, String username, String password, String client_id)
    {
        Token token = null;
        String grant_type = "password";

        String params = "username=" + username +
                "&password=" + password +
                "&grant_type=" + grant_type +
                "&client_id=" + client_id;

        try
        {
            URL url = new URL(tokenUrlAsString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            //add request header
            connection.setRequestMethod("POST");

            // Send post request
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(params);
            dataOutputStream.flush();
            dataOutputStream.close();

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

                // assign the received values to a new Token object
                token = new Token();
                token.setToken(responseJsonObject.getString("token"));
                token.setAccess_token(responseJsonObject.getString("access_token"));
                token.setToken_type(responseJsonObject.getString("token_type"));
                return token;
            }
            else
            {
                // invalid username and password returns an error 400 Bad Request
                System.err.println("Error getting the token. Check username and password");
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

        return token;
    }
}
