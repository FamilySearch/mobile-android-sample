package org.familysearch.sampleapp.model.ancestry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Flores
 */
public class NameForms implements Parcelable {

    private String fullText;

    private List<Parts> partsList;

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullText);
        dest.writeList(this.partsList);
    }

    public NameForms() {
    }

    protected NameForms(Parcel in) {
        this.fullText = in.readString();
        this.partsList = new ArrayList<Parts>();
        in.readList(this.partsList, Parts.class.getClassLoader());
    }

    public static final Parcelable.Creator<NameForms> CREATOR = new Parcelable.Creator<NameForms>() {
        @Override
        public NameForms createFromParcel(Parcel source) {
            return new NameForms(source);
        }

        @Override
        public NameForms[] newArray(int size) {
            return new NameForms[size];
        }
    };
}
