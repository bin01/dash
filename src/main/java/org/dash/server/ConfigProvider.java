package org.dash.server;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConfigProvider<T> implements Provider<T> {
  private final Class<T> clazz;
  private ConfigurationObjectFactory factory = null;

  public ConfigProvider(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Inject
  public void inject(ConfigurationObjectFactory factory) {
    this.factory = factory;
  }

  @Override
  public T get() {
    try {
      return factory.build(clazz);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  public static <T> void bind(Binder binder, Class<T> clazz) {
    binder.bind(clazz).toProvider(new ConfigProvider<>(clazz)).asEagerSingleton();
  }
}
