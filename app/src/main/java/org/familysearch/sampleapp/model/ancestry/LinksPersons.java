package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class LinksPersons implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.ancestry, flags);
        dest.writeParcelable(this.person, flags);
        dest.writeParcelable(this.descendancy, flags);
        dest.writeParcelable(this.self, flags);
        dest.writeParcelable(this.childRelationships, flags);
    }

    public LinksPersons() {
    }

    protected LinksPersons(Parcel in) {
        this.ancestry = in.readParcelable(Ancestry.class.getClassLoader());
        this.person = in.readParcelable(Person.class.getClassLoader());
        this.descendancy = in.readParcelable(Descendancy.class.getClassLoader());
        this.self = in.readParcelable(Self.class.getClassLoader());
        this.childRelationships = in.readParcelable(ChildRelationships.class.getClassLoader());
    }

    public static final Parcelable.Creator<LinksPersons> CREATOR = new Parcelable.Creator<LinksPersons>() {
        @Override
        public LinksPersons createFromParcel(Parcel source) {
            return new LinksPersons(source);
        }

        @Override
        public LinksPersons[] newArray(int size) {
            return new LinksPersons[size];
        }
    };
}
