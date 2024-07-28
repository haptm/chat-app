package com.haptm.chatapp.exceptionHandler;

import com.haptm.chatapp.exception.FriendRequestNotFoundException;
import com.haptm.chatapp.exception.InvalidPasswordException;
import com.haptm.chatapp.exception.UserExistException;
import com.haptm.chatapp.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        return new ErrorMessage(10000, ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        return new ErrorMessage(10010, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ErrorMessage(10100, ex.getMessage());
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleUserExistException(UserExistException ex, WebRequest request) {
        return new ErrorMessage(10101, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        return new ErrorMessage(10102, ex.getMessage());
    }

    @ExceptionHandler(FriendRequestNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleFriendRequestNotFoundException(FriendRequestNotFoundException ex, WebRequest request) {
        return new ErrorMessage(10103, ex.getMessage());
    }
}
