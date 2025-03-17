package com.featuretoggle.util;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

public class ValidatorEntities<T> implements Predicate<T> {

	@Override
	public boolean test(T t) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

		String errors = constraintViolations.stream()
				.map(ConstraintViolation::getMessageTemplate)
				.collect(Collectors.joining(", "));

		if (errors != null && !errors.isEmpty()) {
			throw new ValidationException(errors);
		}

		return true;
	}

}
