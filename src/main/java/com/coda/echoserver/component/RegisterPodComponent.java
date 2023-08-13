package com.coda.echoserver.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class RegisterPodComponent {
    @Autowired
    private Environment environment;

    private RestTemplate restTemplate;

    private  boolean retryRegistration = false;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        restTemplate = new RestTemplate();
        retryRegistration = this.registerPod();
    }

    public boolean registerPod() {
        String externalServerPort = environment.getProperty("JAVA_SERVER_PORT");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String url = "http://host.docker.internal:8888/v1/registerPod";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                                .queryParam("hostName","http://host.docker.internal")
                                .queryParam("port", externalServerPort).build();

        try{

            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity ,String.class);
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                log.error("Error communicate with routing server");
                return  true;
            }
        } catch (Exception e) {
            log.error("Error communicate with routing server");
            e.printStackTrace();
           return true;
        }

        return false;

    }

    @Scheduled(cron = "*/10 * * * * *")
    public void onSchedule() {
        // retry to register pod with routing server
        if (retryRegistration) {
            retryRegistration = this.registerPod();
        }
    }


}
