package com.example.dronedeliveryapp.exception;

import com.example.dronedeliveryapp.util.response.ResponseModel;
import com.example.dronedeliveryapp.util.response.model.Response;
import com.example.dronedeliveryapp.util.response.model.ResponseError;
import com.example.dronedeliveryapp.util.response.transformer.ResponseAssembler;
import com.example.dronedeliveryapp.util.response.transformer.ResponseErrorAssembler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class HttpMethodNotAssignToUrlErrHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMethodNotAssignToUrlErrHandler.class);
    private final ResponseModel responseModel;

    public HttpMethodNotAssignToUrlErrHandler(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex) {
        var message = String.format("Could not find the %s method for URL: %s", ex.getHttpMethod(), ex.getRequestURL());
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(message, HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}