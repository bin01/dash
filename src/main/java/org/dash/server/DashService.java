package org.dash.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class DashService {
  private Server server;
  private ObjectMapper objectMapper;

  @Inject
  public DashService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void start(int port) throws Exception {
    server = new Server(port);
    final ServletContextHandler queries = new ServletContextHandler(ServletContextHandler.SESSIONS);
    queries.addServlet(new ServletHolder(new CommandExecutorServlet(objectMapper)), "/dash/*");

    final ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
    root.addServlet(new ServletHolder(new DefaultServlet()), "/*");

    final HandlerList handlerList = new HandlerList();
    handlerList.setHandlers(new Handler[] {queries, root, new DefaultHandler()});
    server.setHandler(handlerList);
    server.start();
  }

  public void stop() throws Exception {
    server.stop();
  }

}
