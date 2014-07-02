package com.ebj.conf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pholser.util.properties.BoundProperty;
import com.pholser.util.properties.DefaultsTo;
import com.pholser.util.properties.ParsedAs;
import com.pholser.util.properties.ValuesSeparatedBy;

public interface ConfigurationSchema {
    String unadorned();

    @BoundProperty("unconverted.property")
    String annotated();
    
    @BoundProperty("exchange.rate.yahoo.api")
    String getYahooExcgRtAPI();
    
    
    @BoundProperty("tt")
    String getTT();

    int intProperty();
    
    @BoundProperty("ds.data.host")
    String getH2ServHost();
    @BoundProperty("ds.data.port")
    String getH2ServPort();
    @BoundProperty("ds.data.dbdir")
    String getH2DBDir();
    @BoundProperty("ds.data.table")
    String getH2Table();
    @BoundProperty("ds.data.db.user")
    String getH2DBUser();
    @BoundProperty("ds.data.db.password")
    String getH2DBPass();
    @BoundProperty("h2.sql.table.drop")
    String getSQLTmplTableDrop(String tableName);
    @BoundProperty("h2.sql.table.create")
    String getSQLTmplTableCreate(String tableName, String columsDefine);
    @BoundProperty("h2.sql.table.insert")
    String getSQLTmplInsertOrUpdate();
    @BoundProperty("h2.sql.table.query")
    String getSQLTmplQuery(String columsName, String tableName);

    @BoundProperty("exchange.currencies.name")
    String getCurrenciesName();
    
    Long wrappedLongProperty();

    char[] charArrayProperty();

    List<Character> charListProperty();

    @ValuesSeparatedBy(pattern = "\\s*,\\s*")
    List<EnumTypeDemo> listOfEnumsWithSeparator();

    @DefaultsTo(value = "10")
    BigDecimal bigDecimalPropertyWithDefault();

    @BoundProperty("date.property")
    @ParsedAs({"MM/dd/yyyy", "yyyy-MM-dd" })
    Date dateProperty();

    String argsProperty(int quantity, Date time);
}
