package com.lianger.submessage.model;

import java.io.Serializable;
import java.net.IDN;
/**
 *  词语实体类
 * @author ZhangLiang
 *
 */
public class CharsEntity implements Serializable {

	private static final long serialVersionUID = 5345451778156055913L;
	
	private String acsii;
	private String unicode;
	private long useCount;
	public String getAcsii() {
		return acsii;
	}
	public void setAcsii(String acsii) {
		this.acsii = acsii;
	}
	public String getUnicode() {
		return unicode;
	}
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	public long getUseCount() {
		return useCount;
	}
	public void setUseCount(long useCount) {
		this.useCount = useCount;
	}
	
	public CharsEntity(String unicode){
		this.unicode = unicode;
		this.acsii = IDN.toASCII(unicode);
		this.useCount = 0L;
	}
	public CharsEntity(String unicode,long useCount){
		this.unicode = unicode;
		this.acsii = IDN.toASCII(unicode);
		this.useCount = useCount;
	}
	@Override
	public String toString() {
		return unicode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acsii == null) ? 0 : acsii.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CharsEntity)) {
			return false;
		}
		CharsEntity other = (CharsEntity) obj;
		if (acsii == null) {
			if (other.acsii != null) {
				return false;
			}
		} else if (!acsii.equals(other.acsii)) {
			return false;
		}
		return true;
	}
	public void addUseCount() {
		// TODO Auto-generated method stub
		this.useCount++;
	}
	
	
}
