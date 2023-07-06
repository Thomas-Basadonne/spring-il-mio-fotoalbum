package com.photographer.photoalbum.exceptions;

public class PhotoNotFoundExceptions extends RuntimeException {
    public PhotoNotFoundExceptions(String message) {
        super(message);
    }
}
