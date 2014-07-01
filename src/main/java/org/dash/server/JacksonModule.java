package org.dash.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;

public class JacksonModule implements Module {

  @Override
  public void configure(Binder binder) {
    binder.bind(ObjectMapper.class).toInstance(new DefaultObjectMapper());
  }

}
