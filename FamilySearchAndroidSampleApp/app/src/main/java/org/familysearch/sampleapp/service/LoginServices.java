package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.AppKeys;
import org.familysearch.sampleapp.LoginActivity;
import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.LoginListener;
import org.familysearch.sampleapp.model.Token;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
public class LoginServices extends AsyncTask<String, String, Token> {

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
    protected Token doInBackground(String... params) {

        // the params we expect are username and password, in that order
        String username = params[0];
        String password = params[1];

        // begin by getting the token url
        // call the token url with username, password and client_id/api_key to get a token
        return getToken(Utilities.getUrlsFromCollections().getTokenUrlString(), username, password, AppKeys.API_KEY);
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

                // assign the received values to a new Token object
                token = new Token();
                token.setToken(responseJsonObject.getString("token"));
                token.setAccess_token(responseJsonObject.getString("access_token"));
                token.setToken_type(responseJsonObject.getString("token_type"));
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

    @Override
    protected void onPostExecute(Token token) {
        super.onPostExecute(token);

        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        loginListener.onLoginSucceeded(token);
    }
}
