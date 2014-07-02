package com.ebj.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.ebj.conf.JettyServerConfig;
import com.google.common.primitives.Ints;

public class JettyServerModule {

    public static Server name(JettyServerConfig config) {
        final QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(1);
        threadPool.setMaxThreads(10);

        final Server server = new Server();
        server.setThreadPool(threadPool);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8090);
        connector.setMaxIdleTime(Ints.checkedCast(config.getMaxIdleTime().toStandardDuration().getMillis()));
        connector.setStatsOn(true);

        server.setConnectors(new Connector[]{connector});

        return server;
    }
    
    public static void main(String[] args) {
        System.out.println();
    }
}
