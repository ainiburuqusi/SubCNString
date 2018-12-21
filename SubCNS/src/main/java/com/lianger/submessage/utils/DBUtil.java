package com.lianger.submessage.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lianger.submessage.context.Context;

public class DBUtil {

	/**
	 * 
	 * @param path 指定词库文件存储到词库
	 * @return
	 */
	public static boolean fileToDB(String path) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		boolean result = false;
		try {
			File file = new File(path);
			if (file.exists()) {
				reader = new BufferedReader(new FileReader(file));
				String len = null;
				while ((len = reader.readLine()) != null) {
					list.add(len);
				}
				result = listToDB(list);
			} else {
				result = false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 *  指定List存储到词库
	 * @param list
	 * @return 
	 */
	public static boolean listToDB(List<String> list) {

		for (String string : list) {
			Context.put(string);
		}
		return true;
	}

}
