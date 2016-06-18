package org.javaee7.wildfly.samples.everest.catalog;

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
public class CatalogService {

  private static final String serviceName = "catalog";

  @Inject
  @ConsulServices
  ServiceRegistry services;

  @Inject
  WildFlyUtil util;

  private String endpoint;

  @PostConstruct
  public void registerService() {
    endpoint = getEndpoint();
    services.registerService(serviceName, endpoint);
  }

  @PreDestroy
  public void unregisterService() {
    services.unregisterService(serviceName, endpoint);
  }

  private String getEndpoint() {
    return "http://" + util.getHostName() + ":" + util.getHostPort() + "/catalog/resources/catalog";
  }

}
