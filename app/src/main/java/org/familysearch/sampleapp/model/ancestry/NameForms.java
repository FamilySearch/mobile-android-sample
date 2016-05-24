package org.familysearch.sampleapp.model.ancestry;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public class NameForms {

    private String fullText;

    private List<Parts> partsList;

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }
}
