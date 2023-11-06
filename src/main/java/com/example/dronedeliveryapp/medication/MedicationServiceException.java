package com.example.dronedeliveryapp.medication;

import com.example.dronedeliveryapp.exception.DroneDeliveryAppException;
import org.springframework.http.HttpStatus;

public class MedicationServiceException extends DroneDeliveryAppException {

    private final HttpStatus httpStatus;

    public MedicationServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public MedicationServiceException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public MedicationServiceException(HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public MedicationServiceException(String message, HttpStatus httpStatus, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
