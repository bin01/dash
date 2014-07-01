package org.dash.server;

public interface DashServiceAnnouncer {

  void announce(DashNode node);

  void unannounce(DashNode node);

}
