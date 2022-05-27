package com.featuretoggle.interfaces;

import com.featuretoggle.model.FeatureToggle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureToggleRepository extends CrudRepository<FeatureToggle, Integer> {

}
