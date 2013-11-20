/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic

import org.vertx.groovy.core.http.HttpServer
import org.vertx.groovy.core.impl.ClosureUtil
import org.vertx.java.core.Handler
import org.vertx.java.core.Vertx
import org.vertx.java.core.http.HttpServer as JHttpServer
import org.vertx.java.core.http.HttpServerRequest as JHttpServerRequest
import org.vertx.java.core.http.ServerWebSocket as JServerWebSocket

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultHttpServer implements HttpServer {

  // Putting it as a final public groovy property to be able to use `HttpServer.jServer` notation
  final JHttpServer jServer

  DefaultHttpServer(Vertx vertx, Map props = null) {
    jServer = vertx.createHttpServer()
    if (props != null) {
      props.each { String k, v ->
        setProperty(k, v)
      }
    }
  }

  @Override
  HttpServer requestHandler(Closure requestHandler) {
    jServer.requestHandler({requestHandler(new DefaultHttpServerRequest((JHttpServerRequest) it))} as Handler)
    this
  }

  @Override
  HttpServer websocketHandler(Closure wsHandler) {
    jServer.websocketHandler({wsHandler(new DefaultServerWebSocket((JServerWebSocket) it))} as Handler)
    this
  }

  @Override
  HttpServer listen(int port) {
    jServer.listen(port)
    this
  }

  @Override
  HttpServer listen(int port, String host) {
    jServer.listen(port, host)
    this
  }

  @Override
  HttpServer listen(int port, Closure bindHandler) {
    jServer.listen(port, ClosureUtil.wrapAsyncResultHandler(bindHandler, {
      this
    }))
    this
  }

  @Override
  HttpServer listen(int port, String host, Closure bindHandler) {
    jServer.listen(port, host, ClosureUtil.wrapAsyncResultHandler(bindHandler, {
      this
    }))
    this
  }

  @Override
  void close() {
    jServer.close()
  }

  @Override
  HttpServer setMaxWebSocketFrameSize(int maxSize) {
    jServer.setMaxWebSocketFrameSize( maxSize )
    this
  }
 
  @Override
  int getMaxWebSocketFrameSize() {
    jServer.getMaxWebSocketFrameSize()
  }

  @Override
  void close(Closure doneHandler) {
    jServer.close(ClosureUtil.wrapAsyncResultHandler(doneHandler))
}

  @Override
  HttpServer setClientAuthRequired(boolean required) {
    jServer.setClientAuthRequired(required)
    this
  }

  @Override
  boolean isClientAuthRequired() {
    jServer.isClientAuthRequired()
  }

  @Override
  HttpServer setSSL(boolean ssl) {
    jServer.setSSL(ssl)
    this
  }

  @Override
  boolean isSSL() {
    jServer.isSSL()
  }

  @Override
  HttpServer setKeyStorePath(String path) {
    jServer.setKeyStorePath(path)
    this
  }

  @Override
  String getKeyStorePath() {
    jServer.getKeyStorePath()
  }

  @Override
  HttpServer setKeyStorePassword(String pwd) {
    jServer.setKeyStorePassword(pwd)
    this
  }

  @Override
  String getKeyStorePassword() {
    jServer.getKeyStorePassword()
  }

  @Override
  HttpServer setTrustStorePath(String path) {
    jServer.setTrustStorePath(path)
    this
  }

  @Override
  String getTrustStorePath() {
    jServer.getTrustStorePath()
  }

  @Override
  HttpServer setTrustStorePassword(String pwd) {
    jServer.setTrustStorePassword(pwd)
    this
  }

  @Override
  String getTrustStorePassword() {
    jServer.getTrustStorePassword()
  }

  @Override
  HttpServer setAcceptBacklog(int backlog) {
    jServer.setAcceptBacklog(backlog)
    this
  }

  @Override
  int getAcceptBacklog() {
    jServer.getAcceptBacklog()
  }

  @Override
  HttpServer setTCPNoDelay(boolean tcpNoDelay) {
    jServer.setTCPNoDelay(tcpNoDelay)
    this
  }

  @Override
  HttpServer setSendBufferSize(int size) {
    jServer.setSendBufferSize(size)
    this
  }

  @Override
  HttpServer setReceiveBufferSize(int size) {
    jServer.setReceiveBufferSize(size)
    this
  }

  @Override
  HttpServer setTCPKeepAlive(boolean keepAlive) {
    jServer.setTCPKeepAlive(keepAlive)
    this
  }

  @Override
  HttpServer setReuseAddress(boolean reuse) {
    jServer.setReuseAddress(reuse)
    this
  }

  @Override
  HttpServer setSoLinger(int linger) {
    jServer.setSoLinger(linger)
    this
  }

  @Override
  HttpServer setTrafficClass(int trafficClass) {
    jServer.setTrafficClass(trafficClass)
    this
  }

  @Override
  HttpServer setUsePooledBuffers(boolean pooledBuffers) {
    jServer.setUsePooledBuffers(pooledBuffers)
    this
  }
  
  @Override
  HttpServer setCompressionSupported(boolean compressionSupported) {
    jServer.setCompressionSupported(compressionSupported)
    this
  }
  

  @Override
  boolean isTCPNoDelay() {
    jServer.isTCPNoDelay()
  }

  @Override
  int getSendBufferSize() {
    jServer.getSendBufferSize()
  }

  @Override
  int getReceiveBufferSize() {
    jServer.getReceiveBufferSize()
  }

  @Override
  boolean isTCPKeepAlive() {
    jServer.isTCPKeepAlive()
  }

  @Override
  boolean isReuseAddress() {
    jServer.isReuseAddress()
  }

  @Override
  int getSoLinger() {
    jServer.getSoLinger()
  }

  @Override
  int getTrafficClass() {
    jServer.getTrafficClass()
  }

  @Override
  boolean isUsePooledBuffers() {
    jServer.isUsePooledBuffers()
  }

  @Override
  boolean isCompressionSupported() {
    jServer.isCompressionSupported()
  }

  /**
   * Get the Java instance
   *
   * @deprecated use  `HttpServer.jServer` notation instead.  
   */
  @Deprecated 
  JHttpServer toJavaServer() {
    jServer
  }
}
