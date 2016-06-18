package org.javaee7.wildfly.samples.everest.utils;

import org.javaee7.wildfly.samples.everest.utils.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class WildFlyUtil {

  private static final Logger log = LoggerFactory.getLogger(WildFlyUtil.class);

  private static String hostName = "localhost";
  private static int hostPort = 8080;
  private static int hostSecurePort = 8443;

  @PostConstruct
  void init() throws InitializationException {

    try {
      MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

      ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
      hostName = (String) mBeanServer.getAttribute(http, "boundAddress");
      hostPort = (int) mBeanServer.getAttribute(http, "boundPort");

      ObjectName ws = new ObjectName("jboss.ws", "service", "ServerConfig");
//      hostName = (String) mBeanServer.getAttribute(ws, "WebServiceHost");
//      hostPort = (int) mBeanServer.getAttribute(ws, "WebServicePort");
      hostSecurePort = (int) mBeanServer.getAttribute(ws, "WebServiceSecurePort");
      log.info("--> " + hostName + " : " + hostPort + "/" + hostSecurePort);
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }

  }

  public String getHostName() {
    return hostName;
  }

  public int getHostPort() {
    return hostPort;
  }

  public int getSecurePort() {
    return hostSecurePort;
  }

}
