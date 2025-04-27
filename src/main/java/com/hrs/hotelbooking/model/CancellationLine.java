package com.hrs.hotelbooking.model;

public class CancellationLine {
    private int roomLineNo;
    private double refundAmount;

    public int getRoomLineNo() {
        return roomLineNo;
    }

    public void setRoomLineNo(int roomLineNo) {
        this.roomLineNo = roomLineNo;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
