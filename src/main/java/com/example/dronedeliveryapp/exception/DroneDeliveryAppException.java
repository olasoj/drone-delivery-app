package com.example.dronedeliveryapp.exception;

public class DroneDeliveryAppException extends RuntimeException {


    public DroneDeliveryAppException() {
    }

    public DroneDeliveryAppException(String message) {
        super(message);
    }

    public DroneDeliveryAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public DroneDeliveryAppException(Throwable cause) {
        super(cause);
    }

    public DroneDeliveryAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
