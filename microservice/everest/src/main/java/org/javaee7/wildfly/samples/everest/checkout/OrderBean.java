package org.javaee7.wildfly.samples.everest.checkout;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.javaee7.wildfly.samples.everest.cart.Cart;
import org.javaee7.wildfly.samples.everest.cart.CartItem;
import org.javaee7.wildfly.samples.services.discovery.ServiceDiscovery;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Named
@SessionScoped
public class OrderBean implements Serializable {

  @Inject
  Order order;

  @Inject
  Cart cart;

  String status;

  @Inject
  ServiceDiscovery services;

  public void saveOrder() {
    List<CartItem> cartItems = cart.getItems();
    cartItems.stream().map((cartItem) -> {
      OrderItem orderItem = new OrderItem();
      orderItem.itemId = cartItem.getItemId();
      orderItem.itemCount = cartItem.getItemCount();
      return orderItem;
    }).forEach((orderItem) -> {
      order.getOrderItems().add(orderItem);
    });

    try {
      HystrixRequestContext context = HystrixRequestContext.initializeContext();

      try {
        OrderCommand.Result result = new OrderCommand(services, order.asJson()).execute();
        if (result.isSuccessful()) {
          cart.clearCart();
        }
        status = result.getStatus();
      } finally {
        context.shutdown();
      }

    } catch (Exception e) {
      status = e.getLocalizedMessage();
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}