package com.featuretoggle.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Feature Toggle not found!");
    }
}
