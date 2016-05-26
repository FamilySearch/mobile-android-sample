package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Self implements Parcelable {
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public Self() {
    }

    protected Self(Parcel in) {
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Self> CREATOR = new Parcelable.Creator<Self>() {
        @Override
        public Self createFromParcel(Parcel source) {
            return new Self(source);
        }

        @Override
        public Self[] newArray(int size) {
            return new Self[size];
        }
    };
}
