package com.example.dronedeliveryapp.delivery;

import com.example.dronedeliveryapp.exception.DroneDeliveryAppException;
import org.springframework.http.HttpStatus;

public class DeliveryServiceException extends DroneDeliveryAppException {

    private final HttpStatus httpStatus;

    public DeliveryServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DeliveryServiceException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public DeliveryServiceException(HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public DeliveryServiceException(String message, HttpStatus httpStatus, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
