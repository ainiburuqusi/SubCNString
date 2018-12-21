package com.lianger.submessage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lianger.submessage.context.Context;
import com.lianger.submessage.handler.BaseSubHandler;

@RestController
@RequestMapping(value="/",produces = "application/json;charset=UTF-8")
public class ClientController {
	
	private static final Logger logger =LoggerFactory.getLogger(ClientController.class);

	@Value("${manager.id}")
	private String managerId;
	
	/**
	 * 
	 * @param resType 需要返回的类型分词类型
	 * 							str ：以字符串返回分词结果，以"|"分隔词组
	 * 							arr : 以数组返回分词结果
	 * @param question 
	 * @return
	 */
	@RequestMapping(value="getChars",method=RequestMethod.POST)
	public String getCharts(@RequestBody(required=true)Map params){
		String question = MapUtils.getString(params, "question", "");
		String resType = MapUtils.getString(params, "resType", "str");
		Map<String, Object> resMap = new HashMap<String, Object>();
		switch (resType) {
		case "str":
			String res = BaseSubHandler.getInstance().subStringToSymBol(question);
			if (res != null && res.length() > 0) {
				resMap.put("retcode", "0");
				resMap.put("message", res);
			}
			break;
		case "arr":
			List<String> resList = BaseSubHandler.getInstance().subStringToList(question);
			if (resList != null && resList.size() > 0) {
				resMap.put("retcode", "0");
				resMap.put("message", resList);
			}
			break;
		default:
			resMap.put("retcode", "1");
			resMap.put("message", "ERROR");
			break;
		}
		
		return JSON.toJSONString(resMap);
	}
	/**
	 * 
	 * @param managerId 配置文件中的manager.id
	 * @param {"chars":"张亮最帅"}
	 * @return
	 */
	@RequestMapping(value="addChars",method=RequestMethod.PUT)
	public String addChars(@RequestHeader(value="managerId",required=true)String managerId,@RequestBody(required=true) Map params){
		Map<String, Object> resMap = new HashMap<String, Object>();
		String chars = MapUtils.getString(params, "chars", "");
		if (managerId.equals(this.managerId)) {
			if (Context.put(chars)) {
				resMap.put("retcode", "0");
				resMap.put("message", "SUCCESS");
			}else{
				resMap.put("retcode", "1");
				resMap.put("message", "chars exists");
			}
		}else{
			resMap.put("retcode", "1");
			resMap.put("message", "put managerId to request header");
		}
		return  JSON.toJSONString(resMap);
	}
	@RequestMapping(value="removeChars",method=RequestMethod.DELETE)
	public String removeChars(@RequestHeader(value="managerId",required=true)String managerId,@RequestBody(required=true) Map params){
		Map<String, Object> resMap = new HashMap<String, Object>();
		String chars = MapUtils.getString(params, "chars", "");
		if (managerId.equals(this.managerId)) {
			if (Context.remove(chars)) {
				resMap.put("retcode", "0");
				resMap.put("message", "SUCCESS");
			}else{
				resMap.put("retcode", "1");
				resMap.put("message", "chars not exists");
			}
		}else{
			resMap.put("retcode", "1");
			resMap.put("message", "put managerId to request header");
		}
		return  JSON.toJSONString(resMap);
	}
}
