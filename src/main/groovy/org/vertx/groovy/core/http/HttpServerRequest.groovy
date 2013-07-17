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
import org.vertx.java.core.http.HttpVersion

import javax.net.ssl.SSLPeerUnverifiedException
import javax.security.cert.X509Certificate

/**
 * Represents a server-side HTTP request.<p>
 * An instance of this class is created for each request that is handled by the server
 * and is passed to the user via the handler instance
 * registered with the {@link HttpServer} using the method {@link HttpServer#requestHandler(Closure)}.<p>
 * Each instance of this class is associated with a corresponding {@link HttpServerResponse} instance via
 * the {@code response} field.<p>
 * It implements {@link org.vertx.groovy.core.streams.ReadStream} so it can be used with
 * {@link org.vertx.groovy.core.streams.Pump} to pump data with flow control.<p>
 * Instances of this class are not thread-safe<p>
 *
 * @author Peter Ledbrook
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
interface HttpServerRequest extends ReadStream<HttpServerRequest> {

  /**
   * The HTTP version
   */
  HttpVersion getVersion()

  /**
   * The HTTP method for the request. One of GET, PUT, POST, DELETE, TRACE, CONNECT, OPTIONS or HEAD
   */
  String getMethod()

  /**
   * The uri of the request. For example
   * http://www.somedomain.com/somepath/somemorepath/somresource.foo?someparam=32&someotherparam=x
   */
  String getUri()

  /**
   * The path part of the uri. For example /somepath/somemorepath/somresource.foo
   */
  String getPath()

  /**
   * The query part of the uri. For example someparam=32&someotherparam=x
   */
  String getQuery()

  /**
   * The response. Each instance of this class has an {@link org.vertx.java.core.http.HttpServerResponse} instance attached to it. This is used
   * to send the response back to the client.
   */
  HttpServerResponse getResponse()

  /**
   * A map of all headers in the request, If the request contains multiple headers with the same key, the values
   * will be concatenated together into a single header with the same key value, with each value separated by a comma,
   * as specified <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2">here</a>.
   * The headers will be automatically lower-cased when they reach the server
   */
  MultiMap getHeaders()

  /**
   * Returns a map of all the parameters in the request
   */
  MultiMap getParams()

  /**
   * Call this with true if you are expecting a multi-part form to be submitted in the request
   * This must be called before the body of the request has been received
   * @param expect
   */
  HttpServerRequest setExpectMultiPart(boolean expect);

  /**
   * Set the upload handler. The handler will get notified once a new file upload was received and so allow to
   * get notified by the upload in progress.
   */
  HttpServerRequest uploadHandler(Closure uploadHandler);

  /**
   * Returns a map of all form attributes which was found in the request. Be aware that this message should only get
   * called after the endHandler was notified as the map will be filled on-the-fly.
   */
  MultiMap getFormAttributes()

  /**
   * Return the remote (client side) address of the request
   */
  InetSocketAddress getRemoteAddress()

  /**
   * @return an array of the peer certificates.  Returns null if connection is
   *         not SSL.
   * @throws javax.net.ssl.SSLPeerUnverifiedException SSL peer's identity has not been verified.
   */
  X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException

  /**
   * Get the absolute URI corresponding to the the HTTP request
   * @return the URI
   */
  URI getAbsoluteURI()

  /**
   * Convenience method for receiving the entire request body in one piece. This saves the user having to manually
   * set a data and end handler and append the chunks of the body until the whole body received.
   * Don't use this if your request body is large - you could potentially run out of RAM.
   *
   * @param bodyHandler This handler will be called after all the body has been received
   */
  HttpServerRequest bodyHandler(Closure bodyHandler)

}
