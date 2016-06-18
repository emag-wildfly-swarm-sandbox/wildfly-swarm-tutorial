package org.javaee7.wildfly.samples.everest.catalog;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.topology.TopologyArchive;

public class Main {

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    swarm.start();

    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
    archive.addPackage(Main.class.getPackage());
    archive.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
    archive.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");
    archive.addAllDependencies();

    archive.as(TopologyArchive.class).advertise(
      swarm.stageConfig().resolve("service.catalog.service-name").getValue()
    );

    swarm.deploy(archive);
  }

}
