package org.dash.client;

import java.util.Scanner;

import org.dash.server.ConfigModule;
import org.dash.server.DiscoveryModule;
import org.dash.server.JacksonModule;

import rx.Observable;

import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;

public class DashClientMain {

  public static void main(String[] args) {
    Injector injector =
        LifecycleInjector.builder()
            .withModules(new JacksonModule(), new ConfigModule(), new DiscoveryModule()).build()
            .createInjector();
    
    DashClient client = injector.getInstance(DashClient.class);
    Scanner scanner = new Scanner(System.in);
    while(true) {
      System.out.print("Enter your command> ");
      String command = scanner.nextLine();
      if("".equals(command)) {
        continue;
      }
      if("exit".equals(command)) {
        System.out.println("Bye Bye..");
        break;
      }
      Observable<DashCommandResult> results = client.executeCommand(command);
      results.toBlocking().forEach(DashCommandOutputPrinter::print);
    }
    scanner.close();
  }

}
