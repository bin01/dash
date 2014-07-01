package org.dash.client;

import javax.inject.Inject;

import org.dash.server.CommandPayload;
import org.dash.server.DashNode;
import org.dash.server.DashServiceFinder;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

public class DashClient {
  private DashServiceFinder finder;

  @Inject
  public DashClient(DashServiceFinder finder) {
    this.finder = finder;
  }

  public Observable<DashCommandResult> executeCommand(String command) {
    Observable<DashNode> nodes = finder.find();
    final CommandPayload commandPayload = new CommandPayload(command);
    Observable<DashProxy> clients = nodes.map(DashFactory::createProxy);
    Observable<DashCommandResult> serverResults =
        clients.flatMap(Async.<DashProxy, DashCommandResult>toAsync(
            (DashProxy client) -> new DashCommandResult(client.getDashNode(), client.executeCommand(commandPayload)) 
            , Schedulers.io()));
    return serverResults;
  }

}
