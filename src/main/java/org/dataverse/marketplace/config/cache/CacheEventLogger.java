package org.dataverse.marketplace.config.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

    Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(
      CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        logger.info(cacheEvent.getType().toString(),
          cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}