package com.eservia.model.remote.rest.file.services;

public class CommonFileRestResponse extends FileServerResponse {

    @Override
    public boolean isItemValid() {
        return getId() != null;
    }
}
