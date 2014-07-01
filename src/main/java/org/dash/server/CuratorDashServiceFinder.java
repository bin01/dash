package org.dash.server;

import java.util.Collection;

import javax.inject.Inject;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceInstance;

public class CuratorDashServiceFinder implements DashServiceFinder {
  private ServiceDiscovery<Void> discovery;

  @Inject
  public CuratorDashServiceFinder(ServiceDiscovery<Void> discovery) {
    this.discovery = discovery;
  }

  @Override
  public Observable<DashNode> find() {
    Observable<ServiceInstance<Void>> serviceInstances = Observable.<ServiceInstance<Void>>create(new OnSubscribe<ServiceInstance<Void>>() {
      @Override
      public void call(Subscriber<? super ServiceInstance<Void>> subscriber) {
        try {
          if(subscriber.isUnsubscribed()) {
            return;
          }
          Collection<ServiceInstance<Void>> instances = discovery.queryForInstances(CuratorDashServiceAnnouncer.DASH_SERVICE);
          for(ServiceInstance<Void> instance : instances) {
            if(subscriber.isUnsubscribed()) {
              return;
            }
            subscriber.onNext(instance);
          }
          if(!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
          }
        } catch(Exception e) {
          if(!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
          }
        }
      }
    });
    return serviceInstances.map(input -> new DashNode(input.getAddress(), input.getPort()));
  }

}
