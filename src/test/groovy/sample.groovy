import static org.elasticsearch.node.NodeBuilder.nodeBuilder

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

def node = nodeBuilder().local(true).node()

println "settings $node.settings.asMap"

def future = node.client.index {
    index "twitter"
    type "tweet"
    id "1"
    source {
        user = "kimchy"
        message = "this is a tweet"
    }
}

println "Indexed $future.response.index/$future.response.type/$future.response.id"

node.close()
