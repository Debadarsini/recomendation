package reco.service;

import java.util.List;

import reco.model.Feature;

public interface FeatureService {
    
    Feature findById(Long id);
    
    Feature findByName(String name);
    
    void saveFeature(Feature feature);
    
    void updateUser(Feature feature);
    
    void deleteFeatureById(Long id);
    
    void deleteAllFeatures();
    
    List<Feature> findAllFeatures();
    

}
