package com.featuretoggle.converter;

import com.featuretoggle.dto.FeatureToggleDTO;
import com.featuretoggle.model.FeatureToggle;

public class FeatureConverter {

    public static FeatureToggleDTO toDTO(FeatureToggle feature) {
    var dto = new FeatureToggleDTO();

        dto.setCustomerIds(feature.getCustomerIds());
        dto.setDescription(feature.getDescription());
        dto.setDisplayName(feature.getDisplayName());
        dto.setExpiresOn(feature.getExpiresOn());
        dto.setId(feature.getId());
        dto.setInverted(feature.getInverted());
        dto.setTechnicalName(feature.getTechnicalName());

        return dto;
}

    public static FeatureToggle toFeature(FeatureToggleDTO dto) {
        var feature = new FeatureToggle();

        feature.setCustomerIds(dto.getCustomerIds());
        feature.setDescription(dto.getDescription());
        feature.setDisplayName(dto.getDisplayName());
        feature.setExpiresOn(dto.getExpiresOn());
        feature.setId(dto.getId());
        feature.setInverted(dto.getInverted());
        feature.setTechnicalName(dto.getTechnicalName());

        return feature;
    }
}
