package com.ebj.domain;

import java.util.List;


public class QueryResult{
    private String flag;
    private String msg;
    private List<YAHOO_XCHANGE_RATE> rates;
    
    public QueryResult() {}

    public QueryResult(String flag, String msg, List<YAHOO_XCHANGE_RATE> rates) {
        super();
        this.flag = flag;
        this.msg = msg;
        this.rates = rates;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<YAHOO_XCHANGE_RATE> getRates() {
        return rates;
    }
    public void setRates(List<YAHOO_XCHANGE_RATE> rates) {
        this.rates = rates;
    }
}
