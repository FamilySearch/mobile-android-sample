package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class ChildRelationships implements Parcelable {
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

    public ChildRelationships() {
    }

    protected ChildRelationships(Parcel in) {
        this.href = in.readString();
    }

    public static final Parcelable.Creator<ChildRelationships> CREATOR = new Parcelable.Creator<ChildRelationships>() {
        @Override
        public ChildRelationships createFromParcel(Parcel source) {
            return new ChildRelationships(source);
        }

        @Override
        public ChildRelationships[] newArray(int size) {
            return new ChildRelationships[size];
        }
    };
}
