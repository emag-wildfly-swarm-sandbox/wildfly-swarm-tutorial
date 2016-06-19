package org.javaee7.wildfly.samples.everest;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import javax.enterprise.concurrent.ManagedThreadFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EEConcurrencyStrategy extends HystrixConcurrencyStrategy {

  private final ManagedThreadFactory threadFactory;

  public EEConcurrencyStrategy(ManagedThreadFactory threadFactory) {
    this.threadFactory = threadFactory;
  }

  @Override
  public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                          HystrixProperty<Integer> corePoolSize,
                                          HystrixProperty<Integer> maximumPoolSize,
                                          HystrixProperty<Integer> keepAliveTime,
                                          TimeUnit unit,
                                          BlockingQueue<Runnable> workQueue) {
    return new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory);
  }

}
