package com.example.dronedeliveryapp.drone.advise;

import com.example.dronedeliveryapp.delivery.DeliveryServiceException;
import com.example.dronedeliveryapp.drone.DroneServiceException;
import com.example.dronedeliveryapp.medication.MedicationServiceException;
import com.example.dronedeliveryapp.util.response.ResponseModel;
import com.example.dronedeliveryapp.util.response.model.Response;
import com.example.dronedeliveryapp.util.response.model.ResponseError;
import com.example.dronedeliveryapp.util.response.transformer.ResponseAssembler;
import com.example.dronedeliveryapp.util.response.transformer.ResponseErrorAssembler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DroneServiceExceptionAdvise {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroneServiceExceptionAdvise.class);
    private final ResponseModel responseModel;

    public DroneServiceExceptionAdvise(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(DroneServiceException.class)
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, DroneServiceException ex) {
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(ex.getMessage(), ex.getHttpStatus());
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(ex.getHttpStatus(), responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    @ExceptionHandler(DeliveryServiceException.class)
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, DeliveryServiceException ex) {
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(ex.getMessage(), ex.getHttpStatus());
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(ex.getHttpStatus(), responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    @ExceptionHandler(MedicationServiceException.class)
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, MedicationServiceException ex) {
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(ex.getMessage(), ex.getHttpStatus());
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(ex.getHttpStatus(), responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}