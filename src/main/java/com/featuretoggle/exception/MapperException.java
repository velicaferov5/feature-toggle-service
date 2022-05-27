package com.featuretoggle.exception;

public class MapperException extends RuntimeException {

    public MapperException() {
        super("Could not convert item!");
    }
}
