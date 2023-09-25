package com.stampcrush.backend.application.manager.event;

public class StampCreateEvent {

    private final String userPhone;
    private final int stampCount;

    public StampCreateEvent(String userPhone, int stampCount) {
        this.userPhone = userPhone;
        this.stampCount = stampCount;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public int getStampCount() {
        return stampCount;
    }
}
