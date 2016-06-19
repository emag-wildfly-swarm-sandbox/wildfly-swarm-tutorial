package org.javaee7.wildfly.samples.everest.checkout;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
@XmlRootElement
public class Order implements Serializable {

  int orderId;
  List<OrderItem> orderItems;

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public List<OrderItem> getOrderItems() {
    if (null == orderItems) {
      orderItems = new ArrayList<>();
    }
    return orderItems;
  }

  public String asJsonString() {
    JsonArray orderItems = null;
    for (OrderItem orderItem : getOrderItems()) {
      orderItems = Json.createArrayBuilder()
        .add(Json.createObjectBuilder()
          .add("itemId", orderItem.getItemId())
          .add("itemCount", orderItem.getItemCount()))
        .build();
    }

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("orderItems", orderItems)
      .build();

    StringWriter writer = new StringWriter();
    try (JsonWriter w = Json.createWriter(writer)) {
      w.write(jsonObject);
    }

    return writer.toString();
  }

  public Entity<String> asJson() {
    return Entity.json(asJsonString());
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
