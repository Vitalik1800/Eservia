package com.eservia.model.remote.rest.file.services.photo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotoService {

    @Multipart
    @POST("Photo/FormData")
    Observable<UploadPhotoResponse> uploadFile(@Part MultipartBody.Part photo);
}
