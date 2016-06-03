package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class LinksPersons implements Parcelable {

    private Person person;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.person, flags);
    }

    public LinksPersons() {
    }

    protected LinksPersons(Parcel in) {
        this.person = in.readParcelable(Person.class.getClassLoader());
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
