package com.bitsfromspace.torrentrss.utils;

import org.springframework.stereotype.Component;

/**
 * @author chris
 * @since 05-09-16.
 */
@Component
public class TimeProviderImpl implements TimeProvider {

    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }
}
