package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class Names implements Parcelable {

    private NameForms nameForms;

    public NameForms getNameForms() {
        return nameForms;
    }

    public void setNameForms(NameForms nameForms) {
        this.nameForms = nameForms;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.nameForms, flags);
    }

    public Names() {
    }

    protected Names(Parcel in) {
        this.nameForms = in.readParcelable(NameForms.class.getClassLoader());
    }

    public static final Parcelable.Creator<Names> CREATOR = new Parcelable.Creator<Names>() {
        @Override
        public Names createFromParcel(Parcel source) {
            return new Names(source);
        }

        @Override
        public Names[] newArray(int size) {
            return new Names[size];
        }
    };
}
