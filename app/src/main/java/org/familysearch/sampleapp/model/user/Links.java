package org.familysearch.sampleapp.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Links implements Parcelable {
    private Artifacts artifacts;

    public Artifacts getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Artifacts artifacts) {
        this.artifacts = artifacts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.artifacts, flags);
    }

    public Links() {
    }

    protected Links(Parcel in) {
        this.artifacts = in.readParcelable(Artifacts.class.getClassLoader());
    }

    public static final Parcelable.Creator<Links> CREATOR = new Parcelable.Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
}
