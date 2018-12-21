package com.lianger.submessage.handler;

import java.util.List;

public abstract class AbstractSubHandler {

	protected  String[] punctuation = new String []{",","\\.","\\?",":",";","，","。","？","；","\"","、","”","“"};
	
	public abstract String[] subStringByPunctuation(String message);

	public abstract String[] subStringArrayByChars(String subMsg);
	
	public abstract String subStringToSymBol(String question);
	
	public abstract List subStringToList(String question);
	
}
