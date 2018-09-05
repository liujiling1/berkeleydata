package com.test.berkeleydata.service;

import java.io.UnsupportedEncodingException;

import com.sleepycat.je.DatabaseStats;
import com.sleepycat.je.OperationStatus;

public interface CrudStringService {
	
	public OperationStatus insertAndUpdateString(String k, String v) throws UnsupportedEncodingException ;
	
	public String selectString(String k) throws UnsupportedEncodingException ;
	
	public OperationStatus deleteString(String k) throws UnsupportedEncodingException ;

	public Object selectObj(String k);

	public OperationStatus insertAndUpdateObj(String k, Object v);
	
	public DatabaseStats getStats();

}
