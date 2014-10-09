/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.groovy.client.action;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * {@code ActionFutureExtensions} provides extensions for the built-in {@link ActionFuture} class with Groovy-friendly
 * methods.
 */
public class ActionFutureExtensions {
    /**
     * Get the {@link ActionFuture#actionGet() response}.
     * @param self The {@code this} parameter representing the augmented {@link ActionFuture}.
     * @return Always the result of {@link ActionFuture#actionGet()}.
     * @throws NullPointerException if {@code self} is {@code null}
     */
    public static <T> T getResponse(ActionFuture<T> self) {
        return self.actionGet();
    }

    /**
     * Get the {@link ActionFuture#actionGet(String) response}.
     * @param self The {@code this} parameter representing the augmented {@link ActionFuture}.
     * @param timeout The timeout to parse and use.
     * @return Always the result of {@link ActionFuture#actionGet(String)} given {@code timeout}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static <T> T response(ActionFuture<T> self, String timeout) {
        return self.actionGet(timeout);
    }

    /**
     * Get the {@link ActionFuture#actionGet(long) response}.
     * @param self The {@code this} parameter representing the augmented {@link ActionFuture}.
     * @param timeoutMillis The timeout in milliseconds.
     * @return Always the result of {@link ActionFuture#actionGet(long)} given {@code timeoutMillis}.
     * @throws NullPointerException if {@code self} is {@code null}
     */
    public static <T> T response(ActionFuture<T> self, long timeoutMillis) {
        return self.actionGet(timeoutMillis);
    }

    /**
     * Get the {@link ActionFuture#actionGet(TimeValue) response}.
     * @param self The {@code this} parameter representing the augmented {@link ActionFuture}.
     * @param timeout The timeout to use
     * @return Always the result of {@link ActionFuture#actionGet(TimeValue)} given {@code timeout}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static <T> T response(ActionFuture<T> self, TimeValue timeout) {
        return self.actionGet(timeout);
    }

    /**
     * Get the {@link ActionFuture#actionGet(long, TimeUnit) response}.
     * @param self The {@code this} parameter representing the augmented {@link ActionFuture}.
     * @param timeout The timeout to use in the given {@code unit}s
     * @param unit The unit of the {@code timeout}
     * @return Always the result of {@link ActionFuture#actionGet(long, TimeUnit)} given {@code timeout} and {@code unit}.
     * @throws NullPointerException if {@code self} or {@code unit} is {@code null}
     */
    public static <T> T response(ActionFuture<T> self, long timeout, TimeUnit unit) {
        return self.actionGet(timeout, unit);
    }
}
