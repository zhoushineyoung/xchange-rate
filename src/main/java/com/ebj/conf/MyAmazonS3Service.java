package com.ebj.conf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.gson.Gson;


public class MyAmazonS3Service {
	private static Logger logger = Logger.getLogger(MyAmazonS3Service.class);
	
	static String dst_dir = "F://access.log_json_format//M02//";
	static String accessKey = "AKIAIIRBULVIK2HAJF6A";
	static String secretKey = "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";
	static String srcBucket = "offline.ds.ymtech.com";
	static String srcDir = "logs/nginx";
	
	static String charset = "UTF-8";
	// static String destBucket = "";
	// static String destDir = "";
	
	AWSCredentials credentials = null;
	static AmazonS3 conn = null;
	
	public MyAmazonS3Service() {
		super();
		PropertiesConfiguration cfg = ConfigLoader.getInstance().getConfigurations();
		if (null != cfg) {
			dst_dir = ConfigKeyConst.parseStr(cfg, dst_dir, ConfigKeyConst.JSON_FORMAT_LOG_FILE_STOR_DST_PATH, null, logger);
			accessKey = ConfigKeyConst.parseStr(cfg, accessKey, ConfigKeyConst.S3_KEY_ACCESS, null, logger);
	        secretKey = ConfigKeyConst.parseStr(cfg, secretKey, ConfigKeyConst.S3_KEY_SECRET, null, logger);
	        srcBucket = ConfigKeyConst.parseStr(cfg, srcBucket, ConfigKeyConst.S3_BUCKET_SRC, null, logger);
	        srcDir = ConfigKeyConst.parseStr(cfg, srcDir, ConfigKeyConst.S3_DIR_SRC, null, logger);
	        
	        charset = ConfigKeyConst.parseStr(cfg, charset, ConfigKeyConst.CHARACTER_ENCODING_CHARSET, null, logger);
	        
	        credentials = new BasicAWSCredentials(accessKey, secretKey);
	        conn = new AmazonS3Client(credentials);
		}
	}

}
