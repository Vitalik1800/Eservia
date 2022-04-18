package com.eservia.booking.ui.profile;

public class UserInfo {

    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String photoPath;
    private Long sexId;
    private Long birthday;
    private boolean isEmailConfirmed;
    private boolean isGoogleAttached;
    private boolean isFacebookAttached;

    public boolean isEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public boolean isGoogleAttached() {
        return isGoogleAttached;
    }

    public void setGoogleAttached(boolean googleAttached) {
        isGoogleAttached = googleAttached;
    }

    public boolean isFacebookAttached() {
        return isFacebookAttached;
    }

    public void setFacebookAttached(boolean facebookAttached) {
        isFacebookAttached = facebookAttached;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Long getSexId() {
        return sexId;
    }

    public void setSexId(Long sexId) {
        this.sexId = sexId;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }
}
