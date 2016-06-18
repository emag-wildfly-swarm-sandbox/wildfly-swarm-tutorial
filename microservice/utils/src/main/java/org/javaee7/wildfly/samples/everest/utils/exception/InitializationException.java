package org.javaee7.wildfly.samples.everest.utils.exception;

public class InitializationException extends RuntimeException {

  public InitializationException() {
    super();
  }

  public InitializationException(String err) {
    super(err);
  }

  public InitializationException(Throwable t) {
    super(t);
  }

}
