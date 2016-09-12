package com.bitsfromspace.torrentrss.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author chris
 * @since 02-09-16.
 */
public class LogUtils {

    private static final Map<Class, Logger> CLASS_TO_LOGGER = new ConcurrentHashMap<>();

    public static void logError(Class clazz, String message, Throwable t){
        CLASS_TO_LOGGER.computeIfAbsent(clazz, aClass -> Logger.getLogger(aClass.getName()))
                .log(Level.SEVERE, message, t);
    }
}
