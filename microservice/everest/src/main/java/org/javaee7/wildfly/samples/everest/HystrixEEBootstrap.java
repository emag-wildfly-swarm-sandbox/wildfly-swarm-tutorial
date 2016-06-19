package org.javaee7.wildfly.samples.everest;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.strategy.HystrixPlugins;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedThreadFactory;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class HystrixEEBootstrap {

  @Resource
  ManagedThreadFactory threadFactory;

  @PostConstruct
  public void onStartup() {
    System.out.println("Initializing hystrix...");
    HystrixPlugins.getInstance().registerConcurrencyStrategy(new EEConcurrencyStrategy(threadFactory));
  }

  @PreDestroy
  public void onShutdown() {
    System.out.println("Shutting down hystrix...");
    Hystrix.reset(1, TimeUnit.SECONDS);
  }

}
