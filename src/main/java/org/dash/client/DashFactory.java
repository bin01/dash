package org.dash.client;

import org.dash.server.DashNode;
import org.dash.server.DefaultObjectMapper;

import feign.Feign;
import feign.jackson.JacksonEncoder;

public class DashFactory {

  public static Dash create(DashNode node) {
    DefaultObjectMapper mapper = new DefaultObjectMapper();
    return Feign.builder().encoder(new JacksonEncoder(mapper))
        .target(Dash.class, "http://" + node.getHost() + ":" + node.getPort());
  }
  
  public static DashProxy createProxy(DashNode node) {
    Dash dash = create(node);
    return new DashProxy(dash, node);
  }
  
}
