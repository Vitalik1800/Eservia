package com.eservia.model.prefs;


import androidx.annotation.Nullable;

import com.eservia.model.entity.ProfileUserData;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.prefs.common.PreferencesManager;
import com.eservia.model.remote.rest.users.services.profile.EmailUserData;


public class Profile {

    private static final String KEY_USER_ID = "profileUserId";
    private static final String KEY_CUSTOMER_ID = "profileCustomerId";
    private static final String KEY_USER_FIRST_NAME = "profileUserFirstName";
    private static final String KEY_USER_LAST_NAME = "profileUserLastName";
    private static final String KEY_USER_EMAIL = "profileUserEmail";
    private static final String KEY_PHOTO_URL = "profilePhotoUrl";
    private static final String KEY_BIRTHDAY = "profileBirthDate";
    private static final String KEY_PHONE_NUMBER = "profilePhoneNumber";
    private static final String KEY_ESTABLISHMENT_ID = "profileEstablishmentId";
    private static final String KEY_ROLE_ID = "profileRoleId";
    private static final String KEY_IS_ACTIVE = "profileIsActive";
    private static final String KEY_CARD_NUMBER = "profileCardNumber";
    private static final String KEY_CITY = "profileCity";
    private static final String KEY_FEEDBACK_COUNT = "feedback";
    private static final String KEY_ORDERS_COUNT = "orders";
    private static final String KEY_BONUS_COUNT = "bonus";
    private static final String KEY_GENDER = "sexId";
    private static final String KEY_GOOGLE_ID = "googleId";
    private static final String KEY_FACEBOOK_ID = "facebookId";
    private static final String KEY_IS_BLOCKED = "isBlocked";
    private static final String KEY_IS_EMAIL_CONFIRMED = "isEmailConfirmed";
    private static final String KEY_USER_LAST_LOCATION_LAT = "keyUserLastLocationLat";
    private static final String KEY_USER_LAST_LOCATION_LNG = "keyUserLastLocationLng";

    private Profile() {
    }

    public static void update(EmailUserData emailUserData) {
        if (emailUserData == null) return;
        if (emailUserData.getEmail() != null) {
            Profile.setUserEmail(emailUserData.getEmail());
            Profile.setEmailConfirmed(emailUserData.getEmailConfirmed());
        }
        ContentChangesObservable.send(SyncEvent.PROFILE);
    }

    public static void update(ProfileUserData profile) {
        if (profile == null) return;
        Profile.setUserId(profile.getId());
        Profile.setCustomerId(profile.getCustomerId());
        Profile.setPhotoId(profile.getPhotoId());
        Profile.setUserFirstName(profile.getFirstName());
        Profile.setUserLastName(profile.getLastName());
        Profile.setUserEmail(profile.getEmail());
        Profile.setBirthDate(profile.getBirthday());
        Profile.setUserPhoneNumber(profile.getPhoneNumber());
        Profile.setEstablishmentId(profile.getEstablishmentId());
        Profile.setGender(profile.getGender());
        Profile.setGoogleId(profile.getGoogleId());
        Profile.setFacebookId(profile.getFacebookId());
        Profile.setBlocked(profile.isBlocked());
        Profile.setEmailConfirmed(profile.getEmailConfirmed());
        ContentChangesObservable.send(SyncEvent.PROFILE);
    }

    public static String getGoogleId() {
        return PreferencesManager.userPrefs().getString(KEY_GOOGLE_ID);
    }

    public static void setGoogleId(String googleId) {
        PreferencesManager.userPrefs().putString(KEY_GOOGLE_ID, googleId);
    }

    public static String getFacebookId() {
        return PreferencesManager.userPrefs().getString(KEY_FACEBOOK_ID);
    }

    public static void setFacebookId(String facebookId) {
        PreferencesManager.userPrefs().putString(KEY_FACEBOOK_ID, facebookId);
    }

    public static boolean isBlocke() {
        return PreferencesManager.userPrefs().getBoolean(KEY_IS_BLOCKED);
    }

    public static void setBlocked(boolean isBlocked) {
        PreferencesManager.userPrefs().putBoolean(KEY_IS_BLOCKED, isBlocked);
    }

    public static boolean isEmailConfirmed() {
        return PreferencesManager.userPrefs().getBoolean(KEY_IS_EMAIL_CONFIRMED);
    }

    public static void setEmailConfirmed(boolean isEmailConfirmed) {
        PreferencesManager.userPrefs().putBoolean(KEY_IS_EMAIL_CONFIRMED, isEmailConfirmed);
    }

    public static long getGender() {
        return PreferencesManager.userPrefs().getLong(KEY_GENDER);
    }

    public static void setGender(long genderType) {
        PreferencesManager.userPrefs().putLong(KEY_GENDER, genderType);
    }

    public static int getFeedbacksQuantity() {
        return PreferencesManager.userPrefs().getInt(KEY_FEEDBACK_COUNT);
    }

    public void setFeedbacksQuantity(int ordersCount) {
        PreferencesManager.userPrefs().putInt(KEY_FEEDBACK_COUNT, ordersCount);
    }

    public static int getOrdersQuantity() {
        return PreferencesManager.userPrefs().getInt(KEY_ORDERS_COUNT);
    }

