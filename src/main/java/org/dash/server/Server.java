package org.dash.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Singleton
public class Server {
  private static final Logger log = LoggerFactory.getLogger(Server.class);
  private final DashService dashServer;
  private final DashServiceAnnouncer dashServiceAnnouncer;
  private final DashConfig dashConfig;

  @Inject
  public Server(DashService dashServer, DashServiceAnnouncer dashServiceAnnouncer,
      DashConfig dashConfig) {
    this.dashServer = dashServer;
    this.dashServiceAnnouncer = dashServiceAnnouncer;
    this.dashConfig = dashConfig;
  }

  @PostConstruct
  public void start() {
    try {
      dashServer.start(dashConfig.serverPort());
    } catch (Exception e) {
      log.error("Unable to start the server", e);
    }
    
    dashServiceAnnouncer.announce(new DashNode(dashConfig.serverHost(), dashConfig.serverPort()));
  }

  @PreDestroy
  public void stop() {
    try {
      dashServiceAnnouncer
          .unannounce(new DashNode(dashConfig.serverHost(), dashConfig.serverPort()));
      dashServer.stop();
    } catch (Exception e) {
      log.error("Unable to stop the server", e);
    }
  }
}
