package com.ebj.test;

import java.util.Arrays;
import java.util.Set;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class Test {
    public static void main(String[] args) {
    	String tmpString = "USD,JPY,BGN,CZK,DKK,GBP,HUF,LTL,LVL,PLN,RON,SEK,CHF,NOK,HRK,RUB,TRY,AUD,BRL,CAD,CNY,HKD,IDR,ILS,INR,KRW,MXN,MYR,NZD,PHP,SGD,THB,ZAR,EUR,EUR,USD";
    	String tmpString2 = "HKD,MOP,CNY,KPW,VND,JPY,LAK,KHR,PHP,MYR,SGD,THP,BUK,LKR,MVR,IDR,PRK,INR,NPR,AFA,IRR,IQD,SYP,LBP,JOD,SAR,KWD,BHD,QAR,OMR,YER,YDD,TRL,CYP,EUR,ISK,DKK,NOK,SEK,FIM,SUR,PLZ,CSK,HUF,DEM,ATS,CHF,NLG,BEF,LUF,GBP,IEP,FRF,ESP,PTE,ITL,MTP,YUD,ROL,BGL,ALL,GRD,CAD,USD,MXP,GTQ,SVC,HNL,NIC,CRC,PAB,CUP,BSD,JMD,HTG,DOP,TTD,BBD,COP,VEB,GYD,SRG,PES,ECS,BRC,BOP,CLP,ARP,PYG,UYP,EGP,LYD,SDP,TND,DZD,MAD,MRO,XOF,XOF,XOF,XOF,XOF,XOF,GMD,GWP,GNS,SLL,LRD,GHC,NGN,XAF,XAF,XAF,XAF,XAF,GQE,ZAR,DJF,SOS,KES,UGS,TZS,RWF,BIF,ZRZ,ZMK,MCF,SCR,MUR,ZWD,KMF,AUD,NZD,FJD,SBD";
        ConfigurationSchema config = ConfigManager.getInstance().getConfig();
        String currenciesName = config.getCurrenciesName();
        String[] names = currenciesName.split(",");
        System.out.println(names.length);
        System.out.println(Arrays.toString(names));
        Set<String> nameSet = Sets.newHashSet(names);
        System.out.println("config.name.nums = " + nameSet.size());
        System.out.println("********************");
        System.out.println(tmpString.split(",").length);
        Set<String> nameSet2 = Sets.newHashSet(tmpString.split(","));
        System.out.println("tmpString.name.nums = " + nameSet2.size());
        
        System.out.println(tmpString2.split(",").length);
        Set<String> nameSet3 = Sets.newHashSet(tmpString2.split(","));
        System.out.println("tmpString2.name.nums = " + nameSet3.size());
        
        System.out.println("********difference********");
        SetView<String> diff = Sets.difference(nameSet, nameSet2);
        System.out.println(diff.size());
        System.out.println("********union********");
        SetView<String> union = Sets.union(nameSet, nameSet2);
        System.out.println(union.size());
        System.out.println("********intersection********");
        SetView<String> inter = Sets.intersection(nameSet, nameSet2);
        System.out.println(inter.size());
        
        System.out.println("**********************");
        SetView<String> union2 = Sets.union(union, nameSet3);
        System.out.println(union2.size());
        
        System.out.println(Joiner.on(",").join(union2));
    }
}
