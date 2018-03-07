package com.spbau.mit.kirakosian.threadPool.exceptions;

import com.spbau.mit.kirakosian.threadPool.LightFuture;

/**
 * This exception may be thrown if implementation of {@link LightFuture}
 * interface methods was not concurrent safe.
 * <p>
 * For example, if tasks method "run" was called more then one time.
 */
public class TaskIsReadyAlreadyException extends RuntimeException {

}
