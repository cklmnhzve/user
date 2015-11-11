package com.ldp.datahub.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类（使用：org.slf4j.Logger）
 * 
 * @author 兰剑辉
 * @since 2015/1/15
 *
 */
public class LogUtil {

	/**
	 * 日志Logger对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

	/**
	 * 获得日志Logger对象
	 * 
	 * @return
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	/**
	 * 记录异常日志（使用logger.error来记录的）
	 * 
	 * @param e
	 */
	public static void loggerException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
		} catch (Exception ex) {
			logger.info("System Logger Exception Error!");
		}
	}

}
