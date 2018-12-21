package com.lianger.submessage.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcesUtil {
	
	private static Properties pro = null;
	
	public static String getProperties(String name){
		Properties props = getPropertiesInCache();
		String value = props.getProperty(name); 
		return value;
	}
	
    public static Properties getPropertiesInCache() throws SecurityException {
    	if(pro == null){
    		pro = getProperties();
    	}
        return pro;
    }
    
    private static Properties getProperties(){
    	InputStream in = null;
		try { 
			in = Thread.currentThread().getContextClassLoader().getResource("application.properties").openStream();
			Properties props = new Properties();
			props.load(in); 
			return props;
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(null!=in)
				try {in.close();} catch (IOException e) {e.printStackTrace();} // 别忘了关流
		}
		return null;
    }
    
    public static void putPropertiesToCache(){
    	Properties pro = getProperties();
    }

}
