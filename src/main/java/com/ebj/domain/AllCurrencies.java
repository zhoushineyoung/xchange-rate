package com.ebj.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

public class AllCurrencies {
    private static Set<String> currenciesNameSet = null;
    private static String[] allCurrencyName = null;
    private static String[] codes = {
            "CNY", "LVL", "JPY", "RON", "CZK", "MXN", "CAD", "ZAR", "NZD", "AUD", "GBP", "ILS", "NOK", "CHF", "RUB", "INR", "THB", "IDR", "TRY", "SGD", "HKD",
            "LTL", "HRK", "DKK", "MYR", "SEK", "BRL", "EUR", "BGN", "PHP", "HUF", "PLN", "USD", "KRW" };

    static {
        // load currency-type configuration from properties
        ConfigurationSchema config = ConfigManager.getInstance().getConfig();
        Iterable<String> currenciesName = Splitter.on(",").omitEmptyStrings().split(config.getCurrenciesName());
        if (null != currenciesName) {
            currenciesNameSet = Sets.newHashSet(currenciesName);
        } else {
            currenciesNameSet = new HashSet<String>(Arrays.asList(codes));
        }
    }

    public static void setCurrencyNames(String... names) {
        codes = names;
        if (codes.length > 0) {
            for (String string : codes) {
                System.out.println(string.getClass().getName() + " - " + string.toString());
            }
            currenciesNameSet = new HashSet<String>(Arrays.asList(codes));
        }
    }

    public static String[] getAllCurrencyNames() {
        int m = currenciesNameSet.size();
        if (m > 0) {
            allCurrencyName = currenciesNameSet.toArray(new String[0]);
            return allCurrencyName;
        }
        return null;
    }
}
