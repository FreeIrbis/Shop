package com.shop.controller.exception;

import com.shop.error.ShopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        logExceptionResponse(exceptionResponse);
        logException(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ShopException.class, java.sql.SQLException.class})
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(ShopException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        logExceptionResponse(exceptionResponse);
        logException(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private void logExceptionResponse(ExceptionResponse exceptionResponse) {
        logger.error("--------------------ERROR RESPONSE--------------------");
        logger.info(exceptionResponse.toString());
    }

    private void logException(Exception ex) {
        logger.error(ex.toString());
        logger.error(ex.toString(), ex);
        logger.error("--------------------ERROR--------------------");
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        for(StackTraceElement stackTraceElement : stackTraceElements) {
            logger.error(stackTraceElement.toString());
        }
    }

}
