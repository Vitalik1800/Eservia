package com.eservia.booking.ui.contacts;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ContactUtil;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.contact.EserviaFeedbackRequest;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ContactsPresenter extends BasePresenter<ContactsView> {

    @Inject
    RestManager mRestManager;

    private Disposable mContactsDisposable;

    public ContactsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    void onSendFeedback(String theme, String message) {
        if (paginationInProgress(mContactsDisposable)) {
            return;
        }
        sendFeedback(Profile.getUserFirstName(), Profile.getUserEmail(),
                Profile.getUserPhoneNumber(), theme, message);
    }

    private void sendFeedback(String name, String email, String phone, String theme, String message) {
        getViewState().showProgress();

        if (name == null || name.isEmpty()) {
            name = ContactUtil.DEFAULT_NAME;
        }
        if (email == null || email.isEmpty()) {
            email = ContactUtil.DEFAULT_EMAIL;
        }
        if (phone == null || phone.isEmpty()) {
            phone = ContactUtil.DEFAULT_PHONE;
        }
        if (theme == null || theme.isEmpty()) {
            theme = ContactUtil.DEFAULT_SUBJECT;
        }
        if (message == null || message.isEmpty()) {
            message = theme;
        }
        EserviaFeedbackRequest request = new EserviaFeedbackRequest();
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setSubject(theme);
        request.setMessage(message);

        mContactsDisposable = mRestManager
                .sendEserviaContactFeedback(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onFeedbackSendSuccess(), this::onFeedbackSendFailed);
        addSubscription(mContactsDisposable);
    }

    private void onFeedbackSendSuccess() {
        getViewState().hideProgress();
        getViewState().onFeedbackSendSuccess();
        getViewState().closeActivity();
        mContactsDisposable = null;
    }

    private void onFeedbackSendFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onFeedbackSendFailed(throwable);
        mContactsDisposable = null;
    }
}
