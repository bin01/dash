package org.dash.server;

import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

public class Main {

  public static void main(String[] args) throws Exception {
    Injector injector =
        LifecycleInjector.builder()
            .withModules(new JacksonModule(), new ConfigModule(), new DiscoveryModule()).build()
            .createInjector();

    injector.getInstance(Server.class);
    
    final LifecycleManager manager = injector.getInstance(LifecycleManager.class);
    manager.start();
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        manager.close();
      }
    });
  }

}
