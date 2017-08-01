package reco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reco.model.Feature;
import reco.repositories.FeatureRepository;

@Service("featureService")
@Transactional
public class FeatureServiceImpl implements FeatureService{

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public Feature findById(Long id) {
        return featureRepository.findOne(id);
    }

    @Override
    public Feature findByName(String name) {
        return featureRepository.findByName(name);
    }

    @Override
    public void saveFeature(Feature feature) {
        featureRepository.save(feature);
    }

    @Override
    public void updateUser(Feature feature) {
       featureRepository.save(feature);
    }

    @Override
    public void deleteFeatureById(Long id) {
        featureRepository.delete(id);
        
    }

    @Override
    public void deleteAllFeatures() {
        featureRepository.deleteAll();
    }

    @Override
    public List<Feature> findAllFeatures() {
        return featureRepository.findAll();
    }
    
    
}
