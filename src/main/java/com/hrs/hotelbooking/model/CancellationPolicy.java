package com.hrs.hotelbooking.model;

public class CancellationPolicy {
    private String policyId;
    private String description;
    private int hoursBeforeCheckIn;
    private double cancellationCharge;
    private boolean isRefundable;

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHoursBeforeCheckIn() {
        return hoursBeforeCheckIn;
    }

    public void setHoursBeforeCheckIn(int hoursBeforeCheckIn) {
        this.hoursBeforeCheckIn = hoursBeforeCheckIn;
    }

    public double getCancellationCharge() {
        return cancellationCharge;
    }

    public void setCancellationCharge(double cancellationCharge) {
        this.cancellationCharge = cancellationCharge;
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
    }
} 