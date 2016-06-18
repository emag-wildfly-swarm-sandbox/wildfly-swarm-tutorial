package org.javaee7.wildfly.samples.services.discovery;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class ServiceDiscovery {

  private WebTarget userService;
  private WebTarget catalogService;
  private WebTarget orderService;

  public abstract String getUserServiceURI();
  public abstract String getCatalogServiceURI();
  public abstract String getOrderServiceURI();

  public WebTarget getUserService() {
    if (userService == null) {
      userService = ClientBuilder
        .newClient()
        .target(
          UriBuilder.fromUri(URI.create(getUserServiceURI()))
            .path("/user/resources/user")
            .build()
        );
    }

    return userService;
  }

  public WebTarget getCatalogService() {
    if (catalogService == null) {
      catalogService = ClientBuilder
        .newClient()
        .target(
          UriBuilder.fromUri(URI.create(getCatalogServiceURI()))
            .path("/catalog/resources/catalog")
            .build()
        );

    }

    return catalogService;
  }

  public WebTarget getOrderService() {
    if (orderService == null) {
      orderService = ClientBuilder
        .newClient()
        .target(
          UriBuilder.fromUri(URI.create(getOrderServiceURI()))
            .path("/order/resources/order")
            .build()
        );

    }

    return orderService;
  }

}
