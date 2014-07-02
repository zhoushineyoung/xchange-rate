package com.ebj.domain;


public class YAHOO_XCHANGE_RATE {
    private Integer id ;
    private String currency_from;
    private String currency_to;
    private Double rate_from_to;
    private Double rate_usd_to ;
    private Integer is_deleted ;
    private String last_update_time;
    
    public YAHOO_XCHANGE_RATE() {}
	public YAHOO_XCHANGE_RATE(int id, String currency_from, String currency_to,
			Double rate_from_to, Double rate_usd_to, int is_deleted,
			String last_update_time) {
		this.id = id;
		this.currency_from = currency_from;
		this.currency_to = currency_to;
		this.rate_from_to = rate_from_to;
		this.rate_usd_to = rate_usd_to;
		this.is_deleted = is_deleted;
		this.last_update_time = last_update_time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrency_from() {
		return currency_from;
	}
	public void setCurrency_from(String currency_from) {
		this.currency_from = currency_from;
	}
	public String getCurrency_to() {
		return currency_to;
	}
	public void setCurrency_to(String currency_to) {
		this.currency_to = currency_to;
	}
	public Double getRate_from_to() {
		return rate_from_to;
	}
	public void setRate_from_to(Double rate_from_to) {
		this.rate_from_to = rate_from_to;
	}
	public Double getRate_usd_to() {
		return rate_usd_to;
	}
	public void setRate_usd_to(Double rate_usd_to) {
		this.rate_usd_to = rate_usd_to;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
}
