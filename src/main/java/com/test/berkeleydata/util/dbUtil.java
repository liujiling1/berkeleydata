package com.test.berkeleydata.util;

import org.springframework.beans.factory.annotation.Autowired;
import com.sleepycat.je.Database;
import com.sleepycat.je.Environment;

public class dbUtil {
	
	@Autowired
	Environment environment;
	
	@Autowired
	Database database;

	public void close() {
		if (null != database) {
			database.close();
		}
		if (null != environment) {
			environment.cleanLog();// 在关闭环境前清理下日志
			environment.close();
			environment = null;
		}
	}
	
}