package org.familysearch.sampleapp.utils;

import org.familysearch.sampleapp.model.Links;
import org.familysearch.sampleapp.model.ancestry.Display;
import org.familysearch.sampleapp.model.ancestry.LinksPersons;
import org.familysearch.sampleapp.model.ancestry.Person;
import org.familysearch.sampleapp.model.ancestry.Persons;
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
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class Utilities
{
    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String KEY_SHARED_PREFERENCES = "shared_preferences";

    public static final String KEY_USER = "user";

    public static final String KEY_PERSONS = "persons";

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

    public static List<Persons> getListOfPersonsFromPersonsJsonObject(JSONObject personsJsonObject) throws JSONException {
        List<Persons> personsList = null;
        if (personsJsonObject.has("persons"))
        {
            JSONArray personsJsonArray = personsJsonObject.getJSONArray("persons");
            personsList = new ArrayList<>();
            for (int i = 0; i < personsJsonArray.length(); i++)
            {
                Persons persons = new Persons();
                Display personsDisplay = new Display();
                LinksPersons linksPersons = new LinksPersons();
                Person person = new Person();

                JSONObject personsObject = personsJsonArray.getJSONObject(i);
                JSONObject personsDisplayObject = personsObject.getJSONObject("display");

                JSONObject personsLinksObject = personsObject.getJSONObject("links");
                JSONObject personPersonObject = personsLinksObject.getJSONObject("person");
                person.setHref(personPersonObject.getString("href"));
                linksPersons.setPerson(person);

                persons.setId(personsObject.getString("id"));

                personsDisplay.setName(personsDisplayObject.getString("name"));
                personsDisplay.setGender(personsDisplayObject.getString("gender"));
                personsDisplay.setLifespan(personsDisplayObject.getString("lifespan"));

                if (personsDisplayObject.has("ascendancyNumber"))
                {
                    personsDisplay.setAscendancyNumber(personsDisplayObject.getString("ascendancyNumber"));
                }
                if (personsDisplayObject.has("descendancyNumber"))
                {
                    personsDisplay.setDescendancyNumber(personsDisplayObject.getString("descendancyNumber"));
                }
                if (personsDisplayObject.has("birthDate"))
                {
                    personsDisplay.setBirthDate(personsDisplayObject.getString("birthDate"));
                }
                if (personsDisplayObject.has("birthPlace"))
                {
                    personsDisplay.setBirthPlace(personsDisplayObject.getString("birthPlace"));
                }
                if (personsDisplayObject.has("deathDate"))
                {
                    personsDisplay.setDeathDate(personsDisplayObject.getString("deathDate"));
                }
                if (personsDisplayObject.has("deathPlace"))
                {
                    personsDisplay.setDeathPlace(personsDisplayObject.getString("deathPlace"));
                }

                persons.setLinks(linksPersons);
                persons.setDisplay(personsDisplay);
                personsList.add(persons);
            }
        }
        return personsList;
    }

}
