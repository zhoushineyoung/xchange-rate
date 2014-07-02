package com.ebj.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.ebj.service.XchangeRate;
import com.ebj.service.ServerStatus;
import com.sun.net.httpserver.HttpServer;



public class EBJHttpServer {
    static Logger logger = Logger.getLogger(EBJHttpServer.class);
    private static int port = 9099;
    private static EBJHttpServer ebjHttpServer = null;
    
    public static EBJHttpServer getInstance() {
        if (null == ebjHttpServer) {
            ebjHttpServer = new EBJHttpServer();
        }
        return ebjHttpServer;
    }
    private EBJHttpServer() {}

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/status", new ServerStatus());
            server.createContext("/xchangeRates", new XchangeRate());
            server.setExecutor(null); // creates a default executor
            logger.info("start server[status, xchangeRates] at port " + String.valueOf(port));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
