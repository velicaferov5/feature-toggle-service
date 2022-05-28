package com.featuretoggle.service;

import com.featuretoggle.exception.NotFoundException;
import com.featuretoggle.interfaces.FeatureToggleRepository;
import com.featuretoggle.model.FeatureToggle;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeatureServiceTest {

    public static final String FEATURE_TOGGLE_NOT_FOUND = "Feature Toggle not found!";
    private static final Integer ID = 1;
    private static final String DISPLAY_NAME = "Cool feature";
    private static final String TECHNICAL_NAME = "UDDFD12";
    private static final OffsetDateTime FUTURE_DATE_TIME = OffsetDateTime.of(2100, 12, 1, 1, 1, 1, 1, ZoneOffset.MAX);
    private static final String DESCRIPTION = "Feature for a project";
    private static final boolean INVERTED = true;
    private static final String CUSTOMER_ID = "11";
    private static final String CUSTOMER_IDS = "11,12,13";
    public static final String NO_VALUE_PRESENT = "No value present";

    @Mock
    private FeatureToggleRepository repository;

    @InjectMocks
    private FeatureService service;

    @Test
    public void findById_found() {
        var feature = newFeature();
        when(repository.findById(anyInt())).thenReturn(Optional.of(feature));
        var actual = service.findById(1).get();
        assertFeatures(feature, actual);
    }

    @Test
    public void findById_fail_noValuePresent() {
        var exception = Assertions.assertThrows(NoSuchElementException.class, () -> service.findById(1).get());
        assertThat(exception.getMessage()).isEqualTo(NO_VALUE_PRESENT);
    }

    @Test
    public void findAll_found() {
        var expected = Arrays.asList(newFeature());
        when(repository.findAll()).thenReturn(expected);
        var actual = service.findAll();
        assertFeatures(expected, actual);
    }

    @Test
    public void findAll_notFound() {
        var actual = service.findAll();
        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    public void findByCustomerId_invertedFound() {
        var feature = newFeature();
        var expected = Arrays.asList(feature, feature);
        when(repository.findAll()).thenReturn(expected);
        var actual = service.findByCustomerId(CUSTOMER_ID, true);
        assertFeatures(expected, actual);
    }

    @Test
    public void findByCustomerId_notInvertedFound() {
        var feature = newFeature();
        feature.setInverted(false);
        var expected = Arrays.asList(feature, feature);
        when(repository.findAll()).thenReturn(expected);
        var actual = service.findByCustomerId(CUSTOMER_ID, false);
        assertFeatures(expected, actual);
    }

    @Test
    public void findByCustomerId_invertedNotFound() {
        var feature = newFeature();
        feature.setInverted(false);
        var expected = Arrays.asList(feature, feature);
        when(repository.findAll()).thenReturn(expected);
        var result = service.findByCustomerId(CUSTOMER_ID, true);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByCustomerId_notFound() {
        var feature = newFeature();
        feature.setCustomerIds(null);
        var expected = Arrays.asList(feature, feature);
        when(repository.findAll()).thenReturn(expected);
        var result = service.findByCustomerId(CUSTOMER_ID, true);
        assertTrue(result.isEmpty());
    }

    @Test
    public void create_success() {
        var inputFeature = newFeature();
        when(repository.save(inputFeature)).thenReturn(inputFeature);
        var actual = service.create(inputFeature);
        assertFeatures(inputFeature, actual);
    }

    @Test
    public void create_fail_validationException() {
        var inputFeature = newFeature();
        inputFeature.setTechnicalName(null);
        var exception = Assertions.assertThrows(ValidationException.class, () -> service.create(inputFeature));
        assertEquals(exception.getMessage(), "TechnicalName can not be empty");
        Mockito.verify(repository, times(0)).save(inputFeature);
    }

    @Test
    public void create_notSaved() {
        var inputFeature = newFeature();
        assertNull(service.create(inputFeature));
    }

    @Test
    public void update_success() {
        var inputFeature = newFeature();
        var expected = newFeature();
        expected.setTechnicalName(inputFeature.getTechnicalName() + "UGJ564");
        when(repository.findById(inputFeature.getId())).thenReturn(Optional.of(expected));
        when(repository.save(expected)).thenReturn(expected);
        var actual = service.update(inputFeature);
        assertFeatures(expected, actual);
    }

    @Test
    public void update_fail_featureNotFound() {
        var inputFeature = newFeature();
        var exception = Assertions.assertThrows(NotFoundException.class, () -> service.update(inputFeature));
        assertThat(exception.getMessage()).isEqualTo(FEATURE_TOGGLE_NOT_FOUND);
        Mockito.verify(repository, times(0)).save(inputFeature);
    }

    @Test
    public void update_fail_validationException() {
        var inputFeature = newFeature();
        inputFeature.setInverted(null);
        var exception = Assertions.assertThrows(ValidationException.class, () -> service.create(inputFeature));
        assertEquals(exception.getMessage(), "Inverted can not be null");
        Mockito.verify(repository, times(0)).save(inputFeature);
    }

    @Test
    public void archive_success() {
        var feature = newFeature();
        when(repository.findById(ID)).thenReturn(Optional.of(feature));
        var expected = newFeature();
        expected.setExpiresOn(OffsetDateTime.now());
        when(repository.save(ArgumentMatchers.any(FeatureToggle.class))).thenReturn(expected);
        var actual = service.archive(ID);
        assertThat(actual.getExpiresOn().isBefore(OffsetDateTime.now()));
        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(FeatureToggle.class));
    }

    @Test
    public void archive_fail_featureNotFound() {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(NotFoundException.class, () -> service.archive(ID));
        assertThat(exception.getMessage()).isEqualTo(FEATURE_TOGGLE_NOT_FOUND);
        Mockito.verify(repository, times(0)).save(ArgumentMatchers.any(FeatureToggle.class));
    }

    @Test
    public void invert_success() {
        var feature = newFeature();
        when(repository.findById(ID)).thenReturn(Optional.of(feature));
        var expected = newFeature();
        expected.setInverted(!feature.getInverted());
        when(repository.save(feature)).thenReturn(expected);
        var actual = service.invert(ID, !feature.getInverted());
        assertFeatures(expected, actual);
        Mockito.verify(repository, times(1)).save(expected);
    }

    @Test
    public void invert_fail_featureNotFound() {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(NotFoundException.class, () -> service.invert(ID, INVERTED));
        assertThat(exception.getMessage()).isEqualTo(FEATURE_TOGGLE_NOT_FOUND);
        Mockito.verify(repository, times(0)).save(ArgumentMatchers.any(FeatureToggle.class));
    }

    @Test
    public void save_success() {
        var inputFeature= newFeature();
        when(repository.save(ArgumentMatchers.any(FeatureToggle.class))).thenReturn(inputFeature);
        var actual = service.save(inputFeature);
        Mockito.verify(repository, times(1)).save(inputFeature);
        assertEquals(inputFeature, actual);
    }

    @Test
    public void save_fail_validationException() {
        var inputFeature = newFeature();
        inputFeature.setTechnicalName(null);
        var exception = Assertions.assertThrows(ValidationException.class, () -> service.save(inputFeature));
        assertEquals(exception.getMessage(), "TechnicalName can not be empty");
        Mockito.verify(repository, times(0)).save(inputFeature);
    }

    @Test
    public void isValid_success() {
        var feature = newFeature();
        assertTrue(service.isValid(feature));
    }

    @Test
    public void isValid_fail_customerIdEmpty() {
        var feature = newFeature();
        feature.setCustomerIds("");
        var exception = Assertions.assertThrows(ValidationException.class, () -> service.isValid(feature));
        assertThat(exception.getMessage()).isEqualTo("CustomerIds can not be empty");
    }

    @Test
    public void isValid_fail_invertedNull() {
        var feature = newFeature();
        feature.setInverted(null);
        var exception = Assertions.assertThrows(ValidationException.class, () -> service.isValid(feature));
        assertThat(exception.getMessage()).isEqualTo("Inverted can not be null");
    }

    private void assertFeatures(FeatureToggle expected, FeatureToggle actual) {
        assertEquals(expected.getCustomerIds(), actual.getCustomerIds());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDisplayName(), actual.getDisplayName());
        assertEquals(expected.getExpiresOn(), actual.getExpiresOn());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getInverted(), actual.getInverted());
        assertEquals(expected.getTechnicalName(), actual.getTechnicalName());
    }

    private void assertFeatures(List<FeatureToggle> expected, List<FeatureToggle> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i=0; i<actual.size(); i++) {
            assertFeatures(expected.get(i), actual.get(i));
        }
    }


    private FeatureToggle newFeature() {
        var feature = new FeatureToggle();
        feature.setCustomerIds(CUSTOMER_IDS);
        feature.setDescription(DESCRIPTION);
        feature.setDisplayName(DISPLAY_NAME);
        feature.setExpiresOn(FUTURE_DATE_TIME);
        feature.setId(ID);
        feature.setInverted(INVERTED);
        feature.setTechnicalName(TECHNICAL_NAME);

        return feature;
    }
}