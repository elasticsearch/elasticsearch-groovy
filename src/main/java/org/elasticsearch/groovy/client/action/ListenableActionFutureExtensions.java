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

import groovy.lang.Closure;
import org.elasticsearch.ElasticsearchNullPointerException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;

/**
 * {@code ListenableActionFutureExtensions} decorates {@link ListenableActionFuture} with Groovy-friendly methods.
 */
public class ListenableActionFutureExtensions {
    /**
     * Invoke the {@code successCallback} {@link Closure} and pass it the response value of type {@code T} if the action
     * succeeds.
     * <p />
     * Note: This will ignore any failure.
     * @param self The {@code this} parameter representing the augmented {@link ListenableActionFuture}.
     * @param successCallback The callback to invoke upon success.
     * @return Always {@code self}.
     * @throws NullPointerException if {@code self} is {@code null}
     * @throws ElasticsearchNullPointerException if {@code failureCallback} is {@code null}
     */
    public static <T> ListenableActionFuture<T> onResponse(ListenableActionFuture<T> self,
                                                           final Closure successCallback) {
        if (successCallback == null) {
            throw new ElasticsearchNullPointerException("successCallback cannot be null");
        }

        self.addListener(new ActionListener<T>() {
            @Override
            public void onResponse(T t) {
                successCallback.call(t);
            }

            @Override
            public void onFailure(Throwable e) {
                // ignore
            }
        });

        return self;
    }

    /**
     * Invoke the {@code failureCallback} {@link Closure} and pass it the {@link Throwable} if the action fails.
     * <p />
     * Note: This will ignore any success.
     * @param self The {@code this} parameter representing the augmented {@link ListenableActionFuture}.
     * @param failureCallback The callback to invoke upon failure.
     * @return Always {@code self}.
     * @throws NullPointerException if {@code self} is {@code null}
     * @throws ElasticsearchNullPointerException if {@code failureCallback} is {@code null}
     */
    public static <T> ListenableActionFuture<T> onFailure(ListenableActionFuture<T> self,
                                                          final Closure failureCallback) {
        if (failureCallback == null) {
            throw new ElasticsearchNullPointerException("failureCallback cannot be null");
        }

        self.addListener(new ActionListener<T>() {
            @Override
            public void onResponse(T t) {
                // ignore
            }

            @Override
            public void onFailure(Throwable e) {
                failureCallback.call(e);
            }
        });

        return self;
    }
}
