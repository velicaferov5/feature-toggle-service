package com.featuretoggle.util;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

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
