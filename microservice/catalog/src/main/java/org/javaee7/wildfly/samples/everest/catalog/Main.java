package org.javaee7.wildfly.samples.everest.catalog;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.topology.TopologyArchive;
import org.wildfly.swarm.topology.consul.ConsulTopologyFraction;

public class Main {

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    swarm.fraction(new ConsulTopologyFraction(
      System.getProperty("swarm.consul.url", "http://localhost:8500/")
    ));

    swarm.start();

    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
    archive.addPackage(Main.class.getPackage());
    archive.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
    archive.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");
    archive.addAllDependencies();

    archive.as(TopologyArchive.class).advertise("catalog");

    swarm.deploy(archive);
  }

}
