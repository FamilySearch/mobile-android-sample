package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Display implements Parcelable {
    private String name;

    private String gender;

    private String lifespan;

    private String ascendancyNumber;

    private String descendancyNumber;

    private String birthDate;

    private String birthPlace;

    private String deathDate;

    private String deathPlace;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getAscendancyNumber() {
        return ascendancyNumber;
    }

    public void setAscendancyNumber(String ascendancyNumber) {
        this.ascendancyNumber = ascendancyNumber;
    }

    public String getDescendancyNumber() {
        return descendancyNumber;
    }

    public void setDescendancyNumber(String descendancyNumber) {
        this.descendancyNumber = descendancyNumber;
    }


    public Display() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeString(this.lifespan);
        dest.writeString(this.ascendancyNumber);
        dest.writeString(this.descendancyNumber);
        dest.writeString(this.birthDate);
        dest.writeString(this.birthPlace);
        dest.writeString(this.deathDate);
        dest.writeString(this.deathPlace);
    }

    protected Display(Parcel in) {
        this.name = in.readString();
        this.gender = in.readString();
        this.lifespan = in.readString();
        this.ascendancyNumber = in.readString();
        this.descendancyNumber = in.readString();
        this.birthDate = in.readString();
        this.birthPlace = in.readString();
        this.deathDate = in.readString();
        this.deathPlace = in.readString();
    }

    public static final Creator<Display> CREATOR = new Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel source) {
            return new Display(source);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[size];
        }
    };
}
