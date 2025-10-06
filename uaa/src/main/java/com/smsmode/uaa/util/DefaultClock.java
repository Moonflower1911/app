/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.util;

import io.jsonwebtoken.Clock;

import java.util.Date;

/**
 * Default implementation of the {@link Clock} interface, providing the current date and time using
 * the system's default clock.
 *
 * <p>This class is a singleton, and the current date and time can be obtained by calling the {@link
 * #now()} method. The singleton instance is accessible through the {@link #INSTANCE} constant.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
public class DefaultClock implements Clock {
    /**
     * Singleton instance of the {@link DefaultClock}.
     */
    public static final Clock INSTANCE = new DefaultClock();

    /**
     * Constructs a new instance of {@link DefaultClock}. (Private to enforce singleton pattern)
     */
    private DefaultClock() {
    }

    /**
     * Returns the current date and time using the system's default clock.
     *
     * @return A {@link Date} object representing the current date and time.
     */
    public Date now() {
        return new Date();
    }
}
