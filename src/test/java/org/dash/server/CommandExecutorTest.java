package org.dash.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.Test;

public class CommandExecutorTest {

  @Test
  public void test() throws ExecuteException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CommandLine cmdLine = CommandLine.parse("tail -100f /Users/firefly/Programming/elasticsearch/README.textile");
    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
    PumpStreamHandler streamHander = new PumpStreamHandler(baos);
    ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
    Executor executor = new DefaultExecutor();
    executor.setExitValue(1);
    executor.setStreamHandler(streamHander);
    executor.setWatchdog(watchdog);
    executor.execute(cmdLine, resultHandler);
    try {
      resultHandler.waitFor();
    } catch (InterruptedException e) {
    }
    System.out.println(new String(baos.toByteArray()));
  }

}
