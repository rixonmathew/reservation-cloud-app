package com.rixon.learn.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @Autowired
  @Output(Source.OUTPUT)
  private MessageChannel messageChannel;

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

  @RequestMapping(method = RequestMethod.POST)
  public void createReservation(@RequestBody String reservationName){
    //Sample curl call curl -H "Content-Type: text/plain" --data "GullaGulla" http://localhost:9999/reservations
    Message<String> message = MessageBuilder.withPayload(reservationName).build();
    messageChannel.send(message);
  }
}
