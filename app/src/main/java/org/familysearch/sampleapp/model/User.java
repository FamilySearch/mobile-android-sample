package org.familysearch.sampleapp.model;

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
        dest.writeString(id);
        dest.writeString(contactName);
        dest.writeString(helperAccessPin);
        dest.writeString(givenName);
        dest.writeString(familyName);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(gender);
        dest.writeString(birthDate);
        dest.writeString(phoneNumber);
        dest.writeString(mailingAddress);
        dest.writeString(preferredLanguage);
        dest.writeString(displayName);
        dest.writeString(personId);
        dest.writeString(treeUserId);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        id = in.readString();
        contactName = in.readString();
        helperAccessPin = in.readString();
        givenName = in.readString();
        familyName = in.readString();
        email = in.readString();
        country = in.readString();
        gender = in.readString();
        birthDate = in.readString();
        phoneNumber = in.readString();
        mailingAddress = in.readString();
        preferredLanguage = in.readString();
        displayName = in.readString();
        personId = in.readString();
        treeUserId = in.readString();
    }
}
