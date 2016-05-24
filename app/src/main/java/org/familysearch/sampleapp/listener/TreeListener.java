package org.familysearch.sampleapp.listener;

import org.familysearch.sampleapp.model.ancestry.Persons;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public interface TreeListener {

    void onGeneaologySucceeded(List<Persons> personsList);
}
