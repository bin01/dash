package org.dash.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DefaultObjectMapper extends ObjectMapper {

  private static final long serialVersionUID = 1593367199104131464L;

  public DefaultObjectMapper() {}

  public DefaultObjectMapper(JsonFactory jsonFactory) {
    super(jsonFactory);
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    configure(MapperFeature.AUTO_DETECT_GETTERS, false);
    configure(MapperFeature.AUTO_DETECT_FIELDS, false);
    configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
    configure(MapperFeature.AUTO_DETECT_SETTERS, false);
    configure(SerializationFeature.INDENT_OUTPUT, false);
  }

}
