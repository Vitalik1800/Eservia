package com.eservia.model.remote.rest.users.services.auth;

import com.eservia.model.entity.Validator;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 26.12.2017.
 */

public class SignUpData implements Validator {

    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("emailConfirmed")
    private Boolean emailConfirmed;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("photoPath")
    private String photoPath;
    @SerializedName("sexId")
    private Integer sexId;
    @SerializedName("birthday")
    private Long birthday;
    @SerializedName("isBlocked")
    private Boolean isBlocked;
    @SerializedName("googleId")
    private String googleId;
    @SerializedName("facebookId")
    private String facebookId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getSexId() {
        return sexId;
    }

    public void setSexId(Integer sexId) {
        this.sexId = sexId;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @Override
    public boolean isItemValid() {
        return id != null && firstName != null;
    }
}
