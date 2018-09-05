package com.test.berkeleydata.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

@Configuration
public class EvnConfig {
	
	@Value("${berkeleydata.catalog}")
	private String catalog;
	
	@Value("${berkeleydata.dbName}")
	private String dbName;

    @Bean
	public Environment initEnv() {
		
		// 创建一个EnvironmentConfig配置对象
		EnvironmentConfig envCfg = new EnvironmentConfig();
		envCfg.setAllowCreate(true);// 如果设置了true则表示当数据库环境不存在时候重新创建一个数据库环境，默认为false.
		envCfg.setCacheSize(1024 * 1024 * 20);// 设置数据库缓存大小
		envCfg.setTransactional(true);
		
		return new Environment(new File(catalog), envCfg);
	}
    
    @Bean
	public Database creatDatabase(Environment evn) {

		DatabaseConfig dbCfg = new DatabaseConfig();
		dbCfg.setAllowCreate(true);// 如果数据库不存在则创建一个
		dbCfg.setTransactional(true);

		return evn.openDatabase(null, dbName, dbCfg);
	}

}
