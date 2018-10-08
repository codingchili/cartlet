package com.codingchili.webshoppe.controller;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robin Duda
 *
 * Provides support for async operations.
 */
public class Async {
    private static final Logger logger = LoggerFactory.getLogger(Async.class);
    private static Vertx vertx = Vertx.vertx();

    /**
     * @param runnable the runnable to execute asynchronously.
     * @param name name of the task to run, for logging.
     */
    public static void task(Runnable runnable, String name) {
        long then = System.currentTimeMillis();
        vertx.executeBlocking(future -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                logger.error("error invoking async: " + name, e);
            } finally {
                future.complete();
            }
        }, false, done -> {
            long elapsed = (System.currentTimeMillis()) - then;
            logger.info("async task '" + name + "' in " + elapsed + "ms.");
        });
    }

    /**
     * @return a non-clustered vertx instance.
     */
    public static Vertx vertx() {
        return vertx;
    }
}
