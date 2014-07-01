package org.dash.server;

import org.junit.Assert;
import org.junit.Test;
import org.skife.config.ConfigurationObjectFactory;
import org.skife.config.SimplePropertyConfigSource;

public class DashConfigTest {

  @Test
  public void testDefaults() {
    ConfigurationObjectFactory factory =
        new ConfigurationObjectFactory(new SimplePropertyConfigSource(System.getProperties()));
    DashConfig config = factory.build(DashConfig.class);
    Assert.assertEquals("127.0.0.1:2181", config.zkConnectionString());
    Assert.assertEquals("/dash", config.dashPathPrefix());
  }

}
