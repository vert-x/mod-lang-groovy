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

import org.vertx.java.core.ServerSSLSupport
import org.vertx.java.core.ServerTCPSupport


/**
 * An HTTP and WebSockets server<p>
 * If an instance is instantiated from an event loop then the handlers
 * of the instance will always be called on that same event loop.
 * If an instance is instantiated from some other arbitrary Java thread then
 * an event loop will be assigned to the instance and used when any of its handlers
 * are called.<p>
 * Instances of this class are thread-safe
 *
 *
 * @author Peter Ledbrook
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
interface HttpServer extends ServerSSLSupport<HttpServer>, ServerTCPSupport<HttpServer> {

  /**
   * Set the request handler for the server to {@code requestHandler}. As HTTP requests are received by the server,
   * instances of {@link org.vertx.java.core.http.HttpServerRequest} will be created and passed to this handler.
   *
   * @return a reference to this, so methods can be chained.
   */
  HttpServer requestHandler(Closure requestHandler)

  /**
   * Set the websocket handler for the server to {@code wsHandler}. If a websocket connect handshake is successful a
   * new {@link org.vertx.java.core.http.ServerWebSocket} instance will be created and passed to the handler.
   *
   * @return a reference to this, so methods can be chained.
   */
  HttpServer websocketHandler(Closure wsHandler)

  /**
   * Tell the server to start listening on all available interfaces and port {@code port}
   *
   */
  HttpServer listen(int port)

  /**
   * Tell the server to start listening on port {@code port} and hostname or ip address given by {@code host}.
   *
   */
  HttpServer listen(int port, String host)

  /**
   * Tell the server to start listening on all available interfaces and port {@code port}
   *
   */
  HttpServer listen(int port, Closure bindHandler)

  /**
   * Tell the server to start listening on port {@code port} and hostname or ip address given by {@code host}.
   *
   */
  HttpServer listen(int port, String host, Closure bindHandler)


  /**
   * Close the server. Any open HTTP connections will be closed.
   */
  void close()

  /**
   * Close the server. Any open HTTP connections will be closed. The {@code doneHandler} will be called when the close
   * is complete.
   */
  void close(Closure doneHandler)
  
  /**
   * Set if the {@link HttpServer} should compress the http response if the connected client supports it.
   */
  HttpServer setCompressionSupported(boolean compressionSupported)

  /**
   * Returns {@code true} if the {@link HttpServer} should compress the http response if the connected client supports it.
   */
  boolean isCompressionSupported()


  /**
   * Sets the maximum websocket frame size in bytes. Default is 65536 bytes.
   * @param maxSize The size in bytes
   */
  HttpServer setMaxWebSocketFrameSize(int maxSize)

  /**
   * Get the  maximum websocket frame size in bytes.
   */
  int getMaxWebSocketFrameSize()

   /**
   * Get the underlying Java server
   */
  org.vertx.java.core.http.HttpServer toJavaServer();
}
