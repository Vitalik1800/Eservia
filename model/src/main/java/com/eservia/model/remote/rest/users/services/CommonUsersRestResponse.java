package com.eservia.model.remote.rest.users.services;

/**
 * Created by Alexander on 27.12.2017.
 */

public class CommonUsersRestResponse extends UsersServerResponse {
    @Override
    public boolean isItemValid() {
        return getId() != null;
    }
}
