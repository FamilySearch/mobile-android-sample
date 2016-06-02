package org.familysearch.sampleapp.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Artifacts implements Parcelable {

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

    public Artifacts() {
    }

    protected Artifacts(Parcel in) {
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Artifacts> CREATOR = new Parcelable.Creator<Artifacts>() {
        @Override
        public Artifacts createFromParcel(Parcel source) {
            return new Artifacts(source);
        }

        @Override
        public Artifacts[] newArray(int size) {
            return new Artifacts[size];
        }
    };
}
