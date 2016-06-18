package org.javaee7.wildfly.samples.everest.order;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Embeddable
@XmlRootElement
public class OrderItem implements Serializable {
  int itemId;
  int itemCount;

  public OrderItem() {
  }

  public OrderItem(int itemId, int itemCount) {
    this.itemId = itemId;
    this.itemCount = itemCount;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

}