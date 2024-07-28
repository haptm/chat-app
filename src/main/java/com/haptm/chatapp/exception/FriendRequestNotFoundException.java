package com.haptm.chatapp.exception;

public class FriendRequestNotFoundException extends RuntimeException{
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
