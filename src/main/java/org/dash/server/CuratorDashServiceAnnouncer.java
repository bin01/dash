package org.dash.server;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceInstance;

public class CuratorDashServiceAnnouncer implements DashServiceAnnouncer {
  public static final String DASH_SERVICE = "dash";
  private static final Logger log = LoggerFactory.getLogger(CuratorDashServiceAnnouncer.class);
  private final ServiceDiscovery<Void> discovery;
  private final Object monitor = new Object();
  private Optional<ServiceInstance<Void>> serviceInstance = Optional
      .<ServiceInstance<Void>>absent();
  
  @Inject
  public CuratorDashServiceAnnouncer(ServiceDiscovery<Void> discovery) {
    this.discovery = discovery;
  }

  @Override
  public void announce(DashNode node) {
    final ServiceInstance<Void> instance;
    synchronized (monitor) {
      if (serviceInstance.isPresent()) {
        log.warn("Ignoring request to announce service[{}]", DASH_SERVICE);
        return;
      } else {
        try {
          instance =
              ServiceInstance.<Void>builder().name(DASH_SERVICE)
              .address(node.getHost())
              .port(node.getPort())
              .build();
        } catch (Exception e) {
          throw Throwables.propagate(e);
        }

        serviceInstance = Optional.of(instance);
      }
    }

    try {
      log.info("Announcing service [{}]", DASH_SERVICE);
      discovery.registerService(instance);
    } catch (Exception e) {
      log.warn("Failed to announce service[{}]", DASH_SERVICE, e);
      synchronized (monitor) {
        serviceInstance = Optional.absent();
      }
    }
  }

  @Override
  public void unannounce(DashNode node) {
    synchronized (monitor) {
      if (!serviceInstance.isPresent()) {
        log.warn("Ignoring request to unannounce service[{}]", DASH_SERVICE);
        return;
      }
    }

    try {
      log.info("Unannouncing service [{}]", DASH_SERVICE);
      discovery.unregisterService(serviceInstance.get());
    } catch (Exception e) {
      log.error("Unable to unannounce service[{}]", DASH_SERVICE, e);
    } finally {
      synchronized (monitor) {
        serviceInstance = Optional.absent();
      }
    }
  }

}
