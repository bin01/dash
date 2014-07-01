package org.dash.client;

public class DashCommandOutputPrinter {

  public static final void print(DashCommandResult result) {
    System.out.println(result.getNode().getHost() + ":" + result.getNode().getPort());
    System.out.println("--------------------------------------------------------------");
    System.out.println(result.getOutput());
  }
  
}
