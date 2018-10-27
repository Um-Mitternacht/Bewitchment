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

	public static void i(Object s) {
		Bewitchment.logger.info(s);
	}

	public static void w(Object s) {
		Bewitchment.logger.warn(s);
	}

	public static void e(Object s) {
		Bewitchment.logger.error(s);
	}

	public static void askForReport() {
		StringBuilder sb = new StringBuilder();
		String s = "This is a bug in the mod Bewitchment. Update it or report it if already using the latest version";
		for (int i = 0; i < s.length() + 10; i++) {
			sb.append("#");
		}
		String frame = sb.toString();
		e(frame);
		e("#    " + s + "    #");
		e(frame);
		Thread.dumpStack();
	}

	public static void d(String s) {
		if ("true".equals(System.getProperty("debug"))) {
			i("[DEBUG] -- " + s);
		} else {
			Bewitchment.logger.debug(s);
		}
	}
}
