# Hotel Booking Service Implementation

## API Documentation

### 1. Create Booking
```bash
curl -X POST http://localhost:8080/api/bookings/createBooking \
-H "Content-Type: application/json" \
-d '{
    "userId": "USER123",
    "hotelCode": "HOTEL001",
    "roomRequest": [
        {
            "roomType": "DELUXE",
            "noOfRooms": 2,
            "noOfNights": 3,
            "baseAmount": 5000.00,
            "igstAmount": 900.00,
            "cgstAmount": 450.00,
            "sgstAmount": 450.00,
            "taxAmount": 1800.00,
            "sellingPrice": 6800.00,
            "discountAmount": 0.00
        }
    ]
}'
```
Response:
```json
{
    "status": true,
    "message": "Booking created successfully",
    "bookingId": "BK123456",
    "userId": "USER123"
}
```

### 2. Get Booking
```bash
curl -X GET http://localhost:8080/api/bookings/getBooking/BK123456
```
Response:
```json
{
    "bookingId": "BK123456",
    "hotelDetails": [
        {
            "bookingId": "BK123456",
            "lineNo": 1000,
            "roomLineNo": 1000,
            "hotelCode": "HOTEL001",
            "roomType": "DELUXE",
            "noOfRooms": 2,
            "noOfNights": 3,
            "baseAmount": 5000.00,
            "igstAmount": 900.00,
            "cgstAmount": 450.00,
            "sgstAmount": 450.00,
            "taxAmount": 1800.00,
            "sellingPrice": 6800.00,
            "discountAmount": 0.00,
            "bookingStatus": "CONFIRMED"
        }
    ],
    "cancellationPolicies": [
        {
            "policyId": "POL001",
            "refundable": true,
            "cancellationCharge": 1000.00,
            "validFrom": "2024-01-01T00:00:00",
            "validTo": "2024-12-31T23:59:59"
        }
    ]
}
```

### 3. Update Booking
```bash
curl -X PUT http://localhost:8080/api/bookings/updateBooking \
-H "Content-Type: application/json" \
-d '{
    "bookingId": "BK123456",
    "hotelDetails": [
        {
            "bookingId": "BK123456",
            "lineNo": 1000,
            "roomLineNo": 1000,
            "hotelCode": "HOTEL001",
            "roomType": "DELUXE",
            "noOfRooms": 3,
            "noOfNights": 3,
            "baseAmount": 7500.00,
            "igstAmount": 1350.00,
            "cgstAmount": 675.00,
            "sgstAmount": 675.00,
            "taxAmount": 2700.00,
            "sellingPrice": 10200.00,
            "discountAmount": 0.00,
            "bookingStatus": "CONFIRMED"
        }
    ]
}'
```
Response:
```json
{
    "status": true,
    "message": "Booking updated successfully"
}
```

### 4. Cancel Booking
```bash
curl -X POST http://localhost:8080/api/bookings/cancelBooking \
-H "Content-Type: application/json" \
-d '{
    "bookingId": "BK123456",
    "userId": "USER123",
    "reason": "Change of plans",
    "cancellationLines": [
        {
            "roomLineNo": 1000
        }
    ],
    "refundAdjustment": "CREDIT"
}'
```
Response:
```json
{
    "status": true,
    "message": "Booking cancelled successfully"
}
```

### 5. Get All Bookings
```bash
curl -X GET http://localhost:8080/api/bookings/getAllBookings/USER123
```
Response:
```json
[
    {
        "bookingId": "BK123456",
        "userId": "USER123",
        "createdAt": "2024-03-15T10:30:00",
        "bookingStatus": "CANCELLED"
    },
    {
        "bookingId": "BK123457",
        "userId": "USER123",
        "createdAt": "2024-03-16T14:20:00",
        "bookingStatus": "CONFIRMED"
    }
]
```

## Database Tables

### CancellationDetails Table
```sql
CREATE TABLE cancellation_details (
    booking_id VARCHAR(50),
    cancellation_id VARCHAR(11),
    line_no INT,
    room_line_no INT,
    cancellation_date DATE,
    refund_amount DECIMAL(10,2),
    cancellation_reason VARCHAR(255),
    cancellation_status VARCHAR(20),
    cancellation_by VARCHAR(50),
    cancellation_remarks VARCHAR(255),
    refund_mode INT,
    PRIMARY KEY (booking_id, cancellation_id, line_no)
);
```

### Description
- `booking_id`: Reference to the booking being cancelled
- `cancellation_id`: Unique identifier for the cancellation (format: CAN + 8 random uppercase chars)
- `line_no`: Line number for the cancellation (starts at 1000, increments by 1000)
- `room_line_no`: Reference to the hotel details line number
- `cancellation_date`: Date and time of cancellation
- `refund_amount`: Amount to be refunded (selling price - penalty)
- `cancellation_reason`: Reason for cancellation
- `cancellation_status`: Status of the cancellation (e.g., CANCELLED)
- `cancellation_by`: User ID who initiated the cancellation
- `cancellation_remarks`: Additional remarks for the cancellation
- `refund_mode`: Mode of refund (e.g., 1 for credit, 2 for debit)

### Indexes
```sql
CREATE INDEX idx_cancellation_booking_id ON cancellation_details(booking_id);
CREATE INDEX idx_cancellation_cancellation_id ON cancellation_details(cancellation_id);
CREATE INDEX idx_cancellation_room_line_no ON cancellation_details(room_line_no);
``` 