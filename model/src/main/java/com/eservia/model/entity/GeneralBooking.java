package com.eservia.model.entity;

public abstract class GeneralBooking {

    protected int positionInResponse;

    public int getPositionInResponse() {
        return positionInResponse;
    }

    public void setPositionInResponse(int positionInResponse) {
        this.positionInResponse = positionInResponse;
    }
}
