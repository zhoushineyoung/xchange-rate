package com.ebj.service;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.ebj.domain.MsgType;
import com.ebj.domain.QueryResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class ServerStatus implements HttpHandler{
    private static Logger logger = Logger.getLogger(ServerStatus.class);
    // final static String mimeType = "text/json";
    // final static String mimeType = "text/csv";
    final static String mimeType = "file";
    
    public void handle(HttpExchange httpExchange) throws IOException {
        String query = httpExchange.getRequestURI().getQuery();
        logger.info("query: " + query);
        // Message message = new Message(MSGCode.NDP_MSG_TYPE_INFO, MSGCode.NDP_MSG_CODE_SERVER_OK, MSGCode.NDP_MSG_SERVER_OK);
        // String response = XMLUtils.msg2XML(message);
        QueryResult result = new QueryResult();
        result.setFlag(MsgType.info.toString());
        result.setMsg("Server is running.");
        result.setRates(null);
                
        String response = new Gson().toJson(result, QueryResult.class);
        
        // 响应包含文件下载时，设置下面2行
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", mimeType);
        responseHeaders.set("Content-Disposition","attachment; filename=\""+ new String(("filename_prefix" + "-" + "filename_suffix" + ".csv").getBytes("utf-8"),"utf-8") + "\"");
        
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

}
