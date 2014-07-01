package org.dash.server;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.jetty.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = "/dash/execute", asyncSupported = true)
public class CommandExecutorServlet extends HttpServlet {
  private static final long serialVersionUID = 7493520354930629629L;
  private final ObjectMapper jsonMapper;

  public CommandExecutorServlet(ObjectMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    resp.setStatus(HttpStatus.BAD_REQUEST_400);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    final AsyncContext asyncContext = req.startAsync(req, resp);
    req.getInputStream();
    CommandPayload commandPayload =
        jsonMapper.readValue(req.getInputStream(), CommandPayload.class);
    CommandLine cmdLine = CommandLine.parse(commandPayload.getCommand());
    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
    PumpStreamHandler streamHander = new PumpStreamHandler(resp.getOutputStream());
    ExecuteWatchdog watchdog = new ExecuteWatchdog(3 * 1000);
    Executor executor = new DefaultExecutor();
    executor.setStreamHandler(streamHander);
    executor.setExitValue(1);
    executor.setWatchdog(watchdog);
    executor.execute(cmdLine, resultHandler);
    try {
      resultHandler.waitFor();
      asyncContext.complete();
    } catch (InterruptedException e) {
      resp.sendError(500);
    }
  }

}
