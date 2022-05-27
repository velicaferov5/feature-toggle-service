package com.featuretoggle.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name="FeatureToggle")
public class FeatureToggle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String displayName;

    @NotEmpty(message = "TechnicalName can not be empty")
    @Column
    private String technicalName;

    @Column
    private OffsetDateTime expiresOn;

    @Column
    private String description;

    @NotNull(message = "Inverted can not be null")
    @Column
    private Boolean inverted;


    @NotEmpty(message = "CustomerIds can not be empty")
    @Column
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
        FeatureToggle that = (FeatureToggle) o;
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
