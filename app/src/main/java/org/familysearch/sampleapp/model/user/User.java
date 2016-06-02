package org.familysearch.sampleapp.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Eduardo Flores
 */
public class User implements Parcelable
{
    private String id;

    private String contactName;

    private String helperAccessPin;

    private String givenName;

    private String familyName;

    private String email;

    private String country;

    private String gender;

    private String birthDate;

    private String phoneNumber;

    private String mailingAddress;

    private String preferredLanguage;

    private String displayName;

    private String personId;

    private String treeUserId;

    private Links links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getHelperAccessPin() {
        return helperAccessPin;
    }

    public void setHelperAccessPin(String helperAccessPin) {
        this.helperAccessPin = helperAccessPin;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getTreeUserId() {
        return treeUserId;
    }

    public void setTreeUserId(String treeUserId) {
        this.treeUserId = treeUserId;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public User()
    {
        // empty constructor
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.contactName);
        dest.writeString(this.helperAccessPin);
        dest.writeString(this.givenName);
        dest.writeString(this.familyName);
        dest.writeString(this.email);
        dest.writeString(this.country);
        dest.writeString(this.gender);
        dest.writeString(this.birthDate);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.mailingAddress);
        dest.writeString(this.preferredLanguage);
        dest.writeString(this.displayName);
        dest.writeString(this.personId);
        dest.writeString(this.treeUserId);
        dest.writeParcelable(this.links, flags);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.contactName = in.readString();
        this.helperAccessPin = in.readString();
        this.givenName = in.readString();
        this.familyName = in.readString();
        this.email = in.readString();
        this.country = in.readString();
        this.gender = in.readString();
        this.birthDate = in.readString();
        this.phoneNumber = in.readString();
        this.mailingAddress = in.readString();
        this.preferredLanguage = in.readString();
        this.displayName = in.readString();
        this.personId = in.readString();
        this.treeUserId = in.readString();
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
