package com.hrs.hotelbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private int roomLineNo;  // HotelDetails.lineno
    private String cancellationDate;
    private String refundAmount;
    private String cancellationReason;
    private String cancellationStatus;
    private String cancellationBy;
    private String cancellationRemarks;
    private int refundMode;
}
