package com.ebj.conf;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.pholser.util.properties.PropertyBinder;

/**
 * <p>读取 *.properties 配置信息</p>
 * @since V1.0
 * @Author Martin
 * @createTime 2014年6月12日 下午5:55:49
 * @modifiedBy name
 * @modifyOn dateTime
 */
public class ConfigManager {
    private static Logger logger = Logger.getLogger(ConfigManager.class);
    
    private String filePath = "src/main/resources/config.properties";
    private static ConfigurationSchema config = null;
    private PropertyBinder<ConfigurationSchema> binder = PropertyBinder.forType(ConfigurationSchema.class);
    
    private ConfigManager() {}
    
    private ConfigManager(String filePath) {
        this.filePath = filePath;
    }
    public static ConfigManager getInstance() {
		return new ConfigManager();
	}
    public static ConfigManager getInstance(String filePath) {
		return new ConfigManager(filePath);
	}
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    /**
     * 
     * <p>读取指定的配置文件</p>
     * @since V1.0
     * @Author Martin
     * @createTime 2014年6月12日 下午5:56:30
     * @modifiedBy name
     * @modifyOn dateTime
     * @param filePath
     * @return
     */
    public ConfigurationSchema getConfig(String filePath) {
        try {
            config = binder.bind((!Strings.isNullOrEmpty(filePath)) ? new File(filePath) : new File(this.filePath));
        } catch (IOException e) {
            logger.info("", e);
        }
        return config;
    }
    
    /**
     * <p>读取默认配置文件 config.properties</p>
     * @since V1.0
     * @Author Martin
     * @createTime 2014年6月12日 下午6:22:27
     * @modifiedBy name
     * @modifyOn dateTime
     * @return
     */
    public ConfigurationSchema getConfig() {
        if (null == config) {
            try {
                config = binder.bind(new File(this.filePath));
            } catch (IOException e) {
                logger.info("", e);
            }
        }
        return config;
    }
}
