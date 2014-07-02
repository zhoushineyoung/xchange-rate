package com.ebj.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class NoneTest {
    
    @Test
    public void name() {
    }
    
    public static void main(String[] args) {
        
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // every 00:00:00 and 12:00:00
        calendar.set(year, month, day, 0, 0);
        
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss").format(calendar.getTime()));
        System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // every 00:00:00 and 12:00:00
                calendar.set(year, month, day, 0, 0);
                System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss").format(calendar.getTime()));
            }
        }, calendar.getTime(), 2000);
        
    }
}
