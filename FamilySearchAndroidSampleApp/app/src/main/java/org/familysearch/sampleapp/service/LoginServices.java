package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.AppKeys;
import org.familysearch.sampleapp.activity.LoginActivity;
import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.LoginListener;
import org.familysearch.sampleapp.model.User;
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
public class LoginServices extends AsyncTask<String, String, User> {

    private Context context;

    private LoginActivity activity;

    private LoginListener loginListener;

    private ProgressDialog progressDialog;

    public LoginServices(Context context)
    {
        this.context = context;
        activity = (LoginActivity) context;
    }

    public void setOnLoginListener(LoginListener loginListener)
    {
        this.loginListener = loginListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(context.getString(R.string.login_progress_message));
        progressDialog.show();
    }

    @Override
    protected User doInBackground(String... params) {

        // the params we expect are username and password, in that order
        String username = params[0];
        String password = params[1];

        // begin by getting the token url
        // call the token url with username, password and client_id/api_key to get a token
        String token = getToken(Utilities.getUrlsFromCollections().getTokenUrlString(), username, password, AppKeys.API_KEY);

        // with the token, make another call the get the current user data
        User user = getCurrentUserData(Utilities.getUrlsFromCollections().getCurrentUserString(), token);

        return user;
    }

    private String getToken(String tokenUrlAsString, String username, String password, String client_id)
    {
        String token = null;

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

            // invalid username and password returns an error 400 Bad Request
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

                // assign the received values to the token string, and save the token to shared preferences for later use
                token = responseJsonObject.getString("access_token");
                SharedPreferences sharedPreferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Utilities.KEY_ACCESS_TOKEN, token);
                editor.commit();
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

    private User getCurrentUserData(String urlString, String accessToken)
    {
        User user = null;
        try {
            // send the collection url
            URL collectionUrl = new URL(urlString);
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

                if (responseJsonObject.has("users"))
                {
                    // traverse the 'users' json array
                    JSONArray usersArray = responseJsonObject.getJSONArray("users");

                    // There should be only 1 user
                    JSONObject userJsonObject = usersArray.getJSONObject(0);
                    user = new User();
                    user.setId(userJsonObject.getString("id"));
                    user.setContactName(userJsonObject.getString("contactName"));
                    user.setHelperAccessPin(userJsonObject.getString("helperAccessPin"));
                    user.setGivenName(userJsonObject.getString("givenName"));
                    user.setFamilyName(userJsonObject.getString("familyName"));
                    user.setEmail(userJsonObject.getString("email"));
                    user.setCountry(userJsonObject.getString("country"));
                    user.setGender(userJsonObject.getString("gender"));
                    user.setBirthDate(userJsonObject.getString("birthDate"));
                    user.setPhoneNumber(userJsonObject.getString("phoneNumber"));
                    user.setMailingAddress(userJsonObject.getString("mailingAddress"));
                    user.setPreferredLanguage(userJsonObject.getString("preferredLanguage"));
                    user.setDisplayName(userJsonObject.getString("displayName"));
                    user.setPersonId(userJsonObject.getString("personId"));
                    user.setTreeUserId(userJsonObject.getString("treeUserId"));
                }
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
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        loginListener.onLoginSucceeded(user);
    }
}
