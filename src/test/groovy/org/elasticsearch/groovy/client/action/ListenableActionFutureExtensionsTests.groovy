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
import org.elasticsearch.ElasticsearchNullPointerException
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.ListenableActionFuture

import org.junit.Test
import org.mockito.ArgumentCaptor

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.verifyNoMoreInteractions

/**
 * Tests {@link ListenableActionFutureExtensions}.
 */
class ListenableActionFutureExtensionsTests extends RandomizedTest {
    /**
     * Mock {@link ListenableActionFuture} to ensure functionality.
     */
    private final ListenableActionFuture<String> future = mock(ListenableActionFuture)
    /**
     * {@link ArgumentCaptor} enables getting a reference to the reference that is actually added.
     */
    private final ArgumentCaptor<ActionListener<String>> listenerCaptor = ArgumentCaptor.forClass(ActionListener)

    @Test(expected = NullPointerException)
    void testOnResponse_nullSelf_throwsException() {
        ListenableActionFutureExtensions.onResponse(null) {}
    }

    @Test(expected = ElasticsearchNullPointerException)
    void testOnResponse_nullCallback_throwsException() {
        ListenableActionFutureExtensions.onResponse(future, null)
    }

    @Test
    void testOnResponse_successCallback_invoked() {
        String value = null
        String expected = randomAsciiOfLengthBetween(1, 8)

        // callback assigns passed in value to value
        ListenableActionFutureExtensions.onResponse(future) { String t -> value = t }

        // capture a reference to the listener
        verify(future).addListener((ActionListener<String>)listenerCaptor.capture())

        // force the successful condition
        listenerCaptor.value.onResponse(expected)

        assert value == expected

        // ensure nothing else happened to the future
        verifyNoMoreInteractions(future)
    }

    @Test
    void testOnResponse_successCallback_ignored() {
        // callback should never be invoked
        ListenableActionFutureExtensions.onResponse(future) { fail("successCallback should not be called") }

        // capture a reference to the listener
        verify(future).addListener((ActionListener<String>)listenerCaptor.capture())

        // force the failure condition
        listenerCaptor.value.onFailure(new Throwable())

        // ensure nothing else happened to the future
        verifyNoMoreInteractions(future)
    }

    @Test(expected = NullPointerException)
    void testOnFailure_nullSelf_throwsException() {
        ListenableActionFutureExtensions.onFailure(null) {}
    }

    @Test(expected = ElasticsearchNullPointerException)
    void testOnFailure_nullCallback_throwsException() {
        ListenableActionFutureExtensions.onFailure(future, null)
    }

    @Test
    void testOnFailure_failureCallback_invoked() {
        Throwable value = null
        Throwable expected = new Throwable()

        // callback assigns passed in value to value
        ListenableActionFutureExtensions.onFailure(future) { Throwable t -> value = t }

        // capture a reference to the listener
        verify(future).addListener((ActionListener<String>)listenerCaptor.capture())

        // force the failure condition
        listenerCaptor.value.onFailure(expected)

        assert value == expected

        // ensure nothing else happened to the future
        verifyNoMoreInteractions(future)
    }

    @Test
    void testOnFailure_failureCallback_ignored() {
        // callback should never be invoked
        ListenableActionFutureExtensions.onFailure(future) { fail("failureCallback should not be called") }

        // capture a reference to the listener
        verify(future).addListener((ActionListener<String>)listenerCaptor.capture())

        // force the successful condition
        listenerCaptor.value.onResponse("success!")

        // ensure nothing else happened to the future
        verifyNoMoreInteractions(future)
    }
}
