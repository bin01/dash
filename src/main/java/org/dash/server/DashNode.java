package org.dash.server;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class DashNode {

  @JsonProperty
  private String host = null;

  @JsonProperty
  private int port = -1;

  @JsonCreator
  public DashNode(@JsonProperty("host") String host,
      @JsonProperty("port") Integer port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  @Override
  public String toString() {
    return "DashNode [host=" + host + ", port=" + port + "]";
  }

}
