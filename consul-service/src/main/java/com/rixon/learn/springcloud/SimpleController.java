package com.rixon.learn.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class SimpleController {

  private final Logger LOGGER = LoggerFactory.getLogger(SimpleController.class);

  /*CompositePropertySoruce excepts the following in KV store config/<spring.application.name>/message.
    Also it seems the out of the box Consul does not have support to bulk load the Key values from files or git repos. While migrating the configuration
    we will need a small wrapper to set all the key values for a particular service in Consul KeyValue store.
   */
  @Value("${message:Hello}")
  private String message;

  @RequestMapping("/message")
  public String getMessage() {
    return message;
  }
}
