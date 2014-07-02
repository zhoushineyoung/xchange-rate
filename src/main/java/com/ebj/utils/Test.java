package com.ebj.utils;

import java.util.Arrays;
import java.util.Set;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class Test {
    public static void main(String[] args) {
    	String tmpString = "USD,JPY,BGN,CZK,DKK,GBP,HUF,LTL,LVL,PLN,RON,SEK,CHF,NOK,HRK,RUB,TRY,AUD,BRL,CAD,CNY,HKD,IDR,ILS,INR,KRW,MXN,MYR,NZD,PHP,SGD,THB,ZAR,EUR,EUR,USD";
    	
        ConfigurationSchema config = ConfigManager.getInstance().getConfig();
        String currenciesName = config.getCurrenciesName();
        String[] names = currenciesName.split(",");
        System.out.println(names.length);
        System.out.println(Arrays.toString(names));
        Set<String> nameSet = Sets.newHashSet(names);
        System.out.println(nameSet.size());
        System.out.println("********************");
        System.out.println(tmpString.split(",").length);
        Set<String> nameSet2 = Sets.newHashSet(tmpString.split(","));
        System.out.println(nameSet2.size());
        
        SetView<String> diff = Sets.difference(nameSet, nameSet2);
        System.out.println(diff.size());
        SetView<String> union = Sets.union(nameSet, nameSet2);
        System.out.println(union.size());
        SetView<String> inter = Sets.intersection(nameSet, nameSet2);
        System.out.println(inter.size());
        
    }
}
