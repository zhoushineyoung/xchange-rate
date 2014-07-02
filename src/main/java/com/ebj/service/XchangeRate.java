package com.ebj.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebj.dao.h2.H2DBDao;
import com.ebj.domain.MsgType;
import com.ebj.domain.QueryParam;
import com.ebj.domain.QueryResult;
import com.ebj.domain.QueryType;
import com.ebj.domain.YAHOO_XCHANGE_RATE;
import com.ebj.exception.EBJException;
import com.ebj.utils.HttpUtil;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class XchangeRate implements HttpHandler {
    private static Logger logger = Logger.getLogger(XchangeRate.class);
    final static String mimeType = "text/json";

    public void handle(HttpExchange httpExchange) throws IOException {
        /*
         * http://localhost:9099/report?report=ok 上述查询的 query = "report=ok"
         */
        String query = httpExchange.getRequestURI().getQuery();
        // QueryExecuteThreadPool.initCustomerPool();

        String params = HttpUtil.parseUrlParams(query).get("params");
        
        logger.info("[" + httpExchange.getRequestMethod() + "] Request: " + query);
        System.out.println(params);
        
        String response = "";
        
        // String sql = "SELECT * FROM YAHOO_XCHANGE_RATE";
//        String sql = "SELECT %s FROM %s";
        H2DBDao h2dbDao = H2DBDao.getInstance();
        // h2dbDao.setTableName("YAHOO_XCHANGE_RATE");
        try {
            QueryParam queryParam = HttpUtil.parseParams(params);
            
            QueryType queryType = queryParam.getQuery_type();
            String[] columNames = queryParam.getColums();
            
            List<YAHOO_XCHANGE_RATE> yxrs = new ArrayList<YAHOO_XCHANGE_RATE>();
            boolean flag = true;
            String tableName = (!Strings.isNullOrEmpty(h2dbDao.getTableName())) ? h2dbDao.getTableName() : "YAHOO_XCHANGE_RATE";
            if (queryType.toString().equalsIgnoreCase("all")) {
            	String[] colums = {"ID","CURRENCY_FROM","CURRENCY_TO","RATE_FROM_TO","RATE_USD_TO","IS_DELETED","LAST_UPDATE_TIME"};
            	columNames = colums;
            	ResultSet resultSet = h2dbDao.doQuery("*", tableName);
                yxrs.addAll(parser(resultSet, columNames));
            }else if (queryType.toString().equalsIgnoreCase("colums") || queryType.toString().equalsIgnoreCase("colum")) {
				if (columNames.length > 0) {
					StringBuilder builder = new StringBuilder();
					for (String colum : columNames) {
						builder.append(colum.toUpperCase()).append(",");
					}
	                ResultSet resultSet = h2dbDao.doQuery(builder.toString().substring(0, builder.length() - 1), tableName);
	                yxrs.addAll(parser(resultSet, columNames));
				}else {
					flag = false;
				}
			}
            
            if (flag) {
            	QueryResult result = new QueryResult();
            	result.setFlag(MsgType.info.toString());
            	result.setMsg("ok");
            	result.setRates(yxrs);
            	response = new Gson().toJson(result, QueryResult.class);
				
			}else {
				response = new Gson().toJson(new QueryResult(MsgType.file.toString(), "query parameter `colums` can not be blank.", null), QueryResult.class);
			}
        } catch (EBJException e) {
            String msg = e.getMessage();
            logger.error(msg);
            response = new Gson().toJson(new QueryResult(MsgType.file.toString(), msg, null), QueryResult.class);
        }
        

        // Headers responseHeaders = httpExchange.getResponseHeaders();
        // responseHeaders.set("Content-Type", mimeType);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    
    private List<YAHOO_XCHANGE_RATE> parser(ResultSet resultSet, String[] colums) {
    	List<YAHOO_XCHANGE_RATE> yxrs = null;
		YAHOO_XCHANGE_RATE yxr = null;
		if (null != resultSet && colums.length > 0) {
			yxrs = new ArrayList<YAHOO_XCHANGE_RATE>();
			try {
				while (resultSet.next()) {
					yxr = new YAHOO_XCHANGE_RATE();
					
					for (String colum : colums) {
						String value = resultSet.getObject(colum).toString();
						switch (colum.toLowerCase()) {
						case "id":
                            yxr.setId(Integer.valueOf(value));
                            break;
                        case "currency_from":
                            yxr.setCurrency_from(value);
                            break;
                        case "currency_to":
                            yxr.setCurrency_to(value);
                            break;
                        case "rate_from_to":
                            yxr.setRate_from_to(Double.valueOf(value));
                            break;
                        case "rate_usd_to":
                            yxr.setRate_usd_to(Double.valueOf(value));
                            break;
                        case "is_deleted":
                            yxr.setIs_deleted(Integer.valueOf(value));
                            break;
                        case "last_update_time":
                            yxr.setLast_update_time(value);
                            break;
                        default:
                            break;
						}
					}
					yxrs.add(yxr);
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		
		return yxrs;
	}
}
