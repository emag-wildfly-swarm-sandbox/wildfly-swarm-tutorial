package org.javaee7.wildfly.samples.everest.utils.cdi;

import org.javaee7.wildfly.samples.everest.utils.WildFlyUtil;
import org.javaee7.wildfly.samples.everest.utils.qualifiers.QServerName;
import org.javaee7.wildfly.samples.everest.utils.qualifiers.QServerPort;

import javax.inject.Inject;
import javax.ws.rs.Produces;

public class EnvironmentProducer {

  @Inject
  private WildFlyUtil wildFlyUtil;

  @Produces
  @QServerName
  public String getServerName() {
    return wildFlyUtil.getHostName();
  }

  @Produces
  @QServerPort
  public int getServerPort() {
    return wildFlyUtil.getHostPort();
  }

  @Produces
  @QServerName
  public int getSecurePort() {
    return wildFlyUtil.getSecurePort();
  }
}
