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
package org.elasticsearch.groovy.client;

import groovy.lang.Closure;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequest;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptResponse;
import org.elasticsearch.action.mlt.MoreLikeThisRequest;
import org.elasticsearch.action.percolate.MultiPercolateRequest;
import org.elasticsearch.action.percolate.MultiPercolateResponse;
import org.elasticsearch.action.percolate.PercolateRequest;
import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.suggest.SuggestRequest;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.action.termvector.MultiTermVectorsRequest;
import org.elasticsearch.action.termvector.MultiTermVectorsResponse;
import org.elasticsearch.action.termvector.TermVectorRequest;
import org.elasticsearch.action.termvector.TermVectorResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;

/**
 * {@code ClientExtensions} provides extensions to the Elasticsearch {@link Client} to enable Groovy-friendly
 * requests.
 */
public class ClientExtensions extends AbstractClientExtensions {
    /**
     * Get the admin client that can be used to perform administrative operations.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @return Always {@link Client#admin()}.
     * @throws NullPointerException if {@code self} is {@code null}
     */
    public static AdminClient getAdmin(Client self) {
        return self.admin();
    }

    /**
     * Get the client {@link Settings}.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @return Always {@link Client#settings()}.
     * @throws NullPointerException if {@code self} is {@code null}
     */
    public static Settings getSettings(Client self) {
        return self.settings();
    }

    // REQUEST/RESPONSE

