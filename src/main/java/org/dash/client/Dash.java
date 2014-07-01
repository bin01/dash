package org.dash.client;


import org.dash.server.CommandPayload;

import feign.RequestLine;

public interface Dash {

  @RequestLine("POST /dash/execute")
  String executeCommand(CommandPayload command);

}
