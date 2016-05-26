package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Gender implements Parcelable {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
    }

    public Gender() {
    }

    protected Gender(Parcel in) {
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Gender> CREATOR = new Parcelable.Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel source) {
            return new Gender(source);
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };
}