    /**
     * Index a document associated with a given index and type, then get the future result.
     * <p/>
     * The id is optional, if it is not provided, one will be generated automatically.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link IndexRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<IndexResponse> index(Client self, Closure requestClosure) {
        Wrapper<IndexRequest, IndexResponse> wrapper = wrap(self, Requests.indexRequest(), requestClosure);

        self.index(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Executes a bulk of index / delete operations.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link BulkRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<BulkResponse> bulk(Client self, Closure requestClosure) {
        Wrapper<BulkRequest, BulkResponse> wrapper = wrap(self, new BulkRequest(), requestClosure);

        self.bulk(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Updates a document based on a script.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link UpdateRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<UpdateResponse> update(Client self, Closure requestClosure) {
        Wrapper<UpdateRequest, UpdateResponse> wrapper = wrap(self, new UpdateRequest(), requestClosure);

        self.update(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link DeleteRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<DeleteResponse> delete(Client self, Closure requestClosure) {
        Wrapper<DeleteRequest, DeleteResponse> wrapper = wrap(self, new DeleteRequest(), requestClosure);

        self.delete(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Deletes all documents from one or more indices based on a query.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link DeleteByQueryRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<DeleteByQueryResponse> deleteByQuery(Client self, Closure requestClosure) {
        Wrapper<DeleteByQueryRequest, DeleteByQueryResponse> wrapper =
                wrap(self, Requests.deleteByQueryRequest(), requestClosure);

        self.deleteByQuery(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param index The index to load the document(s) from
     * @param requestClosure The map-like closure that configures the {@link GetRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<GetResponse> get(Client self, String index, Closure requestClosure) {
        Wrapper<GetRequest, GetResponse> wrapper = wrap(self, Requests.getRequest(index), requestClosure);

        self.get(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Multi-get documents. This provides the mechanism to perform bulk requests (as opposed to bulk indexing) to avoid
     * unnecessary back-and-forth requests.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link MultiGetRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<MultiGetResponse> multiGet(Client self, Closure requestClosure) {
        Wrapper<MultiGetRequest, MultiGetResponse> wrapper = wrap(self, new MultiGetRequest(), requestClosure);

        self.multiGet(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Request suggestion matching for a specific query.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link SuggestRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<SuggestResponse> suggest(Client self, Closure requestClosure) {
        Wrapper<SuggestRequest, SuggestResponse> wrapper = wrap(self, new SuggestRequest(), requestClosure);

        self.suggest(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Search across one or more indices and one or more types with a query.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link SearchRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<SearchResponse> search(Client self, Closure requestClosure) {
        Wrapper<SearchRequest, SearchResponse> wrapper = wrap(self, Requests.searchRequest(), requestClosure);

        self.search(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Perform multiple search requests similar to multi-get.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link MultiSearchRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<MultiSearchResponse> multiSearch(Client self, Closure requestClosure) {
        Wrapper<MultiSearchRequest, MultiSearchResponse> wrapper = wrap(self, new MultiSearchRequest(), requestClosure);

        self.multiSearch(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * A more like this action to search for documents that are "like" a specific document.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param index The index to load the document(s) from
     * @param requestClosure The map-like closure that configures the {@link MoreLikeThisRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<SearchResponse> moreLikeThis(Client self,
                                                                      String index,
                                                                      Closure requestClosure) {
        Wrapper<MoreLikeThisRequest, SearchResponse> wrapper =
                wrap(self, Requests.moreLikeThisRequest(index), requestClosure);

        self.moreLikeThis(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Request a count of documents matching a specified query.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link CountRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<CountResponse> count(Client self, Closure requestClosure) {
        Wrapper<CountRequest, CountResponse> wrapper = wrap(self, Requests.countRequest(), requestClosure);

        self.count(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * A search scroll request to continue searching a previous scrollable search request.
     * <p />
     * Note: The {@link SearchResponse} will contain a new ID to use for subsequent requests.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link SearchScrollRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<SearchResponse> searchScroll(Client self, Closure requestClosure) {
        Wrapper<SearchScrollRequest, SearchResponse> wrapper = wrap(self, new SearchScrollRequest(), requestClosure);

        self.searchScroll(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Clears the search contexts associated with specified Scroll IDs.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link ClearScrollRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<ClearScrollResponse> clearScroll(Client self, Closure requestClosure) {
        Wrapper<ClearScrollRequest, ClearScrollResponse> wrapper = wrap(self, new ClearScrollRequest(), requestClosure);

        self.clearScroll(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * An action that returns the term vectors for a specific document.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param index The index to load the document(s) from
     * @param type The type of the document
     * @param id The id of the document
     * @param requestClosure The map-like closure that configures the {@link TermVectorRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<TermVectorResponse> termVector(Client self,
                                                                        String index,
                                                                        String type,
                                                                        String id,
                                                                        Closure requestClosure) {
        TermVectorRequest request = new TermVectorRequest(index, type, id);
        Wrapper<TermVectorRequest, TermVectorResponse> wrapper = wrap(self, request, requestClosure);

        self.termVector(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Multi-get term vectors.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link MultiTermVectorsRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<MultiTermVectorsResponse> multiTermVectors(Client self,
                                                                                    Closure requestClosure) {
        Wrapper<MultiTermVectorsRequest, MultiTermVectorsResponse> wrapper =
                wrap(self, new MultiTermVectorsRequest(), requestClosure);

        self.multiTermVectors(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Percolates a request returning the matching documents.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link PercolateRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<PercolateResponse> percolate(Client self, Closure requestClosure) {
        Wrapper<PercolateRequest, PercolateResponse> wrapper = wrap(self, new PercolateRequest(), requestClosure);

        self.percolate(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Performs multiple percolate requests.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link MultiPercolateRequest}.
     * @return Never {@code null}.
     */
    public static ListenableActionFuture<MultiPercolateResponse> multiPercolate(Client self, Closure requestClosure) {
        Wrapper<MultiPercolateRequest, MultiPercolateResponse> wrapper =
                wrap(self, new MultiPercolateRequest(), requestClosure);

        self.multiPercolate(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Computes a score explanation for the specified request.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param index The index to load the document(s) from
     * @param type The type of the document
     * @param id The id of the document
     * @param requestClosure The map-like closure that configures the {@link ExplainRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<ExplainResponse> explain(Client self,
                                                                  String index,
                                                                  String type,
                                                                  String id,
                                                                  Closure requestClosure) {
        ExplainRequest request = new ExplainRequest(index, type, id);
        Wrapper<ExplainRequest, ExplainResponse> wrapper = wrap(self, request, requestClosure);

        self.explain(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Put (set/add) the indexed script.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link PutIndexedScriptRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<PutIndexedScriptResponse> putIndexedScript(Client self,
                                                                                    Closure requestClosure) {
        Wrapper<PutIndexedScriptRequest, PutIndexedScriptResponse> wrapper =
                wrap(self, new PutIndexedScriptRequest(), requestClosure);

        self.putIndexedScript(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Get an indexed script.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link GetIndexedScriptRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<GetIndexedScriptResponse> getIndexedScript(Client self,
                                                                                    Closure requestClosure) {
        Wrapper<GetIndexedScriptRequest, GetIndexedScriptResponse> wrapper =
                wrap(self, new GetIndexedScriptRequest(), requestClosure);

        self.getIndexedScript(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }

    /**
     * Delete an indexed script.
     *
     * @param self The {@code this} reference for the {@link Client}
     * @param requestClosure The map-like closure that configures the {@link DeleteIndexedScriptRequest}.
     * @return Never {@code null}.
     * @throws NullPointerException if any parameter is {@code null}
     */
    public static ListenableActionFuture<DeleteIndexedScriptResponse> deleteIndexedScript(Client self,
                                                                                          Closure requestClosure) {
        Wrapper<DeleteIndexedScriptRequest, DeleteIndexedScriptResponse> wrapper =
                wrap(self, new DeleteIndexedScriptRequest(), requestClosure);

        self.deleteIndexedScript(wrapper.request, wrapper.responseFuture);

        return wrapper.responseFuture;
    }
}
