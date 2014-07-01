package org.dash.server;

import rx.Observable;

public interface DashServiceFinder {

  Observable<DashNode> find();

}
