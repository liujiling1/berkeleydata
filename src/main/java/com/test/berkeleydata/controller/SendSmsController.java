package com.test.berkeleydata.controller;	

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sleepycat.je.OperationStatus;
import com.test.berkeleydata.service.CrudStringService;

@RestController
@RequestMapping("/yjkj/berkeleydata")
public class SendSmsController {
	Logger logger = LoggerFactory.getLogger(SendSmsController.class);
   
	@Autowired
    private CrudStringService crudStringService;
   
	@RequestMapping("/insertString")
	public OperationStatus insertString(@RequestParam String k,@RequestParam String v) throws IOException {
		return crudStringService.insertAndUpdateString(k, v);
	}
    
    @RequestMapping("/selectString")
	public String selectString(@RequestParam String k) throws IOException {
		return crudStringService.selectString(k);
	}
    
    @RequestMapping("/insertObj")
	public OperationStatus insertObj(@RequestParam String k, @RequestParam Map<Object,Object> param) throws IOException {
		return crudStringService.insertAndUpdateObj(k,param);
	}
    
    @RequestMapping("/selectObj")
	public Object selectObj(@RequestParam String k) throws IOException {
		return crudStringService.selectObj(k);
	}
    
    @RequestMapping("/getStats")
	public Object getStats() throws IOException {
		return crudStringService.getStats();
	}
}
