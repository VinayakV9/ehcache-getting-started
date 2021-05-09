package org.learning.datastore;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CacheUpdater implements ApplicationListener<ApplicationStartedEvent> {

	private static final String STATIC_DATASTORE_SERVICE = "http://localhost:8092/getData/";

	private static Logger logger = LoggerFactory.getLogger(CacheUpdater.class);

	@Autowired
	private CacheManager cacheManager;

	private RestTemplate restTemplate;

	public CacheUpdater() {
		super();
		restTemplate = new RestTemplate();
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {

		Cache<Integer, Integer> cache = cacheManager.getCache("cache", Integer.class, Integer.class);

		try {
			int records = 1000;
			int id = records;
			StopWatch stopWatch = StopWatch.of().start();
			while (id-- > 0) {
				if (!cache.containsKey(id)) {
					ResponseEntity<Integer> response = restTemplate.getForEntity(new URI(STATIC_DATASTORE_SERVICE + id),
							Integer.class);
					cache.put(id, response.getBody());
				}
			}

			Duration duration = stopWatch.stop();
			logger.info("Took " + duration.getSeconds() + " seconds for " + records + " records");
		} catch (RestClientException | URISyntaxException e) {
			e.printStackTrace();
		}

		cacheManager.close();
	}

}
