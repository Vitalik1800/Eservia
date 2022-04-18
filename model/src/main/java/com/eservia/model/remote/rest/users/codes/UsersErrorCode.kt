package com.eservia.model.remote.rest.users.codes


object UsersErrorCode {

    //  region auth

    const val TOKEN_EXPIRED = 1
    const val AUTHORIZATION_FAILED = 2
    const val INVALID_CREDENTIALS = 3
    const val NO_AUTHORIZATION = 5
    const val UNSUPPORTED_LOGIN_PROVIDER = 6
    const val COULD_NOT_CREATE_USER = 7
    const val EMAIL_NOT_CONFIRMED = 8
    const val COULD_NOT_ADD_EXTERNAL_LOGIN = 9
    const val INVALID_EXTERNAL_LOGIN_DATA = 10
    const val INVALID_OAUTH_TOKEN = 11
    const val REFRESH_TOKEN_IS_INCORRECT = 12
    const val REFRESH_TOKEN_DOES_NOT_EXIST = 13
    const val USER_IS_BLOCKED = 14

    //   endregion

    //   region internal

    const val UNKNOWN_ERROR = 50
    const val DB_EXCEPTION_ERROR = 51
    const val COULD_NOT_DELETE_TEMP_FILES = 52
    const val COULD_NOT_SAVE_PHOTO_TO_TEMP_FOLDER = 53
    const val COULD_NOT_RESIZE_PHOTO_TO_TEMP_FOLDER = 54
    const val COULD_NOT_UPLOAD_PHOTO_TO_S3_SERVICE = 55
    const val COULD_NOT_SAVE_PHOTO = 56
    const val FILE_DOES_NOT_EXIST = 57
    const val MISMATCH_OLD_PASSWORD = 58

    //   endregion

    //   region const validation

    const val INVALID_MODEL = 100
    const val CONFIRM_CODE_DOES_NOT_EXIST = 101
    const val INVALID_CONFIRM_CODE = 102
    const val USER_NOT_FOUND = 103
    const val COULD_NOT_UPDATE_USER = 104
    const val INVALID_OPERATION = 105
    const val EMAIL_ALREADY_TAKEN = 106
    const val INVALID_EMAIL = 107
    const val ENUM_DOES_NOT_HAS_ANY_VALUES = 108
    const val ENUM_SUM_IS_NOT_VALID = 109
    const val INCORRECT_ENUM_VALUE = 110
    const val INVALID_BIRTHDAY = 111
    const val PHONE_ALREADY_TAKEN = 116
    const val EMAIL_ALREADY_CONFIRMED = 117

    //   endregion

    //   region photo

    const val REQUEST_DOES_NOT_CONTAINS_FILES = 200
    const val FILE_SIZE_TOO_LARGE = 201
    const val INVALID_FILE_FORMAT = 202
    const val DUPLICATE_PHOTO_PATH = 203

    //   endregion

}
