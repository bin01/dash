package org.dash.server;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.BoundedExponentialBackoffRetry;
import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder;

public class DiscoveryModule implements Module {

  @Override
  public void configure(Binder binder) {
    ConfigProvider.bind(binder, DashConfig.class);
    binder.bind(DashServiceAnnouncer.class).to(CuratorDashServiceAnnouncer.class);
    binder.bind(DashServiceFinder.class).to(CuratorDashServiceFinder.class);
  }

  @Provides
  CuratorFramework provideCurator(DashConfig config) {
    CuratorFramework framework =
        CuratorFrameworkFactory.builder().connectString(config.zkConnectionString())
            .sessionTimeoutMs(config.zkSessionTimeoutMs())
            .retryPolicy(new BoundedExponentialBackoffRetry(1000, 45000, 10)).build();
    framework.start();
    return framework;
  }

  @Provides
  ServiceDiscovery<Void> provideServiceDiscovery(CuratorFramework curator, DashConfig config) {
    return ServiceDiscoveryBuilder.<Void>builder(Void.class).basePath(config.dashPathPrefix())
        .client(curator).build();
  }
  
}
