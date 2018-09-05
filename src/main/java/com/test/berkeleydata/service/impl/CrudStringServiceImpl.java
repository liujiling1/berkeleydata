package com.test.berkeleydata.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseStats;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.test.berkeleydata.service.CrudStringService;

@Service
public class CrudStringServiceImpl implements CrudStringService {
	
	@Autowired
	Database database;
	
	@Override
	public OperationStatus insertAndUpdateString(String k, String v) throws UnsupportedEncodingException {
		DatabaseEntry keyEntry = new DatabaseEntry(k.getBytes("utf-8"));
		DatabaseEntry valEntry = new DatabaseEntry(v.getBytes("utf-8"));
		return database.put(null, keyEntry, valEntry);
	}
	
	@Override
	public String selectString(String k) throws UnsupportedEncodingException {
		DatabaseEntry keyEntry = new DatabaseEntry(k.getBytes("utf-8"));
		DatabaseEntry valEntry4Get = new DatabaseEntry();
		OperationStatus status = database.get(null, keyEntry, valEntry4Get,LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(valEntry4Get.getData(), "utf-8");
		} else {
			return null;
		}
	}
	
	@Override
	public  OperationStatus deleteString(String k) throws UnsupportedEncodingException {
		DatabaseEntry keyEntry = new DatabaseEntry(k.getBytes("utf-8"));
		return database.delete(null, keyEntry);
	}
	
	@Override
	public OperationStatus insertAndUpdateObj(String k, Object v){

		ObjectOutputStream oos = null;
		try {
			DatabaseEntry beanKeyEntry = new DatabaseEntry(k.getBytes("utf-8"));
			DatabaseEntry beanValEntry = new DatabaseEntry();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(v);
			beanValEntry.setData(baos.toByteArray());
			return database.put(null, beanKeyEntry, beanValEntry);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != oos) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Object selectObj(String k) {

		ObjectInputStream ois = null;
		try {
			DatabaseEntry beanKeyEntry = new DatabaseEntry(k.getBytes("utf-8"));
			DatabaseEntry valEntry4Get = new DatabaseEntry();
			
			OperationStatus status = database.get(null, beanKeyEntry, valEntry4Get, LockMode.DEFAULT);
			if (status == OperationStatus.SUCCESS) {
				ByteArrayInputStream bais = new ByteArrayInputStream(valEntry4Get.getData());
				try {
					ois = new ObjectInputStream(bais);
					return ois.readObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != ois) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	
	public DatabaseStats getStats() {
		return database.getStats(null);
	}
}