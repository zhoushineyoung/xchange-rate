package com.ebj.domain.yahooxchange;

public class Row {
	/*
	 * {"id":"USDCNY","Name":"USD to CNY","Rate":"6.2095",
	 * "Date":"6/14/2014","Time":"7:23am","Ask":"6.2145",
	 * "Bid":"6.2045"}
	 */
	private String id;
	private String Name;
	// private String Rate;
	private double Rate;
	private String Date;
	private String Time;
	private String Ask;
	private String Bid;
	
	public Row() {}
	public Row(String id, String name, double rate, String date, String time, String ask, String bid) {
        this.id = id;
        Name = name;
        Rate = rate;
        Date = date;
        Time = time;
        Ask = ask;
        Bid = bid;
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getRate() {
        return Rate;
    }
    public void setRate(double rate) {
        Rate = rate;
    }
    public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getAsk() {
		return Ask;
	}
	public void setAsk(String ask) {
		Ask = ask;
	}
	public String getBid() {
		return Bid;
	}
	public void setBid(String bid) {
		Bid = bid;
	}
}
