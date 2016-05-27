package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Descendancy implements Parcelable {
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

    public Descendancy() {
    }

    protected Descendancy(Parcel in) {
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Descendancy> CREATOR = new Parcelable.Creator<Descendancy>() {
        @Override
        public Descendancy createFromParcel(Parcel source) {
            return new Descendancy(source);
        }

        @Override
        public Descendancy[] newArray(int size) {
            return new Descendancy[size];
        }
    };
}
