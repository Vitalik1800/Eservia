package com.eservia.model.remote.socket;


import androidx.annotation.NonNull;

import com.eservia.model.prefs.Settings;
import com.eservia.model.remote.UrlList;
import com.eservia.model.remote.rest.users.AuthRequestInterceptor;
import com.eservia.model.remote.socket.message.BookingEvent;
import com.eservia.model.remote.socket.message.CancelMessageData;
import com.eservia.model.remote.socket.message.ExpireMessageData;
import com.eservia.model.remote.socket.message.LockMessageData;
import com.eservia.model.remote.socket.message.WsResponseType;
import com.eservia.model.remote.socket.request.CancelRequestData;
import com.eservia.model.remote.socket.request.JoinRequestData;
import com.eservia.model.remote.socket.request.LeaveRequestData;
import com.eservia.model.remote.socket.request.LockRequestData;
import com.eservia.model.remote.socket.request.WsEmitType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.socket.client.IO;
import io.socket.client.Socket;

public class WebSocketManager {

    private final PublishSubject<LockMessageData> lockSubject = PublishSubject.create();
    private final PublishSubject<CancelMessageData> cancelSubject = PublishSubject.create();
    private final PublishSubject<ExpireMessageData> expireSubject = PublishSubject.create();

    private Socket socket;

    private String url;

    private boolean connected;

    public WebSocketManager(@NonNull String url) {
        this.url = url;
    }

    public void connect() {
        disconnect();
        try {
            IO.Options opts = new IO.Options();
            opts.path = UrlList.getBroadcastPath();
            socket = IO.socket(url, opts);
            socket.on(Socket.EVENT_CONNECT, args -> {
                //LogUtils.debug(Contract.LOG_TAG, Socket.EVENT_CONNECT);
            });
            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                //LogUtils.debug(Contract.LOG_TAG, Socket.EVENT_CONNECT_ERROR);
            });

            socket.on(Socket.EVENT_DISCONNECT, args -> {
                //LogUtils.debug(Contract.LOG_TAG, Socket.EVENT_DISCONNECT);
            });
            socket.on(WsResponseType.LOCK, args -> {
                JSONObject obj = (JSONObject) args[0];
                processLockMessage(new Gson().fromJson(jsonElement(obj), LockMessageData.class));
            });
            socket.on(WsResponseType.CANCEL, args -> {
                JSONObject obj = (JSONObject) args[0];
                processCancelMessage(new Gson().fromJson(jsonElement(obj), CancelMessageData.class));
            });
            socket.on(WsResponseType.EXPIRE, args -> {
                JSONObject obj = (JSONObject) args[0];
                processExpireMessage(new Gson().fromJson(jsonElement(obj), ExpireMessageData.class));
            });
            socket.connect();
            connected = true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        connected = false;
        if (socket != null) {
            socket.disconnect();
        }
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
        connect();
    }

    public boolean isConnected() {
        return connected;
    }

    public void sendJoinRequest(Long businessId) {
        JoinRequestData data = new JoinRequestData();
        data.setGuid(Settings.getGuid());
        data.setToken(getToken());
        data.setBusinessId(businessId);
        try {
            send(WsEmitType.JOIN, new JSONObject(new Gson().toJson(data)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendLeaveRequest(Long businessId) {
        LeaveRequestData data = new LeaveRequestData();
        data.setGuid(Settings.getGuid());
        data.setToken(getToken());
        data.setBusinessId(businessId);
        try {
            send(WsEmitType.LEAVE, new JSONObject(new Gson().toJson(data)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendLockRequest(BookingEvent booking, Integer duration, Long businessId,
                                Long lockSeconds) {
        LockRequestData data = new LockRequestData();
        data.setGuid(Settings.getGuid());
        data.setToken(getToken());
        data.setBooking(booking);
        data.setServiceDuration(duration);
        data.setBusinessId(businessId);
        data.setLockSeconds(lockSeconds);
        try {
            send(WsEmitType.LOCK, new JSONObject(new Gson().toJson(data)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendCancelRequest(BookingEvent booking, Integer duration, Long businessId) {
        CancelRequestData data = new CancelRequestData();
        data.setGuid(Settings.getGuid());
        data.setToken(getToken());
        data.setBooking(booking);
        data.setServiceDuration(duration);
        data.setBusinessId(businessId);
        try {
            send(WsEmitType.CANCEL, new JSONObject(new Gson().toJson(data)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Observable<LockMessageData> lockMessage() {
        return lockSubject;
    }

    public Observable<CancelMessageData> cancelMessage() {
        return cancelSubject;
    }

    public Observable<ExpireMessageData> expireMessage() {
        return expireSubject;
    }

    private void processLockMessage(LockMessageData data) {
        //LogUtils.debug(Contract.LOG_TAG, "lock data");
        lockSubject.onNext(data);
    }

    private void processCancelMessage(CancelMessageData data) {
        //LogUtils.debug(Contract.LOG_TAG, "cancel data");
        cancelSubject.onNext(data);
    }

    private void processExpireMessage(ExpireMessageData data) {
        //LogUtils.debug(Contract.LOG_TAG, "expire data");
        expireSubject.onNext(data);
    }

    private void send(String event, JSONObject object) {
        if (socket != null) {
            socket.emit(event, object);
        }
    }

    private JsonElement jsonElement(JSONObject obj) {
        return new Gson().fromJson(obj.toString(), JsonElement.class);
    }

    private String getToken() {
        return AuthRequestInterceptor.provideAccessToken();
    }
}
