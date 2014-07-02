package com.ebj.conf;

import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ConfigKeyConst {
	public static final String DATETIME_FORMAT_STYLE_YYYYMMDD = "datetime.format.style.yyyymmdd";
	public static final String DATETIME_FORMAT_STYLE_YYYYMMDDHHMM = "datetime.format.style.yyyymmddhhmm";
	public static final String DATETIME_FORMAT_STYLE_YYYYMMDDHHMMSS = "datetime.format.style.yyyymmddhhmmss";
	public static final String DATETIME_FORMAT_STYLE_YYYYMMDDHHMMSS_SSS = "datetime.format.style.yyyymmddhhmmss.sss";
	public static final String DATETIME_FORMAT_STYLE_YYYYMMDDTHHMMSS = "datetime.format.style.yyyyMMddTHHmmss";
	public static final String DATETIME_FORMAT_STYLE_DDMMMYYYYHHMMSSZ = "datetime.format.style.ddMMMyyyyHHmmssZ";
	
	public final static String S3_KEY_ACCESS = "ds.offline.keys.access";
    public final static String S3_KEY_SECRET = "ds.offline.keys.secret";
    public final static String S3_BUCKET_SRC = "ds.offline.bucket.src";
    public final static String S3_DIR_SRC = "ds.offline.dir.src";
    public final static String S3_BUCKET_DEST = "ds.offline.bucket.dest";
    public final static String S3_DIR_DEST = "ds.offline.dir.dest";
    
    public final static String FILE_SHORTNAME_LIST_FILE_PATH = "fshortnamelist.file.path";
    public final static String JSON_FORMAT_LOG_FILE_STOR_DST_PATH = "jsonformatlog.file.stor.dst.path";
    
    public final static String CHARACTER_ENCODING_CHARSET = "character.encoding.charset";

	/**
	 * set variable using given configuration unit
	 * 
	 * @param cfg
	 *            : configuration unit
	 * @param var
	 *            : original variable value
	 * @param key
	 *            : variable key in configuration unit
	 * @param fallback
	 *            : fallback value used when no config found if fallback ==
	 *            null, variable keeps its original value
	 */
	public static String parseStr(PropertiesConfiguration cfg, String var,
			String key, String fallback, Logger logger) {

		if (null == logger) {
			logger = Logger.getLogger("KeyConst.parseStr");
		}

		if (null == cfg) {
			logger.info("no config unit, use original(" + var + ")");
			return var;
		}

		String val = cfg.getString(key);
		// remove additional ";"
		val = StringUtils.removeEnd(val, ";");
		// if (StringUtil.isNotNullOrEmpty(val)) {
		if (StringUtils.isNotBlank(val)) {
			return val;
		} else {
			logger.info("No value of "
					+ key
					+ " in config file is found, using "
					+ ((fallback != null) ? "default(" + fallback : "original("
							+ var) + ") instead.");
			if (fallback != null) {
				return fallback;
			}
		}

		return var;
	}

	/**
	 * set variable using given configuration unit
	 * 
	 * @param cfg
	 *            : configuration unit
	 * @param var
	 *            : original variable value
	 * @param key
	 *            : variable key in configuration unit
	 * @param fallback
	 *            : fallback value used when no config found if fallback ==
	 *            null, variable keeps its original value
	 */
	public static int parseInt(PropertiesConfiguration cfg, int var,
			String key, String fallback, Logger logger) {
		if (null == logger) {
			logger = Logger.getLogger("KeyConst.parseInt");
		}

		if (null == cfg) {
			logger.info("no config unit, use original(" + var + ")");
			return var;
		}

		int val = var;
		if (cfg.containsKey(key)) {
			try {
				val = cfg.getInt(key);
			} catch (ConversionException e) {
				logger.error("The value of "
						+ key
						+ " in config file is incorrect, using "
						+ ((fallback != null) ? "default(" + fallback
								: "original(" + var) + ") instead.", e);
				if (fallback != null) {
					return Integer.parseInt(fallback);
				}
			}

		} else {
			logger.info("no value of "
					+ key
					+ " found in config file, using"
					+ ((fallback != null) ? "default(" + fallback : "original("
							+ var) + ") instead.");
			if (fallback != null) {
				return Integer.parseInt(fallback);
			}
		}

		return val;
	}
}
