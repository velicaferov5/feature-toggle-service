package com.featuretoggle.service;

import com.featuretoggle.exception.NotFoundException;
import com.featuretoggle.interfaces.FeatureToggleRepository;
import com.featuretoggle.model.FeatureToggle;
import com.featuretoggle.util.ValidatorEntities;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Services to manage Feature Toggles
 */
@Service
public class FeatureService {

    private final FeatureToggleRepository featureRepository;

    public FeatureService(FeatureToggleRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    /**
     * Finds Feature Toggle by {@param id}.
     * @param id
     * @return FeatureToggle
     */
    public Optional<FeatureToggle> findById(Integer id) {
        return featureRepository.findById(id);
    }

    /**
     * Finds all Feature Toggles.
     * @return FeatureToggle list
     */
    public List<FeatureToggle> findAll() {
        return (List<FeatureToggle>) featureRepository.findAll();
    }

    /**
     * Filters and returns {@param inverted} and not-{@param inverted} feature toggles assigned to customer by {@param customerId}
     *
     * @param customerId
     * @param inverted
     * @return Feature toggles
     */
    public List<FeatureToggle> findByCustomerId(String customerId, Boolean inverted) {
        var features = (List<FeatureToggle>) featureRepository.findAll();

        return features.stream().filter(f ->
            f.getInverted().equals(inverted) && f.getCustomerIds() != null
                    && Arrays.stream(f.getCustomerIds().split(",")).anyMatch(cId -> cId.equals(customerId)))
                .collect(Collectors.toList());

    }

    /**
     * Create Feature Toggle
     *
     * @param feature
     * @return saved feature toggle
     */
    public FeatureToggle create(FeatureToggle feature) {
        return save(feature);
    }

    /**
     * Updates and returns feature toggle
     *
     * @param feature
     * @return updated feature toggle
     */
    public FeatureToggle update(@Valid FeatureToggle feature) {
        var featureDb = featureRepository.findById(feature.getId()).orElseThrow(NotFoundException::new);
        featureDb.setCustomerIds(feature.getCustomerIds());
        featureDb.setDescription(feature.getDescription());
        featureDb.setDisplayName(feature.getDisplayName());
        featureDb.setExpiresOn(feature.getExpiresOn());
        featureDb.setInverted(feature.getInverted());
        featureDb.setTechnicalName(feature.getTechnicalName());

        return save(featureDb);
    }

    /**
     * Archives (sets expiresOn as now) and returns feature toggle
     *
     * @param id
     * @return archived feature toggle
     */
    public FeatureToggle archive(Integer id) {
        var featureDb = featureRepository.findById(id).orElseThrow(NotFoundException::new);
        var now = OffsetDateTime.now();
        featureDb.setExpiresOn(now);

        return save(featureDb);
    }

    /**
     * Inverts feature toggle to {@param inverted} value and returns
     *
     * @param id
     * @param inverted
     * @return inverted feature toggle
     */
    public FeatureToggle invert(Integer id, Boolean inverted) {
        var featureDb = featureRepository.findById(id).orElseThrow(NotFoundException::new);
        featureDb.setInverted(inverted);

        return save(featureDb);
    }

    /**
     * Validates, formats and saves {@param feature} to repository.
     * Not saved and empty feature returned if isn't valid.
     *
     * @param feature
     * @return saved feature toggle
     */
    public FeatureToggle save(FeatureToggle feature) {
        if (isValid(feature)) {
            format(feature);
            return featureRepository.save(feature);
        }

        return new FeatureToggle();
    }

    /**
     * Validates {@param feature} if conditions in FeatureToggle class are met
     * @param feature
     * @return is valid
     */
    public boolean isValid(FeatureToggle feature) {
        return new ValidatorEntities<FeatureToggle>().test(feature);
    }

    /**
     * Removes whitespace from customerIds
     * @param feature
     */
    private void format(FeatureToggle feature) {
        if (feature.getCustomerIds() != null) {
            feature.setCustomerIds(feature.getCustomerIds().replaceAll("\\s+",""));
        }
    }
}
