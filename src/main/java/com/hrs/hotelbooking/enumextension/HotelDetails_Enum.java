package com.hrs.hotelbooking.enumextension;

public class HotelDetails_Enum {
    public enum BookingStatus {
        PENDING(0),
        CONFIRMED(1),
        CANCELLED(2);

        private int value;

        BookingStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public enum HotelStatus {
        CONFIRMED(1),
        REJECTED(2);

        private int value;

        HotelStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public enum RoomType {
        Single(1),
        Double(2),
        Double_1_Extra_Bed(3);

        private int value;

        RoomType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    
}
