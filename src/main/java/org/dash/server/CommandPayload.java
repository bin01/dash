package org.dash.server;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class CommandPayload {

  private String command;

  public CommandPayload() {
  }
  
  @JsonCreator
  public CommandPayload(@JsonProperty("command") String command) {
    this.command = command;
  }

  @JsonProperty
  public String getCommand() {
    return command;
  }

  @Override
  public String toString() {
    return "CommandPayload [command=" + command + "]";
  }

}
