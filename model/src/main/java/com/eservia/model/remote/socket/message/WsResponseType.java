package com.eservia.model.remote.socket.message;

public class WsResponseType {

    public static final String LOCK = "booking:lock:create";
    public static final String CANCEL = "booking:lock:cancel";
    public static final String EXPIRE = "booking:lock:expired";
}
