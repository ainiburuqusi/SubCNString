package com.lianger.submessage.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lianger.submessage.model.CharsEntity;
import com.lianger.submessage.utils.ResourcesUtil;
@Component
public class Context {

	private static ConcurrentHashMap<String, List<CharsEntity>> charsMap =  null;

	private static byte[] lock = new byte[0x0];
	
	private static final Logger logger = LoggerFactory.getLogger(Context.class);
	
	private static String dbPath;
	

	static{
		dbPath =ResourcesUtil.getProperties("db.path");
		Context.init();
	}
	
	public static boolean put(String unicode) {
		if (unicode != null && unicode.length() > 0) {
			String acsii = IDN.toASCII(unicode.substring(0, 1));
			List<CharsEntity> list = charsMap.get(acsii);
			synchronized(lock){
				if (list != null && list.size() > 0) {
					if (!list.contains(new CharsEntity(unicode))) {
						CharsEntity entity = new CharsEntity(unicode);
						list.add(entity);
						logger.debug("导入一条数据："+ entity.getUnicode());
						return true;
					} else
						return false;
				} else {
					CharsEntity entity = new CharsEntity(unicode);
					ArrayList<CharsEntity> l = new ArrayList<CharsEntity>();
					l.add(entity);
					charsMap.put(acsii, l);
					logger.debug("导入一条数据："+ entity.getUnicode());
					return true;
				}
			}
		} else
			return false;
	}

	public static CharsEntity get(String unicode) {
		if (unicode != null && unicode.length() > 0) {
			String cacsii = IDN.toASCII(unicode.charAt(0) + "");
			List<CharsEntity> list = charsMap.get(cacsii);
			if (list != null && list.size() > 0) {
				CharsEntity entity = new CharsEntity(unicode);
				int index = list.indexOf(entity);
				if (index == -1)
					return null;
				else
					return list.get(index);
			} else
				return null;
		} else
			return null;
	}
	public static boolean remove(String unicode){
		if (unicode != null && unicode.length() > 0) {
			String cacsii = IDN.toASCII(unicode.charAt(0) + "");
			CharsEntity entity = new CharsEntity(unicode);
			synchronized (lock) {
				if (charsMap.containsKey(cacsii) && charsMap.get(cacsii).contains(entity)){
					int index = charsMap.get(cacsii).indexOf(entity);
					charsMap.get(cacsii).remove(index);
					logger.debug("删除一条数据："+ unicode);
					return true;
				}else
					return false;
			}
		}else 
			return false;
	}
	public static boolean hasChars(String unicode) {
		if (unicode != null && unicode.length() > 0) {
			String cacsii = IDN.toASCII(unicode.charAt(0) + "");
			CharsEntity entity = new CharsEntity(unicode);
			if (charsMap.containsKey(cacsii) && charsMap.get(cacsii).contains(entity))
				return true;
			else
				return false;
		}else 
			return false;

	}

	public static void addUseCount(String unicode) {
		String cacsii = IDN.toASCII(unicode.charAt(0) + "");
		CharsEntity entity = new CharsEntity(unicode);
		if (charsMap.containsKey(cacsii)) {
			int index = charsMap.get(cacsii).indexOf(entity);
			charsMap.get(cacsii).get(index).addUseCount();
		}

	}

	public static void init() {
		File dbFile = new File(dbPath + File.separator + "chars.dbs");
		ObjectInputStream ois = null;
		logger.debug("词库容器开始初始化.");
		try {
			if (dbFile.exists()) {
				if (dbFile.length() > 0) {
					ois = new ObjectInputStream(new FileInputStream(dbFile));
					charsMap = (ConcurrentHashMap<String, List<CharsEntity>>) ois.readObject();
				}else
					charsMap = new ConcurrentHashMap<String, List<CharsEntity>>();
			} else {
				charsMap = new ConcurrentHashMap<String, List<CharsEntity>>();
				dbFile.createNewFile();
			}
			logger.debug("词库容器初始化完成.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			charsMap = new ConcurrentHashMap<String, List<CharsEntity>>();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			charsMap = new ConcurrentHashMap<String, List<CharsEntity>>();
			e.printStackTrace();
		}finally {
				try {
					if(ois!=null) ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static void backup() {
		File dbFile = new File(dbPath + File.separator + "chars.dbs");
		ObjectOutputStream oos = null;
		try {
			logger.debug("开始备份词库.");
			if (!dbFile.exists()) {
				dbFile.createNewFile();
			}
			oos = new ObjectOutputStream(new FileOutputStream(dbFile,false));
			oos.writeObject(charsMap);
			oos.flush();
			logger.debug("备份词库完成.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					if(oos!=null) oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
