package com.ebj;

import org.apache.log4j.Logger;

import com.ebj.http.EBJHttpServer;
import com.ebj.sync.currency.YahooXchangeSync;

/**
 * Finance Xchange-Rate main class
 */
public class XchangeRateServ {
    static Logger logger = Logger.getLogger(XchangeRateServ.class);

    public static void main(String[] args) {
        EBJHttpServer.getInstance().start();
        YahooXchangeSync.getInstance().fire();
    }
}
