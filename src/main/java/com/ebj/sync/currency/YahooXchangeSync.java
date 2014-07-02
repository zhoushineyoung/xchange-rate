package com.ebj.sync.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import com.ebj.dao.h2.H2DBDao;
import com.ebj.domain.AllCurrencies;
import com.ebj.domain.yahooxchange.YahooXchangeRate;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.gson.Gson;

public class YahooXchangeSync {
    private static Logger logger = Logger.getLogger(YahooXchangeSync.class);

    /*
     * select * from yahoo.finance.xchange where pair in (\"USDCNY\",\"EURCNY\")
     */
    static String yahoo_finance_api = "http://query.yahooapis.com/v1/public/yql?q=%s&env=store://datatables.org/alltableswithkeys&format=json&callback=";
    static String yahoo_finance_query = "select %s from yahoo.finance.xchange where pair in (%s)";
    private final static int batchSize = (2 << 6) - 1;
    static final String __QUOTE = "\"";
    static final String __COMMA = ",";
    static final String __STAR = "*";
    
    private static int conn_time_out = 5; // 5s
    private static int socket_time_out = 60; // 60s
    
    private Timer timer = null;

    private static YahooXchangeSync yahooXchangeSync = null;
    
    private YahooXchangeSync() {}
    public static YahooXchangeSync getInstance() {
        if (null == yahooXchangeSync) {
            yahooXchangeSync = new YahooXchangeSync();
        }
        return yahooXchangeSync;
    }
    
    public YahooXchangeRate getXchangeRate(String from, String to) {
        String yqlStr = buildYahooQueryStr(from, to);
        return query(yqlStr);
    }
    
    public List<YahooXchangeRate> getAllXchangeRates() {
        List<YahooXchangeRate> yahooXchangeRates = null;
        
        String[] allCurrencyNames = AllCurrencies.getAllCurrencyNames();
        StringBuilder stringBuilder = null;
        YahooXchangeRate yahooXchangeRate = null;
        int loop_counter = 0;
        
        if (allCurrencyNames.length > 1) {
            yahooXchangeRates = new ArrayList<YahooXchangeRate>();
            stringBuilder = new StringBuilder();
            
            int size = allCurrencyNames.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    String spliter = (i == (size - 1) && i == j) ? "" : __COMMA;
                    if (i != j) {
                        stringBuilder.append(__QUOTE + allCurrencyNames[i] + allCurrencyNames[j] + __QUOTE + spliter);
                        loop_counter ++;
                        if ((loop_counter & batchSize) == 0) {
                            String pairs = stringBuilder.toString();
                            
                            yahooXchangeRate = batchQuery(pairs);
                            if (null != yahooXchangeRate) {
                                yahooXchangeRates.add(yahooXchangeRate);
                            }else {
                                logger.info("Get None For Pairs[" + pairs + "].");
                            }
                            
                            stringBuilder.setLength(0); // 重置stringBuilder ，namely 清空 stringBuilder 内容
                        }
                    }
                }
            }
            
