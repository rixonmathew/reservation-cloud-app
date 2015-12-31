package com.rixon.learn.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class ReservationServiceActivator {

  private final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceActivator.class);

  @Autowired
  private ReservationRepository reservationRepository;

  @ServiceActivator(inputChannel = Sink.INPUT)
  public void accept(String reservationName) {
    LOGGER.info("Got name [{}]",reservationName );
    reservationRepository.save(new Reservation(reservationName));
  }
}
