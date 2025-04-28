package com.hrs.hotelbooking.enumextension;

public class CancelDetails_Enum {
    public enum Refund_Adjustment {
        Refund_To_Original_Card(1),
        Refund_To_Wallet(2);

        private int value;

        Refund_Adjustment(int value) {
            this.value = value;
        }

        public int getCode() {
            return value;
        }
    }

    public enum RequestType {
        Cancellation(1),
        DateChange(2);

        private int value;

        RequestType(int value) {
            this.value = value;
        }

        public int getCode() {
            return value;
        }
    }

}
