package com.ebj.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ebj.domain.QueryParam;
import com.ebj.exception.EBJException;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gson.Gson;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	public static Map<String, String> parseUrlParams2(String query){
        if (Strings.isNullOrEmpty(query)) return null;
        try {
            Map<String, String> params = Splitter.on('&').trimResults().withKeyValueSeparator('=').split(query);
            return params;
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        }
        return null;
    }
	
	public static Map<String, String> parseUrlParams(String query){
        if (Strings.isNullOrEmpty(query)) return null;
        try {
            Splitter splitter =  Splitter.on('&').trimResults();
            List<String> list = splitter.splitToList(query);
            Map<String, String> params = new HashMap<String, String>();
            String[] tmpArr = new String[2];
            for (int i = 0; i < list.size(); i++) {
            	String string = list.get(i);
            	if (!string.contains("=")) {
            		tmpArr = (list.get(i - 1) + " & " + string).split("=");
            	}else {
            		tmpArr = (string).split("=");
				}
            	params.put(tmpArr[0], tmpArr[1]);
			}
            return params;
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        }
        return null;
    }
	
	public static QueryParam parseParams(String query_params) throws EBJException {
	    QueryParam queryParam = null;
	    try {
	        queryParam = new Gson().fromJson(query_params, QueryParam.class);
        } catch (Exception e) {
            throw new EBJException("QueryParameters Parse Error :" + e.getMessage());
        }
	    return queryParam;
    }
}
