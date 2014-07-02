package com.ebj.domain;

public class QueryParam {
    private QueryType query_type;
    private ReturnFormat return_format;
    private String query_id;
    private String[] colums;
    
    public QueryType getQuery_type() {
        return query_type;
    }
    public void setQuery_type(QueryType query_type) {
        this.query_type = query_type;
    }
    public ReturnFormat getReturn_format() {
        return return_format;
    }
    public void setReturn_format(ReturnFormat return_format) {
        this.return_format = return_format;
    }
    public String getQuery_id() {
        return query_id;
    }
    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }
    public String[] getColums() {
        return colums;
    }
    public void setColums(String[] colums) {
        this.colums = colums;
    }
}
