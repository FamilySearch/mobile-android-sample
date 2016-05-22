package org.familysearch.sampleapp.model.ancestry;

/**
 * @author Eduardo Flores
 */
public class LinksPersons
{
    private Ancestry ancestry;

    private Person person;

    private Descendancy descendancy;

    private Self self;

    private ChildRelationships childRelationships;

    public Ancestry getAncestry() {
        return ancestry;
    }

    public void setAncestry(Ancestry ancestry) {
        this.ancestry = ancestry;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Descendancy getDescendancy() {
        return descendancy;
    }

    public void setDescendancy(Descendancy descendancy) {
        this.descendancy = descendancy;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public ChildRelationships getChildRelationships() {
        return childRelationships;
    }

    public void setChildRelationships(ChildRelationships childRelationships) {
        this.childRelationships = childRelationships;
    }
}
