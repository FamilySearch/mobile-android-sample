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
    }

    public Display() {
    }

    protected Display(Parcel in) {
        this.name = in.readString();
        this.gender = in.readString();
        this.lifespan = in.readString();
        this.ascendancyNumber = in.readString();
        this.descendancyNumber = in.readString();
    }

    public static final Parcelable.Creator<Display> CREATOR = new Parcelable.Creator<Display>() {
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
