package com.bewitchment.common.core.helper;

import com.bewitchment.common.Bewitchment;

public class Log {
	
	public static void i(String s) {
		Bewitchment.logger.info(s);
	}
	
	public static void w(String s) {
		Bewitchment.logger.warn(s);
	}
	
	public static void e(String s) {
		Bewitchment.logger.error(s);
	}
	
	public static void d(String s) {
		if ("true".equals(System.getProperty("debug"))) {
			i("[DEBUG] -- "+s);
		} else {
			Bewitchment.logger.debug(s);
		}
	}
}
