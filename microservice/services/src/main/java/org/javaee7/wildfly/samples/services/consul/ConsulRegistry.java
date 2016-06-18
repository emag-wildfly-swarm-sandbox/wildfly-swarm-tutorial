package org.javaee7.wildfly.samples.services.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import org.javaee7.wildfly.samples.services.ConsulServices;
import org.javaee7.wildfly.samples.services.registration.ServiceRegistry;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@ConsulServices
@ApplicationScoped
public class ConsulRegistry implements ServiceRegistry {

  @Override
  public void registerService(String name, String uri) {
    try {
      URL url = new URL(uri);

      ConsulClient client =  getConsulClient();

      NewService newService = new NewService();
      newService.setId(serviceId(name, url.getHost(), url.getPort()));
      newService.setName(name);
      newService.setAddress(url.getHost());
      newService.setPort(url.getPort());

      NewService.Check serviceCheck = new NewService.Check();
      serviceCheck.setHttp(uri);
      serviceCheck.setInterval("30s");
      newService.setCheck(serviceCheck);

      client.agentServiceRegister(newService);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private ConsulClient getConsulClient() {
    String consulHost = System.getProperty("consul.host", "192.168.99.100");
    return new ConsulClient(consulHost);
  }

  private String serviceId(String name, String address, int port) {
    return name + ":" + address + ":" + port;
  }

  @Override
  public void unregisterService(String name, String uri) {
    try {
      ConsulClient client = getConsulClient();
      URL url = new URL(uri);
      client.agentServiceDeregister(serviceId(name, url.getHost(), url.getPort()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String discoverServiceURI(String name) {
    ConsulClient client = getConsulClient();
    Map<String, Service> agentServices = client.getAgentServices().getValue();

    Service match = agentServices.entrySet().stream()
      .filter(e -> e.getValue().getService().equals(name))
      .findFirst()
      .orElseThrow(() -> new RuntimeException(String.format("Service '%s' cannot be found!", name)))
      .getValue();

    try {
      URL url = new URL("http://" + match.getAddress() + ":" + match.getPort());
      return url.toExternalForm();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
