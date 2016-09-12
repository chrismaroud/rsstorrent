package com.bitsfromspace.torrentrss.utils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * + * @author chris.oudeman
 * + * @since 05-Sep-16
 * +
 */

public class Cache<K, V> {
    private final TimeProvider timeProvider;
    private final int maxSize;
    private final long expireAfterMillis;
    private final DelayQueue<DelayedKey<K>> expirationQueue;
    private final Map<K, V> cache;

    public Cache(TimeProvider timeProvider, int maxSize, int expireAfter, TimeUnit expireAfterTimeUnit) {
        this.timeProvider = timeProvider;
        this.maxSize = maxSize;
        this.expireAfterMillis = expireAfterTimeUnit.toMillis(expireAfter);
        expirationQueue = new DelayQueue<>();
        cache = new HashMap<>();
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        synchronized (cache) {
            clearExpiredKeys();
            return getOrAdd(key, mappingFunction);
        }
    }

    private void clearExpiredKeys() {
        DelayedKey<K> expiredKey;
        while ((expiredKey = expirationQueue.poll()) != null) {
            cache.remove(expiredKey.key);
        }
    }

    private V getOrAdd(K key, Function<? super K, ? extends V> mappingFunction) {
        return cache.computeIfAbsent(key, k -> {
            final V calculatedValue = mappingFunction.apply(key);
            if (calculatedValue == null) {
                return null;
            }
            if (cache.size() > maxSize) {
                final K keyToDelete = expirationQueue.remove().key;
                cache.remove(keyToDelete);
            }
            final long expirationDate = timeProvider.getTime() + expireAfterMillis;
            expirationQueue.put(new DelayedKey<>(timeProvider, expirationDate, k));
            return calculatedValue;
        });
    }


    private static class DelayedKey<K> implements Delayed {
        private final TimeProvider timeProvider;
        private final long expireOn;
        private final K key;


        DelayedKey(TimeProvider timeProvider, long expireOn, K key) {
            this.timeProvider = timeProvider;
            this.expireOn = expireOn;
            this.key = key;
        }

        @Override
        public long getDelay(@NotNull TimeUnit unit) {
            final long delay = unit.convert(expireOn - timeProvider.getTime(), TimeUnit.MILLISECONDS);
            return delay;
        }

        @Override
        public int compareTo(@NotNull Delayed o) {
            long delta = expireOn - ((DelayedKey) o).expireOn;
            return delta < 0 ? -1 : delta > 0 ? 1 : 0;
        }
    }
}