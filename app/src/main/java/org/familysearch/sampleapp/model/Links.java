package org.familysearch.sampleapp.model;

/**
 * @author Eduardo Flores
 */
public class Links
{
    private String tokenUrlString;

    private String currentUserString;

    private String familyTreeUrlString;

    public String getTokenUrlString() {
        return tokenUrlString;
    }

    public void setTokenUrlString(String tokenUrlString) {
        this.tokenUrlString = tokenUrlString;
    }

    public String getCurrentUserString() {
        return currentUserString;
    }

    public void setCurrentUserString(String currentUserString) {
        this.currentUserString = currentUserString;
    }

    public String getFamilyTreeUrlString() {
        return familyTreeUrlString;
    }

    public void setFamilyTreeUrlString(String familyTreeUrlString) {
        this.familyTreeUrlString = familyTreeUrlString;
    }
}
