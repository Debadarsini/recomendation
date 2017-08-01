package reco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import reco.model.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Feature findByName(String name);
}
