package oomtest.api;


import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

@CrossOrigin

public class HealthCheckApi {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String HEALTHCHECK_OK = "HealthCheckApi -> OK! - {}";
    @Autowired ObjectMapper mapper;
    
    @RequestMapping(value = "/health", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    public ResponseEntity<String> getHealth() {
        try {
        	OffsetDateTime x = OffsetDateTime.now(ZoneId.of("Z"));
            log.debug(HEALTHCHECK_OK, x);
            return new ResponseEntity<>(mapper.writeValueAsString("OK! - "+x),HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("unexpected exception",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
