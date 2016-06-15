package org.javaee7.wildfly.samples.everest.checkout;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author arungupta
 */
@Embeddable
public class OrderItem implements Serializable {
  int itemId;
  int itemCount;

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
