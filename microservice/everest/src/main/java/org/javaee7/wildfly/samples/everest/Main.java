package org.javaee7.wildfly.samples.everest;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.topology.TopologyArchive;
import org.wildfly.swarm.undertow.WARArchive;

public class Main {

  static final String[] webResources = {"cart", "catalog", "catalog-item", "checkout", "confirm", "index", "user", "user-status"};

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    WARArchive archive = ShrinkWrap.create(WARArchive.class );
    archive.addPackages(true, Main.class.getPackage());

    ClassLoader classLoader = Main.class.getClassLoader();

    for(String webResource : webResources) {
      String fileName = webResource + ".xhtml";
      archive.addAsWebResource( new ClassLoaderAsset(fileName, classLoader), fileName);
    }

    archive.addAsWebInfResource(
      new ClassLoaderAsset("WEB-INF/web.xml", classLoader), "web.xml");

    archive.addAllDependencies();

    archive.as(TopologyArchive.class).advertise(
      swarm.stageConfig()
        .resolve("service.web.service-name")
        .getValue()
    );

    swarm.start().deploy(archive);
  }

}
