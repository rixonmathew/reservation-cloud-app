package com.rixon.learn.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationApiGatewayRestController {

  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  @RequestMapping("/names")
  @HystrixCommand(fallbackMethod = "getDefaultNames")
  public Collection<String> getReservationNames() {
    ParameterizedTypeReference<Resources<Reservation>> type = new ParameterizedTypeReference<Resources<Reservation>>(){};
    ResponseEntity<Resources<Reservation>> responseEntity = restTemplate.exchange("http://reservation-service/reservations", HttpMethod.GET, null, type);
    return responseEntity.getBody()
        .getContent()
        .stream()
        .map(Reservation::getReservationName)
        .collect(Collectors.toList());
  }

  public Collection<String> getDefaultNames() {
    return Arrays.asList("This","is","default","name");
  }
}
