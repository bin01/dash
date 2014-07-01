package org.dash.client;

import org.dash.server.CommandPayload;
import org.dash.server.DashNode;

public class DashProxy implements Dash {
  private final Dash dash;
  private final DashNode dashNode;

  public DashProxy(Dash dash, DashNode dashNode) {
    this.dash = dash;
    this.dashNode = dashNode;
  }

  @Override
  public String executeCommand(CommandPayload command) {
    return dash.executeCommand(command);
  }

  public DashNode getDashNode() {
    return dashNode;
  }
}
