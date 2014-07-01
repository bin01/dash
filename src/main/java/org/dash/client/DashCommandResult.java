package org.dash.client;

import org.dash.server.DashNode;

public class DashCommandResult {
  private final DashNode node;
  private final String output;

  public DashCommandResult(DashNode node, String output) {
    this.node = node;
    this.output = output;
  }

  public DashNode getNode() {
    return node;
  }

  public String getOutput() {
    return output;
  }

}
