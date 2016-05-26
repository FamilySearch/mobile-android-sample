package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Parts implements Parcelable {
    private String type;

    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.value);
    }

    public Parts() {
    }

    protected Parts(Parcel in) {
        this.type = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Parts> CREATOR = new Parcelable.Creator<Parts>() {
        @Override
        public Parts createFromParcel(Parcel source) {
            return new Parts(source);
        }

        @Override
        public Parts[] newArray(int size) {
            return new Parts[size];
        }
    };
}
