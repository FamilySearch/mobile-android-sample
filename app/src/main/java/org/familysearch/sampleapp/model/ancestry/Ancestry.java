package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Ancestry implements Parcelable {

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

    public Ancestry() {
    }

    protected Ancestry(Parcel in) {
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Ancestry> CREATOR = new Parcelable.Creator<Ancestry>() {
        @Override
        public Ancestry createFromParcel(Parcel source) {
            return new Ancestry(source);
        }

        @Override
        public Ancestry[] newArray(int size) {
            return new Ancestry[size];
        }
    };
}