    public void setOrdersQuantity(int ordersCount) {
        PreferencesManager.userPrefs().putInt(KEY_ORDERS_COUNT, ordersCount);
    }

    public static int getBonusQuantity() {
        return PreferencesManager.userPrefs().getInt(KEY_BONUS_COUNT);
    }

    public void setBonusQuantity(int bonusCount) {
        PreferencesManager.userPrefs().putInt(KEY_BONUS_COUNT, bonusCount);
    }

    public static String getCity() {
        return PreferencesManager.userPrefs().getString(KEY_CITY);
    }

    public static void setCity(String city) {
        PreferencesManager.userPrefs().putString(KEY_CITY, city);
    }

    public static String getCardNumber() {
        return PreferencesManager.userPrefs().getString(KEY_CARD_NUMBER);
    }

    public static void setUserCardNumber(String cardNumber) {
        PreferencesManager.userPrefs().putString(KEY_CARD_NUMBER, cardNumber);
    }

    public static String getRoleId() {
        return PreferencesManager.userPrefs().getString(KEY_ROLE_ID);
    }

    public static void setUserRoleId(String roleId) {
        PreferencesManager.userPrefs().putString(KEY_ROLE_ID, roleId);
    }

    public static boolean isActive() {
        return PreferencesManager.userPrefs().getBoolean(KEY_IS_ACTIVE);
    }

    public static void setActive(boolean isActive) {
        PreferencesManager.userPrefs().putBoolean(KEY_IS_ACTIVE, isActive);
    }

    public static String getUserId() {
        return PreferencesManager.userPrefs().getString(KEY_USER_ID);
    }

    public static void setUserId(String id) {
        PreferencesManager.userPrefs().putString(KEY_USER_ID, id);
    }

    public static String getCustomerId() {
        return PreferencesManager.userPrefs().getString(KEY_CUSTOMER_ID);
    }

    public static void setCustomerId(String id) {
        PreferencesManager.userPrefs().putString(KEY_CUSTOMER_ID, id);
    }

    public static String getUserEmail() {
        return PreferencesManager.userPrefs().getString(KEY_USER_EMAIL);
    }

    public static void setUserEmail(String mail) {
        PreferencesManager.userPrefs().putString(KEY_USER_EMAIL, mail);
    }

    public static String getPhotoId() {
        return PreferencesManager.userPrefs().getString(KEY_PHOTO_URL);
    }

    public static void setPhotoId(String id) {
        PreferencesManager.userPrefs().putString(KEY_PHOTO_URL, id);
    }

    public static long getBirthDate() {
        return PreferencesManager.userPrefs().getLong(KEY_BIRTHDAY);
    }

    public static void setBirthDate(long date) {
        PreferencesManager.userPrefs().putLong(KEY_BIRTHDAY, date);
    }

    public static String getUserFirstName() {
        return PreferencesManager.userPrefs().getString(KEY_USER_FIRST_NAME);
    }

    public static void setUserFirstName(String name) {
        PreferencesManager.userPrefs().putString(KEY_USER_FIRST_NAME, name);
    }

    public static String getUserLastName() {
        return PreferencesManager.userPrefs().getString(KEY_USER_LAST_NAME);
    }

    public static void setUserLastName(String name) {
        PreferencesManager.userPrefs().putString(KEY_USER_LAST_NAME, name);
    }

    @Nullable
    public static String getUserPhoneNumber() {
        return PreferencesManager.userPrefs().getString(KEY_PHONE_NUMBER);
    }

    public static void setUserPhoneNumber(String phone) {
        PreferencesManager.userPrefs().putString(KEY_PHONE_NUMBER, phone);
    }

    public static long getEstablishmentId() {
        return PreferencesManager.userPrefs().getLong(KEY_ESTABLISHMENT_ID);
    }

    public static void setEstablishmentId(long establishmentId) {
        PreferencesManager.userPrefs().putLong(KEY_ESTABLISHMENT_ID, establishmentId);
    }

    public static float getUserLastLocationLat() {
        return PreferencesManager.userPrefs().getFloat(KEY_USER_LAST_LOCATION_LAT);
    }

    public static void setUserLastLocationLat(float userLastLocationLat) {
        PreferencesManager.userPrefs().putFloat(KEY_USER_LAST_LOCATION_LAT, userLastLocationLat);
    }

    public static float getUserLastLocationLng() {
        return PreferencesManager.userPrefs().getFloat(KEY_USER_LAST_LOCATION_LNG);
    }

    public static void setUserLastLocationLng(float userLastLocationLng) {
        PreferencesManager.userPrefs().putFloat(KEY_USER_LAST_LOCATION_LNG, userLastLocationLng);
    }

    public static String getFullName() {
        String firstName = getUserFirstName();
        String lastName = getUserLastName();
        return getFullName(firstName, lastName);
    }

    private static String getFullName(String firstName, String lastName) {
        StringBuilder builder = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            builder.append(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }
        return builder.toString();
    }

    public static boolean isUserLocationKnown() {
        return getUserLastLocationLat() != 0.0f && getUserLastLocationLng() != 0.0f;
    }

    public static void logOut() {
        PreferencesManager.userPrefs().clear();
        AccessToken.resetToken();
    }
}
