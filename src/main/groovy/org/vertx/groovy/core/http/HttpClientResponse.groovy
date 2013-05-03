/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vertx.groovy.core.http

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap;
import org.vertx.groovy.core.streams.ReadStream

/**
 * Represents a client-side HTTP response.<p>
 * An instance of this class is provided to the user via a handler
 * that was specified when one of the HTTP method operations, or the
 * generic {@link HttpClient#request(String, String, Closure)}
 * method was called on an instance of {@link HttpClient}.<p>
 * It implements {@link org.vertx.groovy.core.streams.ReadStream} so it can be used with
 * {@link org.vertx.groovy.core.streams.Pump} to pump data with flow control.<p>
 * Instances of this class are not thread-safe<p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
interface HttpClientResponse extends ReadStream<HttpClientResponse> {

  /**
   * The HTTP status code of the response
   */
  int getStatusCode()

  /**
   * The HTTP status message of the response
   */
  String getStatusMessage()

  /**
   * @return The HTTP headers
   */
  MultiMap getHeaders()

  /**
   * @return The HTTP trailers
   */
  MultiMap getTrailers()

  /**
   * @return The Set-Cookie headers (including trailers)
   */
  List<String> getCookies()

  /**
   * Convenience method for receiving the entire request body in one piece. This saves the user having to manually
   * set a data and end handler and append the chunks of the body until the whole body received.
   * Don't use this if your request body is large - you could potentially run out of RAM.
   *
   * @param bodyHandler This handler will be called after all the body has been received
   */
  HttpClientResponse bodyHandler(Closure bodyHandler)

}
