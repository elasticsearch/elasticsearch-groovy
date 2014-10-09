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
package org.elasticsearch.groovy.client.action

import org.elasticsearch.client.Requests
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.groovy.common.xcontent.GXContentBuilder

/**
 * {@code AbstractRequestExtensions} provides helper functions used by most {@code AbstractRequest} extensions to
 * better support Groovy features.
 */
abstract class AbstractRequestExtensions {
    /**
     * Convert the {@code closure} to bytes using the {@link Requests#CONTENT_TYPE}.
     * @param closure The closure to convert to bytes.
     * @return Never {@code null}.
     * @throws NullPointerException if {@code closure} is {@code null}
     * @see #toBytes(Closure, XContentType)
     */
    protected static byte[] toBytes(Closure closure) {
        toBytes(closure, Requests.CONTENT_TYPE)
    }

    /**
     * Convert the {@code closure} to bytes using the {@code contentType}.
     * @param closure The closure to convert to bytes.
     * @param contentType The desired content type.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    protected static byte[] toBytes(Closure closure, XContentType contentType) {
        new GXContentBuilder().buildAsBytes(closure, contentType)
    }

    /**
     * Convert the {@code closure} to a JSON string.
     * @param closure The closure to convert to a string.
     * @throws NullPointerException if {@code closure} is {@code null}
     * @return Never {@code null}.
     */
    protected static String toString(Closure closure) {
        new GXContentBuilder().buildAsString(closure)
    }
}
