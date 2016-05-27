package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class Persons implements Parcelable {

    private String id;

    private LinksPersons links;

    private boolean living;

    private Gender gender;

    private List<Names> namesList;

    private Display display;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinksPersons getLinks() {
        return links;
    }

    public void setLinks(LinksPersons links) {
        this.links = links;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Names> getNamesList() {
        return namesList;
    }

    public void setNamesList(List<Names> namesList) {
        this.namesList = namesList;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.links, flags);
        dest.writeByte(this.living ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.gender, flags);
        dest.writeList(this.namesList);
        dest.writeParcelable(this.display, flags);
    }

    public Persons() {
    }

    protected Persons(Parcel in) {
        this.id = in.readString();
        this.links = in.readParcelable(LinksPersons.class.getClassLoader());
        this.living = in.readByte() != 0;
        this.gender = in.readParcelable(Gender.class.getClassLoader());
        this.namesList = new ArrayList<Names>();
        in.readList(this.namesList, Names.class.getClassLoader());
        this.display = in.readParcelable(Display.class.getClassLoader());
    }

    public static final Parcelable.Creator<Persons> CREATOR = new Parcelable.Creator<Persons>() {
        @Override
        public Persons createFromParcel(Parcel source) {
            return new Persons(source);
        }

        @Override
        public Persons[] newArray(int size) {
            return new Persons[size];
        }
    };
}
