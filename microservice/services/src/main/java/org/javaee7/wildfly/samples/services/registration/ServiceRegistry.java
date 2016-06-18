package org.javaee7.wildfly.samples.services.registration;

public interface ServiceRegistry {

  void registerService(String name, String uri);
  void unregisterService(String name, String uri);
  String discoverServiceURI(String name);

}
