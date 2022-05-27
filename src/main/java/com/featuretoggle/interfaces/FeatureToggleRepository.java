package com.featuretoggle.interfaces;

import com.featuretoggle.model.FeatureToggle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureToggleRepository extends CrudRepository<FeatureToggle, Integer> {

    /*@Query("{'customerIds._id': :#{#customerId}}")
    List<FeatureToggle> findByCustomerId(@Param("customerId") String customerId);*/
}
