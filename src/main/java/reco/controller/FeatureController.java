package reco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import reco.model.Feature;
import reco.service.FeatureService;

@RestController
@RequestMapping("/reco/feature")
public class FeatureController  {

    @Autowired
    FeatureService featureService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Feature>> listAllFeatures(){
        List<Feature> features = featureService.findAllFeatures();
        if(features.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Feature>> (features,HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/{id}")
    public ResponseEntity<Feature> getFeature(@PathVariable("id") long id){
        Feature feature = featureService.findById(id);
        if(feature==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Feature>(feature,HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> createFeature(@RequestBody Feature feature,UriComponentsBuilder ucBuilder){
         featureService.saveFeature(feature);
         HttpHeaders headers = new HttpHeaders();
         headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(feature.getId()).toUri());
         return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
   
    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature){
         featureService.updateUser(feature);
         return new ResponseEntity<Feature>(feature, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFeature(@PathVariable("id") long id) {
       
        featureService.deleteFeatureById(id);
        return new ResponseEntity<Feature>(HttpStatus.NO_CONTENT);
    }
 
   
    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity<Feature> deleteAllUsers() {
      
        featureService.deleteAllFeatures();
        return new ResponseEntity<Feature>(HttpStatus.NO_CONTENT);
    }
 
    
}
