package com.eservia.model.remote.rest.business.services.contact;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactService {

    @POST("contacts")
    Observable<EserviaFeedbackResponse> sendEserviaContactFeedback(@Body EserviaFeedbackRequest request);
}
