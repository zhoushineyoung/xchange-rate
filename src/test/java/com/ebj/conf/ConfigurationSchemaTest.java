package com.ebj.conf;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static java.math.BigDecimal.*;
import static java.util.Arrays.*;

import com.pholser.util.properties.PropertyBinder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.ebj.conf.EnumTypeDemo.*;
import static org.junit.Assert.*;

public class ConfigurationSchemaTest {
    private static ConfigurationSchema bound;

    @Before public void initializeFixture() throws Exception {
        PropertyBinder<ConfigurationSchema> binder = PropertyBinder.forType(ConfigurationSchema.class);
        bound = binder.bind(new File("src/test/resources/example.properties"));
    }

    @Test public void bindingToPropertyWithMethodName() {
        assertEquals("no conversion", bound.unadorned());
    }

    @Test public void bindingToPropertyWithAnnotation() {
        assertEquals("also no conversion", bound.annotated());
    }

    @Test public void convertingPrimitivePropertyValues() {
        assertEquals(2, bound.intProperty());
    }

    @Test public void convertingWrappedPrimitivePropertyValues() {
        assertEquals(Long.valueOf(-1L), bound.wrappedLongProperty());
    }

    @Test public void convertingCommaSeparatedValuedPropertyToArray() {
        assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c' }, bound.charArrayProperty()));
    }

    @Test public void convertingCommaSeparatedValuedPropertyToList() {
        assertEquals(asList('d', 'e', 'f'), bound.charListProperty());
    }

    @Test public void honoringDifferentSeparatorsForAggregateProperties() {
        assertEquals(asList(YES, NO, YES, MAYBE), bound.listOfEnumsWithSeparator());
    }

    @Test public void honoringDefaultValueIndicationWhenPropertyNotPresent() {
        assertEquals(TEN, bound.bigDecimalPropertyWithDefault());
    }

    @Test public void honoringDateFormatSpecificationsForDateProperties() throws Exception {
        assertEquals(MMddyyyy("02/14/2010"), bound.dateProperty());
    }

    @Test public void formattingPropertiesCorrespondingToMethodsWithArguments() throws Exception {
        assertEquals("10 seconds to 12:00:00 AM", bound.argsProperty(10, MMddyyyy("01/01/2011")));
    }

    private static Date MMddyyyy(String raw) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(raw);
    }
    
    @Ignore public static void myTest(){
    }
    
    public static void main(String[] args) {
        File file = new File("src/main/resources/config.properties");
        PropertyBinder<ConfigurationSchema> binder = PropertyBinder.forType(ConfigurationSchema.class);
        try {
            bound = binder.bind(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(bound.getYahooExcgRtAPI());
        System.out.println(new File("").getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
    }
}
