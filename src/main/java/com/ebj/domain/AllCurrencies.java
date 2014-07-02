package com.ebj.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;

public class AllCurrencies {
	private static Set<String> currencyNames = null;
	private static String[] allCurrencyName = null;
	private static String[] codes = {"USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "LTL",
		"LVL", "PLN", "RON", "SEK", "CHF", "NOK", "HRK", "RUB",
		"TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS",
		"INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB",
		"ZAR", "EUR", "EUR", "USD"};
	
	static{
		// 读取配置文件，动态加载java代码片段  eg. static String[] codes = {"USD", "JPY", "BGN", "CZK"...}
//	    ConfigurationSchema config = ConfigManager.getInstance().getConfig();
//        dataTableHost = config.getH2ServerHost();
//        dataTablePort = config.getH2ServerPort();

	    currencyNames = new HashSet<String>(Arrays.asList(codes));
	}
	
	public static void setCurrencyNames(String ...names) {
	    codes = names;
		if (codes.length > 0) {
			for (String string : codes) {
				System.out.println(string.getClass().getName() + " - " + string.toString());
			}
			currencyNames = new HashSet<String>(Arrays.asList(codes));
		}
	}
	
	public static String[] getAllCurrencyNames() {
		int m = currencyNames.size();
		if (m > 0) {
			allCurrencyName = currencyNames.toArray(new String[0]);
			return allCurrencyName;
		}
		return null;
	}
	
	public static void main(String[] args) {
//	    System.out.println(codes.length);
//		Set<String> names = new HashSet<String>(Arrays.asList(codes));
//		System.out.println(names.size());
//		String[] newNames = names.toArray(new String[0]);
//		System.out.println(newNames.length);
//		for (String string : newNames) {
//            System.out.print(string + "-");
//        }
	    for (String string : AllCurrencies.getAllCurrencyNames()) {
            System.out.println(string);
        }
	    AllCurrencies.setCurrencyNames(new String[]{"ABC","DEF","HIJ"});
	    for (String string : AllCurrencies.getAllCurrencyNames()) {
            System.out.println(string);
        }
	    
	    String[] newArr2 = {"KKK","LLL","MMM"};
	    AllCurrencies.setCurrencyNames(newArr2);
	    for (String string : AllCurrencies.getAllCurrencyNames()) {
	        System.out.println(string);
	    }
	}
}
