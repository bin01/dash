package org.dash.server;

import javax.validation.Validation;
import javax.validation.Validator;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ConfigModule implements Module {
  
  @Override
  public void configure(Binder binder) {
    binder.bind(Validator.class).toInstance(Validation.buildDefaultValidatorFactory().getValidator());
    binder.bind(ConfigurationObjectFactory.class).toInstance(
        new ConfigurationObjectFactory(System.getProperties()));
  }

}
