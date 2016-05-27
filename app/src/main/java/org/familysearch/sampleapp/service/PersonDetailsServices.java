package org.familysearch.sampleapp.service;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.model.PersonDetails;
import org.familysearch.sampleapp.model.ancestry.Person;
import org.familysearch.sampleapp.model.ancestry.Persons;
import org.familysearch.sampleapp.utils.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class PersonDetailsServices extends AsyncTask <String, String, PersonDetails>{

    private Context context;

    private TextView personBirth;

    private TextView personDeath;

    public PersonDetailsServices(Context context, TextView personBirth, TextView personDeath)
    {
        this.context = context;
        this.personBirth = personBirth;
        this.personDeath = personDeath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        personBirth.setText(context.getString(R.string.person_details_birthdate_downloading));
        personDeath.setText(context.getString(R.string.person_details_deathdate_downloading));
    }

    @Override
    protected PersonDetails doInBackground(String... params) {

        // send url as first param
        return downloadPersonDetailsData(params[0]);
    }

    private PersonDetails downloadPersonDetailsData(String personUrlString)
    {
        SharedPreferences preferences = context.getSharedPreferences(Utilities.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String accessToken = preferences.getString(Utilities.KEY_ACCESS_TOKEN, null);

        PersonDetails personDetails = null;
        try {
            // send the ancestry url
            URL ancestryUrl = new URL(personUrlString);
            HttpURLConnection connection = (HttpURLConnection) ancestryUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                personDetails = new PersonDetails();

                // read the response of the person details url
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
                List<Persons> personsList = Utilities.getListOfPersonsFromPersonsJsonObject(responseJsonObject);
                personDetails.setPersonsList(personsList);
            }
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return personDetails;
    }

    @Override
    protected void onPostExecute(PersonDetails personDetails) {
        super.onPostExecute(personDetails);

        if (personDetails != null)
        {
            // there should only be 1 persons element
            personBirth.setText(String.format("%s %s",
                    context.getString(R.string.person_details_birthdate),
                    personDetails.getPersonsList().get(0).getDisplay().getBirthDate()));

            personDeath.setText(String.format("%s %s",
                    context.getString(R.string.person_details_deathdate),
                    personDetails.getPersonsList().get(0).getDisplay().getDeathDate()));
        }
    }
}
