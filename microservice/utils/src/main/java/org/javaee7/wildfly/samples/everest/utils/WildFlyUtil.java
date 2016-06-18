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

  private static final String JBOSS_BIND_ADDRESS = "jboss.bind.address";
  private static final String SWARM_BIND_ADDRESS = "swarm.bind.address";
  private static final String JBOSS_SOCKET_BINDING_PORT_OFFSET = "jboss.socket.binding.port-offset";
  private static final String SWARM_PORT_OFFSET = "swarm.port.offset";

  private static String hostName = "localhost";
  private static int hostPort = 8080;
  private static int hostSecurePort = 8443;

  @PostConstruct
  void init() throws InitializationException {

    try {
      if (!resolveFromSystemProps()) {
        resolveFromJMX();
      }
    } catch (Exception e) {
      System.err.println("--------------------------------");
      System.err.println(
        "WARN: We cannot resolve the IP address of this service instance. " +
          "Make sure to pass '-Dswarm.bind.address=<PUBLIC_IP>'. " +
          "Fallback to 'localhost'.");
      System.err.println("--------------------------------");
    }

    log.info("[INFO] Host and port resolved to: " + hostName + " : " + hostPort + "/" + hostSecurePort);
  }

  private boolean resolveFromSystemProps() {
    String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS) != null ?
      System.getProperty(JBOSS_BIND_ADDRESS) : System.getProperty(SWARM_BIND_ADDRESS);

    String portOffset = System.getProperty(JBOSS_SOCKET_BINDING_PORT_OFFSET) != null ?
      System.getProperty(JBOSS_SOCKET_BINDING_PORT_OFFSET) : System.getProperty(SWARM_PORT_OFFSET, "0");

    if (bindAddress != null) {
      Integer offset = Integer.valueOf(portOffset);
      hostName = bindAddress;
      hostPort = hostPort + offset;
      hostSecurePort = hostSecurePort + offset;
    }

    return bindAddress != null;
  }

  private void resolveFromJMX() {
    try {
      MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

      ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
      hostName = (String) mBeanServer.getAttribute(http, "boundAddress");
      hostPort = (int) mBeanServer.getAttribute(http, "boundPort");

      ObjectName ws = new ObjectName("jboss.ws", "service", "ServerConfig");
      hostSecurePort = (int) mBeanServer.getAttribute(ws, "WebServiceSecurePort");
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
