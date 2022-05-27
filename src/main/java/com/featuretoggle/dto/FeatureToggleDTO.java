package com.featuretoggle.dto;

import java.time.OffsetDateTime;
import java.util.Objects;

public class FeatureToggleDTO {

    private Integer id;

    private String displayName;

    private String technicalName;

    private OffsetDateTime expiresOn;

    private String description;

    private Boolean inverted;

    private String customerIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInverted() {
        return inverted;
    }

    public void setInverted(Boolean inverted) {
        this.inverted = inverted;
    }

    public String getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureToggleDTO that = (FeatureToggleDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(technicalName, that.technicalName) &&
                Objects.equals(expiresOn, that.expiresOn) &&
                Objects.equals(description, that.description) &&
                Objects.equals(inverted, that.inverted) &&
                Objects.equals(customerIds, that.customerIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName, technicalName, expiresOn, description, inverted, customerIds);
    }

    @Override
    public String toString() {
        return "FeatureToggle{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", technicalName='" + technicalName + '\'' +
                ", expiresOn=" + expiresOn +
                ", description='" + description + '\'' +
                ", inverted=" + inverted +
                ", customerIds=" + customerIds +
                '}';
    }

    public enum Status {
        NOT_AVAILABLE,
        RUNNING,
        DELIVERED,
        CANCELLED
    }
}
