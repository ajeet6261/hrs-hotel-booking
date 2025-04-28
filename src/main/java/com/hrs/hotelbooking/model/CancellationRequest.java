package com.hrs.hotelbooking.model;

import com.hrs.hotelbooking.enumextension.CancelDetails_Enum;

import java.util.List;

public class CancellationRequest {
    private String bookingId;
    private String userId;
    private String reason;
    private boolean isCommitCall;
    private List<CancellationLine> cancellationLines;
    private CancelDetails_Enum.Refund_Adjustment refundAdjustment;

    private int requestType;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isCommitCall() {
        return isCommitCall;
    }

    public void setCommitCall(boolean commitCall) {
        isCommitCall = commitCall;
    }

    public List<CancellationLine> getCancellationLines() {
        return cancellationLines;
    }

    public void setCancellationLines(List<CancellationLine> cancellationLines) {
        this.cancellationLines = cancellationLines;
    }

    public CancelDetails_Enum.Refund_Adjustment getRefundAdjustment() {
        return refundAdjustment;
    }

    public void setRefundAdjustment(CancelDetails_Enum.Refund_Adjustment refundAdjustment) {
        this.refundAdjustment = refundAdjustment;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}