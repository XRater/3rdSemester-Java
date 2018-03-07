package com.spbau.mit.kirakosian.threadPool.exceptions;

/**
 * This exception is thrown in case of trying to add new task in the pool, that
 * was already turned off.
 */
public class ThreadPoolIsTurnedDownException extends RuntimeException {
}
