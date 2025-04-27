package com.hrs.hotelbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "cancellation_details")
@IdClass(CancellationDetailsId.class)
public class CancellationDetails {
    @Id
    private String bookingId;
    @Id
    private String cancellationId;
    @Id
    private int lineNo;
    private int roomLineNo;
    private Date cancellationDate;
    private double refundAmount;
    private String cancellationReason;
    private String cancellationStatus;
    private String cancellationBy;
    private String cancellationRemarks;
    private int refundMode;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCancellationId() {
        return cancellationId;
    }

    public void setCancellationId(String cancellationId) {
        this.cancellationId = cancellationId;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public int getRoomLineNo() {
        return roomLineNo;
    }

    public void setRoomLineNo(int roomLineNo) {
        this.roomLineNo = roomLineNo;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus(String cancellationStatus) {
        this.cancellationStatus = cancellationStatus;
    }

    public String getCancellationBy() {
        return cancellationBy;
    }

    public void setCancellationBy(String cancellationBy) {
        this.cancellationBy = cancellationBy;
    }

    public String getCancellationRemarks() {
        return cancellationRemarks;
    }

    public void setCancellationRemarks(String cancellationRemarks) {
        this.cancellationRemarks = cancellationRemarks;
    }

    public int getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(int refundMode) {
        this.refundMode = refundMode;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
