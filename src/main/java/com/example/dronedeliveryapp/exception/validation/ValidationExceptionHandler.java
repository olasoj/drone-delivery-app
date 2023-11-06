package com.example.dronedeliveryapp.exception.validation;

import com.example.dronedeliveryapp.util.response.ResponseModel;
import com.example.dronedeliveryapp.util.response.model.Response;
import com.example.dronedeliveryapp.util.response.model.ResponseError;
import com.example.dronedeliveryapp.util.response.transformer.ResponseAssembler;
import com.example.dronedeliveryapp.util.response.transformer.ResponseErrorAssembler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    private final ResponseModel responseModel;

    public ValidationExceptionHandler(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        Map<String, Object> errors = getErrors(ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError(errors, "Validation failed", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    private Map<String, Object> getErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError("No/Invalid Request body provided", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotSupportedException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError("No/Invalid Request body provided", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}
