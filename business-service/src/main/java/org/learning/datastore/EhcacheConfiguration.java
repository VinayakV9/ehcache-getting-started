package org.learning.datastore;

import java.io.File;

import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.CacheManagerConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EhcacheConfiguration {
	
	@Value("${ehcache-business-service.disk-store:/opt}")
	private String ehcacheDiskStoragePath;

	@Bean
	public CacheManager cacheManager() {
		PersistentCacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.with(CacheManagerBuilder.persistence(new File(getStoragePath(), "myEhcacheData")))
				.build(true);
		
		cacheManager.createCache("cache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Integer.class, Integer.class
						, ResourcePoolsBuilder.newResourcePoolsBuilder().disk(4, MemoryUnit.MB,true))
				
				.build());
				
		return cacheManager;
	}
	
	private String getStoragePath() {
		return ehcacheDiskStoragePath;
	}
}
