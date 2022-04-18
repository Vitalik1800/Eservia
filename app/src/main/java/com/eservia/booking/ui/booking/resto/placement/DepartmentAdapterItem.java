package com.eservia.booking.ui.booking.resto.placement;

import com.eservia.model.entity.RestoDepartment;

public class DepartmentAdapterItem {

    public enum State {
        SELECTED,
        UNSELECTED,
        DISABLED
    }

    private RestoDepartment department;

    private State state;

    public DepartmentAdapterItem(RestoDepartment department, State state) {
        this.department = department;
        this.state = state;
    }

    public RestoDepartment getDepartment() {
        return department;
    }

    public void setDepartment(RestoDepartment department) {
        this.department = department;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
