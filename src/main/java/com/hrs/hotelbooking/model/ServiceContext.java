package com.hrs.hotelbooking.model;

import java.util.Hashtable;

public class ServiceContext {
    private Booking booking;
    private Hashtable<String, Object> thirdPartyResponseList;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Hashtable<String, Object> getThirdPartyResponseList() {
        return thirdPartyResponseList;
    }

    public void setThirdPartyResponseList(Hashtable<String, Object> thirdPartyResponseList) {
        this.thirdPartyResponseList = thirdPartyResponseList;
    }
}
