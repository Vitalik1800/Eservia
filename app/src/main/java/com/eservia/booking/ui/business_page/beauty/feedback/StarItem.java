package com.eservia.booking.ui.business_page.beauty.feedback;

public class StarItem {

    private State state;

    public StarItem(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        NORMAL,
        FILLED,
        FILLED_EXPANDED
    }
}
