package com.featuretoggle.exception;

import com.featuretoggle.model.FeatureToggle;

import java.util.function.Supplier;

public class FeatureToggleException extends RuntimeException implements Supplier<FeatureToggleException> {

    public FeatureToggleException(String message) {
        super(message);
    }

    @Override
    public FeatureToggleException get() {
        return new FeatureToggleException("Error during Feature toggle operation!");
    }
}
