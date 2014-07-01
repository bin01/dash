package org.dash.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JacksonSerialization {

  @Test
  public void test() throws IOException {
    DefaultObjectMapper mapper = new DefaultObjectMapper();
    String string = mapper.writeValueAsString(new CommandPayload("ls"));
    System.out.println(string);
    CommandPayload value = mapper.readValue(string, CommandPayload.class);
    System.out.println(value);
  }

}
