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

import com.carrotsearch.randomizedtesting.RandomizedTest
import org.elasticsearch.action.ActionFuture
import org.elasticsearch.common.unit.TimeValue
import org.junit.Test

import java.util.concurrent.TimeUnit

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests {@link ActionFutureExtensions}.
 */
class ActionFutureExtensionsTests extends RandomizedTest {
    /**
     * Mock {@link ActionFuture} to ensure functionality.
     */
    private final ActionFuture<String> future = mock(ActionFuture)
    /**
     * Expected result from {@code actionGet} methods.
     */
    private final String expectedResult = randomAsciiOfLengthBetween(1, 12)

    @Test(expected = NullPointerException)
    void testGetResponse_null_throwsException() {
        ActionFutureExtensions.getResponse(null)
    }

    @Test
    void testGetResponse_actionGet() {
        when(future.actionGet()).thenReturn(expectedResult)

        assert ActionFutureExtensions.getResponse(future) == expectedResult
    }

    @Test(expected = NullPointerException)
    void testResponseString_nullSelf_throwsException() {
        ActionFutureExtensions.response(null, "string")
    }

    @Test
    void testResponseString_actionGet() {
        String timeout = randomAsciiOfLength(4)

        when(future.actionGet(timeout)).thenReturn(expectedResult)

        assert ActionFutureExtensions.response(future, timeout) == expectedResult
    }

    @Test(expected = NullPointerException)
    void testResponseLong_nullSelf_throwsException() {
        ActionFutureExtensions.response(null, 1L)
    }

    @Test
    void testResponseLong_actionGet() {
        long timeoutMs = randomInt()

        when(future.actionGet(timeoutMs)).thenReturn(expectedResult)

        assert ActionFutureExtensions.response(future, timeoutMs) == expectedResult
    }

    @Test(expected = NullPointerException)
    void testResponseTimeValue_nullSelf_throwsException() {
        ActionFutureExtensions.response(null, TimeValue.timeValueMillis(1L))
    }

    @Test
    void testResponseTimeValue_actionGet() {
        TimeValue timeValue = TimeValue.timeValueMillis(randomInt())

        when(future.actionGet(timeValue)).thenReturn(expectedResult)

        assert ActionFutureExtensions.response(future, timeValue) == expectedResult
    }

    @Test(expected = NullPointerException)
    void testResponseTimeUnit_nullSelf_throwsException() {
        ActionFutureExtensions.response(null, 1L, TimeUnit.MILLISECONDS)
    }

    @Test
    void testResponseTimeUnit_actionGet() {
        long timeout = randomInt()
        TimeUnit timeUnit = TimeUnit.values()[randomInt(TimeUnit.values().length - 1)]

        when(future.actionGet(timeout, timeUnit)).thenReturn(expectedResult)

        assert ActionFutureExtensions.response(future, timeout, timeUnit) == expectedResult
    }
}
