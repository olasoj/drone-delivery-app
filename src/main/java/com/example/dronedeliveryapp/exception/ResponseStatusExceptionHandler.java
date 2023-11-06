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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseStatusExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseStatusExceptionHandler.class);
    private final ResponseModel responseModel;

    public ResponseStatusExceptionHandler(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(HttpServletRequest request, HttpServletResponse response, ResponseStatusException ex) {
        LOGGER.error(ex.getMessage(), ex);

        HttpStatus httpStatus = HttpStatus.resolve(ex.getStatusCode().value());
        assert httpStatus != null;

        ResponseError responseError = ResponseErrorAssembler.toResponseError(ex.getReason(), httpStatus);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(httpStatus, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}