            if (stringBuilder != null && stringBuilder.length() > 0) {
                String pairs = stringBuilder.toString();
                
                yahooXchangeRate = batchQuery(pairs);
                if (null != yahooXchangeRate) {
                    yahooXchangeRates.add(yahooXchangeRate);
                }
            }
        }
        return yahooXchangeRates;
    }

    public Map<String, Double> getAllXchangeRatesAsMap() {
        Map<String, Double> allXchangeRate = null;
        
        List<YahooXchangeRate> yahooXchangeRates = getAllXchangeRates();
        
        if (yahooXchangeRates.size() > 0) {
            allXchangeRate = new HashMap<String, Double>();
            for (YahooXchangeRate yahooXchangeRate : yahooXchangeRates) {
                if (null != yahooXchangeRate.getRatesMap()) {
                    allXchangeRate.putAll(yahooXchangeRate.getRatesMap());
                }
            }
        }
        return allXchangeRate;
    }
    
    private static String yeahMobiHttpRequest(String url) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, (int) TimeUnit.SECONDS.toMillis(conn_time_out));
        HttpConnectionParams.setSoTimeout(httpParams, (int) TimeUnit.SECONDS.toMillis(socket_time_out));
        DefaultHttpClient client = new DefaultHttpClient(httpParams);

        InputStream inStream = null;
        BufferedReader bReader = null;
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            inStream = response.getEntity().getContent();
            bReader = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder strBuilder = new StringBuilder();

            String line;
            while (null != (line = bReader.readLine())) {
                strBuilder.append(line);
            }
            String queryResult = strBuilder.toString();
            if (logger.isDebugEnabled()) {
                logger.debug("Return Msg \r\n[" + queryResult + "] From Yahoo Finance API With Query: \r\n[" + url + "].");
                String code = String.valueOf(response.getStatusLine().getStatusCode());
                if (!Strings.isNullOrEmpty(code)) {
                    if ("200".equals(code)) {
                        logger.debug("Yahoo Finance API [" + url + "] Query Succeed.");
                    } else {
                        logger.debug("Yahoo Finance API [" + url + "] Query Returned With Code [" + code + "].");
                    }
                }
            }
            return queryResult;
        } catch (ClientProtocolException e) {
            logger.error("error:", e);
        } catch (IOException e) {
            logger.error("error:", e);
        } finally {
            try {
                if (null != bReader) {
                    bReader.close();
                }
                if (null != inStream) {
                    inStream.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }

            if (null != client) {
                client.getConnectionManager().shutdown();
            }
        }
        return "";
    }

    private static String buildYahooQueryStr(String from, String to) {
        if (!Strings.isNullOrEmpty(from) && !Strings.isNullOrEmpty(to)) {
            String pair = "\"" + (from + to).toUpperCase() + "\"";
            try {
                return String.format(yahoo_finance_api, URLEncoder.encode(String.format(yahoo_finance_query, "*", pair + ",\"EURCNY\""), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
        }
        return "";
    }
    
    private static YahooXchangeRate query(String yqlStr) {
        if (!Strings.isNullOrEmpty(yqlStr)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            String result = yeahMobiHttpRequest(yqlStr);
            if (!Strings.isNullOrEmpty(result)) {
                if (logger.isDebugEnabled()) {
                    long __time_cost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                    logger.debug("Getting Xchange Rate Cost: " + __time_cost + " Milliseconds.");
                    logger.debug("With Yahoo-Finance Query : \r\n[" + yqlStr + "]");
                    logger.debug("Result = " + result);
                }
                return new Gson().fromJson(result, YahooXchangeRate.class);
            }
        }
        return null;
    }

    private static YahooXchangeRate batchQuery(String pairs) {
        if (!Strings.isNullOrEmpty(pairs)) {
            String query = "";
            
            if (pairs.endsWith(__COMMA)) {
                pairs = pairs.substring(0, pairs.lastIndexOf(__COMMA));
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Yahoo-Finance Query Pairs : \r\n[" + pairs + "]");
            }
            try {
                query = String.format(yahoo_finance_api, URLEncoder.encode(String.format(yahoo_finance_query, __STAR, pairs), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
            
            return query(query);
        }
        return null;
    }

    private static AtomicInteger count = new AtomicInteger();
    private void sync() {
        logger.info("Yahoo Xchange Rates Sync Run [" + count.incrementAndGet() + "] Times, @Time [" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,sss").format(new Date()) + "].");
        
        Map<String, Double> xchangeRateMap = YahooXchangeSync.getInstance().getAllXchangeRatesAsMap();
        if (null != xchangeRateMap && xchangeRateMap.size() > 0) {
            H2DBDao h2dbDao = H2DBDao.getInstance();
            
            h2dbDao.setValue_map(xchangeRateMap);
            h2dbDao.setTableName("YAHOO_XCHANGE_RATE");
            h2dbDao.createTable();
            h2dbDao.doInsert();
        }
    }
    
    public void fire() {
        // sync once when app start up
        sync();

        // schedule sync at every 00:00 and 12:00
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // every 00:00:00 and 12:00:00
        calendar.set(year, month, day, 0, 0);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sync();
            }
        }, calendar.getTime(), TimeUnit.MILLISECONDS.convert(12, TimeUnit.HOURS));
    }
}
