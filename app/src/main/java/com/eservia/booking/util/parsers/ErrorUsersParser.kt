package com.eservia.booking.util.parsers

import androidx.annotation.StringRes
import com.eservia.booking.R
import com.eservia.model.remote.rest.users.codes.UsersErrorCode

object ErrorUsersParser {

    @JvmStatic
    @StringRes
    fun parseErrorCode(errorCode: Int): Int {

        return when (errorCode) {
            UsersErrorCode.AUTHORIZATION_FAILED ->
                R.string.authorization_failed
            UsersErrorCode.INVALID_CREDENTIALS ->
                R.string.invalid_credentials
            UsersErrorCode.NO_AUTHORIZATION,
            UsersErrorCode.TOKEN_EXPIRED ->
                R.string.invalid_authorization
            UsersErrorCode.COULD_NOT_CREATE_USER ->
                R.string.could_not_create_user
            UsersErrorCode.EMAIL_NOT_CONFIRMED ->
                R.string.email_not_confirmed
            UsersErrorCode.UNSUPPORTED_LOGIN_PROVIDER,
            UsersErrorCode.COULD_NOT_ADD_EXTERNAL_LOGIN,
            UsersErrorCode.INVALID_EXTERNAL_LOGIN_DATA,
            UsersErrorCode.INVALID_OAUTH_TOKEN,
            UsersErrorCode.REFRESH_TOKEN_IS_INCORRECT ->
                R.string.invalid_authorization
            UsersErrorCode.REFRESH_TOKEN_DOES_NOT_EXIST ->
                R.string.invalid_authorization
            UsersErrorCode.USER_IS_BLOCKED ->
                R.string.user_blocked

        //region internal
            UsersErrorCode.UNKNOWN_ERROR ->
                R.string.unknown_error
            UsersErrorCode.DB_EXCEPTION_ERROR ->
                R.string.invalid_model
            UsersErrorCode.COULD_NOT_DELETE_TEMP_FILES ->
                R.string.could_not_delete_temp_files
            UsersErrorCode.COULD_NOT_SAVE_PHOTO_TO_TEMP_FOLDER ->
                R.string.could_not_save_photo_to_temp_folder
            UsersErrorCode.COULD_NOT_RESIZE_PHOTO_TO_TEMP_FOLDER ->
                R.string.could_not_resize_photo_to_temp_folder
            UsersErrorCode.COULD_NOT_UPLOAD_PHOTO_TO_S3_SERVICE ->
                R.string.could_not_upload_photo_to_s3_service
            UsersErrorCode.COULD_NOT_SAVE_PHOTO ->
                R.string.could_not_save_photo
            UsersErrorCode.FILE_DOES_NOT_EXIST ->
                R.string.file_does_not_exist
            UsersErrorCode.MISMATCH_OLD_PASSWORD ->
                R.string.invalid_old_password
        //endregion

        //    region user
            UsersErrorCode.INVALID_MODEL,
            UsersErrorCode.INVALID_OPERATION->
                R.string.invalid_model
            UsersErrorCode.CONFIRM_CODE_DOES_NOT_EXIST ->
                R.string.confirm_code_does_not_exist
            UsersErrorCode.INVALID_CONFIRM_CODE ->
                R.string.invalid_confirm_code
            UsersErrorCode.USER_NOT_FOUND ->
                R.string.user_does_not_exist
            UsersErrorCode.COULD_NOT_UPDATE_USER ->
                R.string.could_not_update_user
            UsersErrorCode.EMAIL_ALREADY_TAKEN ->
                R.string.email_already_taken
            UsersErrorCode.INVALID_EMAIL ->
                R.string.invalid_email
            UsersErrorCode.ENUM_DOES_NOT_HAS_ANY_VALUES ->
                R.string.unknown_error
            UsersErrorCode.ENUM_SUM_IS_NOT_VALID ->
                R.string.unknown_error
            UsersErrorCode.INCORRECT_ENUM_VALUE ->
                R.string.unknown_error
            UsersErrorCode.INVALID_BIRTHDAY ->
                R.string.invalid_birthday
            UsersErrorCode.PHONE_ALREADY_TAKEN ->
                R.string.phone_already_taken
            UsersErrorCode.EMAIL_ALREADY_CONFIRMED ->
                R.string.email_already_confirmed
        //endregion

        //    region photo
            UsersErrorCode.REQUEST_DOES_NOT_CONTAINS_FILES ->
                R.string.request_does_not_contains_files
            UsersErrorCode.FILE_SIZE_TOO_LARGE ->
                R.string.unknown_error
            UsersErrorCode.INVALID_FILE_FORMAT ->
                R.string.invalid_file_format
            UsersErrorCode.DUPLICATE_PHOTO_PATH ->
                R.string.unknown_error
        //    endregion

            else ->
                R.string.unknown_error
        }
    }
}
