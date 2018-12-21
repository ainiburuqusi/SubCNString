package com.lianger.submessage.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lianger.submessage.context.Context;


public class BaseSubHandler extends AbstractSubHandler {

	private static byte[] lock =new byte[0x0];
	
	private static BaseSubHandler bsh = null;
	
	private BaseSubHandler(){}
	
	public static BaseSubHandler getInstance(){
		synchronized(lock){
			if (bsh == null) {
				bsh = new BaseSubHandler();
			}
		}
		return bsh;
	}

	@Override
	public String[] subStringByPunctuation(String message) {
		// TODO Auto-generated method stub
		for(int i=0;i<punctuation.length;i++){
			message=message.replaceAll(punctuation[i], "\\|\\|\\|");
		}
		return message.split("\\|\\|\\|");
	}

	@Override
	public String[] subStringArrayByChars(String subMsg) {
		// TODO Auto-generated method stub
		if (subMsg.length() < 2) {
			return new String[]{subMsg};
		}else{
			StringBuffer buffer = new StringBuffer();
			subMsgToChars(buffer,subMsg);
			return buffer.toString().split("\\|\\|");
		}
	}

	private void subMsgToChars(StringBuffer buffer,String subMsg) {
		// TODO Auto-generated method stub
		if (subMsg == null )  return;
		int len = subMsg.length();
		String newMsg = null;
		if ( len <= 1) {
			buffer.append(subMsg.substring(0,len));
		}else{
			for(int i=subMsg.length();i>=0;i--){
				String ss = subMsg.substring(0, i);
				if (Context.hasChars(ss)) {
					buffer.append(ss);
					buffer.append("||");
					newMsg = subMsg.substring(i,subMsg.length());
					break;
				}
			}
			subMsgToChars(buffer, newMsg);
		}
	}

	@Override
	public String subStringToSymBol(String question) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		String[] sbp = subStringByPunctuation(question);
		for (int i=0;i<sbp.length;i++) {
			String[] chars = subStringArrayByChars(sbp[i]);
			for (int j=0;j<chars.length;j++) {
				buffer.append(chars[j]);
				buffer.append("|");
			}
		}
		return buffer.toString().substring(0, buffer.length()-1);
	}

	@Override
	public List subStringToList(String question) {
		// TODO Auto-generated method stub
		String str = subStringToSymBol(question);
		String[] chars = str.split("\\|");
		 return Arrays.asList(chars);
	}

	/*public static void main(String[] args) {
		Context.init();
		String[] s = BaseSubHandler.getInstance().subStringArrayByChars("请问信用卡如何办理分期");
		for (String string : s) {
			System.out.println(string);
		}
	}*/
}
