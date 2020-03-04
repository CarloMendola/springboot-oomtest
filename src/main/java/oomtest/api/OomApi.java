package oomtest.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import oomtest.service.OomService;

@RestController

@CrossOrigin

public class OomApi {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired ObjectMapper mapper;
    @Autowired OomService oomService;
    
    @RequestMapping(value = "/oom", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    public ResponseEntity<String> getHealth(@RequestParam Boolean triggerOom) {
        try {
            log.debug("trigger oom from api: {}",triggerOom);
            if(triggerOom) {
            	oomService.goOutOfMemory();            	
            }
            return new ResponseEntity<>(mapper.writeValueAsString("OK!"),HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("unexpected exception",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
