package org.javaee7.wildfly.samples.everest.order;

import org.javaee7.wildfly.samples.everest.utils.WildFlyUtil;
import org.javaee7.wildfly.samples.services.ConsulServices;
import org.javaee7.wildfly.samples.services.registration.ServiceRegistry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class OrderService {

  @Inject
  @ConsulServices
  ServiceRegistry services;

  @Inject
  WildFlyUtil util;

  private static final String serviceName = "order";

  @PostConstruct
  public void registerService() {
    services.registerService(serviceName, getEndpoint());
  }

  @PreDestroy
  public void unregisterService() {
    services.unregisterService(serviceName, getEndpoint());
  }

  private String getEndpoint() {
    return "http://" + util.getHostName()+ ":" + util.getHostPort() + "/order/resources/order";
  }

}