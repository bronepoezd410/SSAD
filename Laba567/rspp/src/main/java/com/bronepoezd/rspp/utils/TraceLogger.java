package com.bronepoezd.rspp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceLogger {
    private static final Logger VERBOSE_LOGGER = LoggerFactory.getLogger("com.bronepoezd.rspp");

    public static void verbose(String message) {
        VERBOSE_LOGGER.trace(message);
    }
}
