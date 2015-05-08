package com.wjs.study.memSize.usapCacheSize;

import java.lang.ref.SoftReference;

/**
 * jvm缓存实体对象
 *
 * @author 王金绍
 * 2014年10月24日-上午11:49:25
 */
@SuppressWarnings("all")
public class CacheEntry {
	
	private Object value;
	/**有效时间，单位毫秒 */
	private long expTime;
	
	
	public CacheEntry(Object value, int expTime){
		this.expTime = System.currentTimeMillis()+expTime;
		this.value = new SoftReference(value);
		
	}

	public boolean isOutOfDate() {
        return System.currentTimeMillis() > expTime;
    }

	public Object getValue() {
		return ((java.lang.ref.SoftReference) value).get();
	}

}
