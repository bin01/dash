package org.dash.server;

import org.skife.config.Config;
import org.skife.config.Default;

public interface DashConfig {

  @Config("server.host")
  @Default("127.0.0.1")
  String serverHost();

  @Config("server.port")
  @Default("8080")
  int serverPort();
  
  @Config("zk.connection.string")
  @Default("127.0.0.1:2181")
  String zkConnectionString();
  
  @Config("zk.service.sessionTimeoutMs")
  @Default("30000")
  int zkSessionTimeoutMs();
  
  @Config("dash.path.prefix")
  @Default("/dash_app")
  String dashPathPrefix();
  
  @Config("curator.compress")
  @Default("false")
  boolean enableCompression();
  
}
