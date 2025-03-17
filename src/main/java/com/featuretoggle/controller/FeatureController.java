package com.featuretoggle.controller;

import com.featuretoggle.converter.FeatureConverter;
import com.featuretoggle.dto.FeatureToggleDTO;
import com.featuretoggle.exception.MapperException;
import com.featuretoggle.exception.NotFoundException;
import com.featuretoggle.service.FeatureService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST-ful services to make and manage order.
 */
@RestController
@RequestMapping(value = "/api/featuretoggle")
@CrossOrigin(origins = "http://localhost:4200")
public class FeatureController {

    private final FeatureService service;

    public FeatureController(FeatureService service) {
        this.service = service;
    }

    /**
     * Finds and returns feature toggle
     *
     * @param id
     * @return featureToggleDTO
     */
    @GetMapping(value = "/{id}")
    public FeatureToggleDTO findById(@PathVariable(name="id") Integer id) {
        var feature = service.findById(id);
        return feature.map(FeatureConverter::toDTO).orElseThrow(NotFoundException::new);
    }

    /**
     * Finds and returns all feature toggles
     *
     * @return featureToggleDTO list
     */
    @GetMapping
    public List<FeatureToggleDTO> findAll() {
        return service.findAll().stream().map(FeatureConverter::toDTO).collect(Collectors.toList());
    }

    /**
     * Filters and returns all {@param inverted} and not-{@param inverted} feature toggles assigned to customer by {@param customerId}
     *
     * @return featureToggleDTO list
     */
    @GetMapping("/{customerid}/{inverted}")
    public List<FeatureToggleDTO> findByCustomerId(@PathVariable(name="customerid") String customerId, @PathVariable(name="inverted") Boolean inverted) {
        return service.findByCustomerId(customerId, inverted).stream().map(FeatureConverter::toDTO).collect(Collectors.toList());
    }

    /**
     * Create new Feature toggle
     *
     * @param dto
     * @return new feature toggle
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureToggleDTO create(@Valid @RequestBody FeatureToggleDTO dto) {
        var feature = Optional.of(dto).map(FeatureConverter::toFeature).orElseThrow(MapperException::new);
        return Optional.of(service.create(feature)).map(FeatureConverter::toDTO).orElseThrow(MapperException::new);
    }

    /**
     * Update feature toggle
     *
     * @param dto
     * @return updated feature toggle
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureToggleDTO update(@Valid @RequestBody FeatureToggleDTO dto) {
        var feature = Optional.of(dto).map(FeatureConverter::toFeature).orElseThrow(MapperException::new);
        return Optional.of(service.update(feature)).map(FeatureConverter::toDTO).orElseThrow(MapperException::new);
    }

    /**
     * Archive (set expiration date as now) feature toggle
     *
     * @param id
     * @return archived feature toggle
     */
    @PutMapping(value = "/archive/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureToggleDTO archive(@PathVariable(name="id") Integer id) {
        return Optional.of(service.archive(id)).map(FeatureConverter::toDTO).orElseThrow(MapperException::new);
    }

    /**
     * Invert feature toggle
     *
     * @param id
     * @param inverted
     * @return inverted feature toggle
     */
    @PutMapping(value = "/{id}/{inverted}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureToggleDTO invert(@PathVariable(name="id") Integer id, @PathVariable(name="inverted") Boolean inverted) {
        return Optional.of(service.invert(id, inverted)).map(FeatureConverter::toDTO).orElseThrow(MapperException::new);
    }
}
