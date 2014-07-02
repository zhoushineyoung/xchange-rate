package com.ebj.domain;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class QueryParamTest {

    String query_param = ""; 
    @Before
    public void setUp() throws Exception {
        query_param = "{\"query_type\":\"colums\",\"return_format\":\"json\",\"query_id\":\"88e771bc-a151-44dd-afa2-9fcc056e9eeb\",\"colums\":[\"id\",\"currency_from\",\"currency_to\",\"rate_from_to\",\"rate_usd_to\",\"isdeleted\",\"last_update_time\"]}";
    }

    @Test
    public void testQueryParamTo() {
        QueryParam queryParam = new QueryParam();
        queryParam.setQuery_type(QueryType.colums);
        queryParam.setReturn_format(ReturnFormat.json);
        queryParam.setQuery_id(UUID.randomUUID().toString());
        queryParam.setColums(new String[]{"ID","CURRENCY_FROM","CURRENCY_TO","RATE_FROM_TO","RATE_USD_TO","ISDELETED","LAST_UPDATE_TIME"});
        
        System.out.println(new Gson().toJson(queryParam, QueryParam.class));
    }
    
    @Test
    public void testQueryParamRrom() {
        QueryParam queryParam = new Gson().fromJson(query_param, QueryParam.class);
        System.out.println(queryParam.getQuery_id());
        System.out.println(Arrays.toString(queryParam.getColums()).toUpperCase());
        System.out.println(queryParam.getQuery_type());
        System.out.println(queryParam.getReturn_format());
    }

}
