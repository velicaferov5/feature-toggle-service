package com.featuretoggle.service;

import com.featuretoggle.exception.NotFoundException;
import com.featuretoggle.interfaces.FeatureToggleRepository;
import com.featuretoggle.model.FeatureToggle;
import com.featuretoggle.util.ValidatorEntities;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Services to manage Feature Toggles
 */
@Service
public class FeatureService {

    private FeatureToggleRepository featureRepository;

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
     * Create Feature Toggle
     *
     * @param feature
     * @return optional Order
     */
    public FeatureToggle create(FeatureToggle feature) {
        return save(feature);
    }

    /**
     * Updates and returns feature toggle
     *
     * @param feature
     * @return updated featureToggle
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
     * @return archived featureToggle
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
     * @return inverted featureToggle
     */
    public FeatureToggle invert(Integer id, Boolean inverted) {
        var featureDb = featureRepository.findById(id).orElseThrow(NotFoundException::new);
        featureDb.setInverted(inverted);

        return save(featureDb);
    }

    public FeatureToggle save(FeatureToggle feature) {
        if (isValid(feature)) {
            return featureRepository.save(feature);
        }

        return new FeatureToggle();
    }

    public boolean isValid(FeatureToggle feature) {
        return new ValidatorEntities<FeatureToggle>().test(feature);
    }
}
