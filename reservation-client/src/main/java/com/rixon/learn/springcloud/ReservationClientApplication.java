package com.rixon.learn.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
@EnableBinding(Source.class)
public class ReservationClientApplication {

  private final Logger LOGGER = LoggerFactory.getLogger(ReservationClientApplication.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Bean
  AlwaysSampler alwaysSampler() {
    return new AlwaysSampler();
  }

  @Bean
  public CommandLineRunner runner() {
    return args-> {
      discoveryClient.getInstances("reservation-service").forEach(serviceInstance -> {
        LOGGER.info(" Service Id [{}] running at [{}:{}] ",serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance.getPort());
      });
    };
  }


  public static void main(String[] args) {
    SpringApplication.run(ReservationClientApplication.class, args);
  }
}
