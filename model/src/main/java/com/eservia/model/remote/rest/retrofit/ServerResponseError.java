package com.eservia.model.remote.rest.retrofit;


import com.eservia.model.remote.rest.response.ServerResponse;

public class ServerResponseError extends RuntimeException {

    private ServerResponse response;

    public ServerResponseError(ServerResponse response) {
        this.response = response;
    }

    public ServerResponse getResponse() {
        return response;
    }

    public void setResponse(ServerResponse response) {
        this.response = response;
    }
}
