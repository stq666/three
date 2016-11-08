package com.drink.cornerstone.security;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.util.SoftHashMap;

public class SimpleCacheManager extends AbstractCacheManager {
	
	@Override
	protected Cache<Object, Object> createCache(String name) throws CacheException {
		return new MapCache<Object, Object>(name, new SoftHashMap<Object, Object>());
	}
	
}
