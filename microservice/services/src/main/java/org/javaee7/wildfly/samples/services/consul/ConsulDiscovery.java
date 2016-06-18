package org.javaee7.wildfly.samples.services.consul;

import org.javaee7.wildfly.samples.services.ConsulServices;
import org.javaee7.wildfly.samples.services.discovery.ServiceDiscovery;
import org.javaee7.wildfly.samples.services.registration.ServiceRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ConsulServices
@ApplicationScoped
public class ConsulDiscovery extends ServiceDiscovery {

  @Inject
  @ConsulServices
  ServiceRegistry services;

  @Override
  public String getUserServiceURI() {
    return services.discoverServiceURI("user");
  }

  @Override
  public String getCatalogServiceURI() {
    return services.discoverServiceURI("catalog");
  }

  @Override
  public String getOrderServiceURI() {
    return services.discoverServiceURI("order");
  }

}
