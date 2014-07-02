package com.ebj;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.ebj.dao.h2.H2DBDao;
import com.ebj.http.EBJHttpServer;
import com.ebj.sync.currency.YahooXchangeSync;

/**
 * Hello world!
 *
 */
public class XchangeRateServ 
{
    static Logger logger = Logger.getLogger(XchangeRateServ.class);
    private static AtomicInteger count = new AtomicInteger();
    
    public static void main( String[] args )
    {
    	/*
    	 * http://localhost:9099/xchangeRates?params%3D%7B%22query_type%22%3A%22all%22%2C%22return_format%22%3A%22json%22%2C%22query_id%22%3A%2288e771bc-a11-44dd-afa2-9fcc056e9eeb%22%2C%22colums%22%3A%5B%22id%22%2C%22currency_from%22%2C%22currency_to%22%2C%22rate_from_to%22%2C%22rate_usd_to%22%2C%22is_deleted%22%2C%22last_update_time%22%5D%7D
    	 */
        EBJHttpServer.getInstance().start();
        // String query_params = "params={\"query_type\":\"all\",\"return_format\":\"json\",\"query_id\":\"88e771bc-a11-44dd-afa2-9fcc056e9eeb\",\"colums\":[\"id\",\"currency_from\",\"currency_to\",\"rate_from_to\",\"rate_usd_to\",\"is_deleted\",\"last_update_time\"]}";
        // String query_params = "params={\"query_type\":\"colums\",\"return_format\":\"json\",\"query_id\":\"88e771bc-a11-44dd-afa2-9fcc056e9eeb\",\"colums\":[\"id\",\"currency_from\",\"currency_to\",\"rate_from_to\",\"rate_usd_to\"]}";
        String query_params = "params={\"query_type\":\"colums\",\"return_format\":\"json\",\"query_id\":\"88e771bc-a11-44dd-afa2-9fcc056e9eeb\",\"colums\":[\"currency_from\",\"currency_to\",\"rate_from_to\",\"rate_usd_to\"]}";
        try {
            System.out.println(URLEncoder.encode(query_params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
         final String sql = "SELECT * FROM YAHOO_XCHANGE_RATE;";
//        final String sql = "SELECT * FROM YAHOO_XCHANGE_RATE YXR WHERE (YXR.CURRENCY_FROM='CNY' AND YXR.CURRENCY_TO='USD') OR (YXR.CURRENCY_FROM='USD' AND YXR.CURRENCY_TO='CNY');";
        /*
         * -- SELECT * FROM YAHOO_XCHANGE_RATE YXR WHERE YXR.CURRENCY_FROM='currency' AND YXR.CURRENCY_TO='currency_type';
         * SELECT * FROM YAHOO_XCHANGE_RATE YXR WHERE YXR.CURRENCY_FROM='CNY' AND YXR.CURRENCY_TO='USD';
         * SELECT RATE_FROM_TO,RATE_USD_TO FROM YAHOO_XCHANGE_RATE YXR WHERE YXR.CURRENCY_FROM='MYR' AND YXR.CURRENCY_TO='USD';
         */
        YahooXchangeSync.getInstance().fire();
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                doQuery(sql);
                logger.info("Query [" + sql + "] Run [" + count.incrementAndGet() + "] Times, @Time [" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,sss").format(new Date()) + "].");
            }
        }, 100, 3600000);
        
        
    }
    
    private static void doQuery(String sql) {
        H2DBDao h2dbDao = H2DBDao.getInstance();
        List<String> list = new ArrayList<String>();
        if (true) {
            ResultSet resultSet = h2dbDao.doQuery(sql);
            
            try {
                String[] params = {"ID","CURRENCY_FROM","CURRENCY_TO","RATE_FROM_TO","RATE_USD_TO","IS_DELETED","LAST_UPDATE_TIME"};
                
                list.add(Arrays.toString(params));
                while (resultSet.next()) {
                    String str = "";
                    
                    for (int i = 0; i < params.length; i++) {
                        str += resultSet.getObject(params[i].toString());
                        if (i != params.length - 1 ) {
                            str += ",";
                        }
                        
                    }
                    list.add(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                // ...
            }
        }
        for (String string : list) {
            System.out.println(string);
        }
    }
}
