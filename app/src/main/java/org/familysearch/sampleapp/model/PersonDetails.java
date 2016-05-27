package org.familysearch.sampleapp.model;

import org.familysearch.sampleapp.model.ancestry.Persons;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public class PersonDetails {

    private List<Persons> personsList;

    // TODO: relationships, sourceDescriptions, places

    public List<Persons> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Persons> personsList) {
        this.personsList = personsList;
    }
}
