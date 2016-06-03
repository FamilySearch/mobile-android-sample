package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.activity.MemoriesActivity;
import org.familysearch.sampleapp.listener.MemoriesListener;
import org.familysearch.sampleapp.model.memory.ImageThumbnail;
import org.familysearch.sampleapp.model.memory.Links;
import org.familysearch.sampleapp.model.memory.Memories;
import org.familysearch.sampleapp.model.memory.SourceDescriptions;
import org.familysearch.sampleapp.model.user.User;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class MemoryServices extends AsyncTask<String, String, List<Bitmap>>
{
    private Context context;

    private User user;

    private MemoriesActivity activity;

    private ProgressDialog progressDialog;

    private MemoriesListener listener;

    public MemoryServices(Context context, User user)
    {
        this.context = context;
        this.user = user;
        activity = (MemoriesActivity) context;
    }

    public void setOnMemoriesListener(MemoriesListener listener)
    {
        this.listener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(context.getString(R.string.memories_downloading_memories));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<Bitmap> doInBackground(String... params) {

        // get the access token
        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        // download and return a list of jpeg images from memories
        return getMemoriesImagesForUser(user, accessToken);
    }

    private List<Bitmap> getMemoriesImagesForUser(User user, String accessToken)
    {
        List<Bitmap> bitmapList = null;
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

                if (responseJsonObject.has("sourceDescriptions"))
                {
                    JSONArray sourceDescriptionJsonArray = responseJsonObject.getJSONArray("sourceDescriptions");
                    bitmapList = new ArrayList<>();

                    for (int i = 0; i < sourceDescriptionJsonArray.length(); i++)
                    {
                        JSONObject sourceDescriptionsJsonObject = sourceDescriptionJsonArray.getJSONObject(i);

                        if (sourceDescriptionsJsonObject.has("mediaType"))
                        {
                            // only download memories that are jpeg images
                            if (sourceDescriptionsJsonObject.getString("mediaType").equalsIgnoreCase("image/jpeg"))
                            {
                                // get image thumbnail link url
                                if (sourceDescriptionsJsonObject.has("links"))
                                {
                                    JSONObject linksJsonObject = sourceDescriptionsJsonObject.getJSONObject("links");

                                    if (linksJsonObject.has("image-thumbnail"))
                                    {
                                        JSONObject imageThumbnailJsonObject = linksJsonObject.getJSONObject("image-thumbnail");

                                        // download bitmap object
                                        // send the image url from href
                                        URL imageUrl = new URL(imageThumbnailJsonObject.getString("href"));
                                        connection = (HttpURLConnection) imageUrl.openConnection();
                                        connection.setRequestMethod("GET");

                                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                                        {
                                            inputStream = connection.getInputStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                            bitmapList.add(bitmap);
                                        }
                                    }
                                }
                            }
                        }
                    }
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
        return bitmapList;
    }

    @Override
    protected void onPostExecute(List<Bitmap> memories) {
        super.onPostExecute(memories);

        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        listener.onMemoriesBitmapSucceeded(memories);
    }
}
