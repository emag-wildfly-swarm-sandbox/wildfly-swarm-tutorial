package org.javaee7.wildfly.samples.everest.order;

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
    archive.addAllDependencies();

    archive.as(TopologyArchive.class).advertise(
      swarm.stageConfig().resolve("service.order.service-name").getValue()
    );

    swarm.deploy(archive);
  }

}
