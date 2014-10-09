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
package org.elasticsearch.groovy.client.action.admin.indices.settings.put

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder
import org.elasticsearch.client.IndicesAdminClient
import org.elasticsearch.groovy.client.action.AbstractRequestExtensions

/**
 * {@code UpdateSettingsRequestExtensions} provides Groovy-friendly {@link UpdateSettingsRequest} extensions.
 * @see IndicesAdminClient#updateSettings(UpdateSettingsRequest)
 */
class UpdateSettingsRequestExtensions extends AbstractRequestExtensions {
    /**
     * Sets the {@code settings} to update.
     *
     * @param self The {@code this} reference for the {@link UpdateSettingsRequest}.
     * @param settings The settings to update
     * @return Always {@code self}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    static UpdateSettingsRequest settings(UpdateSettingsRequest self, Closure settings) {
        self.settings(toString(settings))
    }

    /**
     * Sets the {@code settings} to update.
     *
     * @param self The {@code this} reference for the {@link UpdateSettingsRequest}.
     * @param settings The settings to update
     * @return Always {@code self}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    static UpdateSettingsRequest setSettings(UpdateSettingsRequest self, Closure settings) {
        settings(self, settings)
    }

    /**
     * Sets the {@code settings} to update.
     *
     * @param self The {@code this} reference for the {@link UpdateSettingsRequestBuilder}.
     * @param settings The settings to update
     * @return Always {@code self}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    static UpdateSettingsRequestBuilder setSettings(UpdateSettingsRequestBuilder self, Closure settings) {
        self.setSettings(toString(settings))
    }
}
