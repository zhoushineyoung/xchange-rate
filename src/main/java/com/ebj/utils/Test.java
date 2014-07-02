package com.ebj.utils;

import java.util.Arrays;
import java.util.Set;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Test {
    public static void main(String[] args) {
        ConfigurationSchema config = ConfigManager.getInstance().getConfig();
        String currenciesName = config.getCurrenciesName();
        String[] names = currenciesName.split(",");
        System.out.println(names.length);
        System.out.println(Arrays.toString(names));
        Set<String> nameSet = Sets.newHashSet(names);
        System.out.println(nameSet.size());
        
        for (String string : nameSet) {
            if (Lists.newArrayList(names).contains(string)) {
                // ...
            }else {
                System.out.println(string);
            }
        }
    }
}
