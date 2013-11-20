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
import org.vertx.groovy.core.MultiMap
import org.vertx.groovy.core.http.HttpClient
import org.vertx.groovy.core.http.HttpClientRequest
import org.vertx.groovy.core.impl.DefaultMultiMap
import org.vertx.java.core.Handler
import org.vertx.java.core.Vertx
import org.vertx.java.core.http.HttpClient as JClient
import org.vertx.java.core.http.HttpClientResponse as JHttpClientResponse
import org.vertx.java.core.http.WebSocket as JWebSocket
import org.vertx.java.core.http.WebSocketVersion

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultHttpClient implements HttpClient {

  private JClient jClient

  DefaultHttpClient(Vertx vertx, Map props = null) {
    jClient = vertx.createHttpClient()
    if (props != null) {
      props.each { String k, v ->
        setProperty(k, v)
      }
    }
  }

  @Override
  HttpClient exceptionHandler(Closure handler) {
    jClient.exceptionHandler(handler as Handler)
    this
  }

  @Override
  HttpClient setMaxPoolSize(int maxConnections) {
    jClient.setMaxPoolSize(maxConnections)
    this
  }

  @Override
  int getMaxPoolSize() {
    jClient.getMaxPoolSize()
  }

  @Override
  HttpClient setKeepAlive(boolean keepAlive) {
    jClient.setKeepAlive(keepAlive)
    this
  }

  @Override
  boolean isKeepAlive() {
    jClient.isKeepAlive()
  }

  @Override
  HttpClient setPort(int port) {
    jClient.setPort(port)
    this
  }

  @Override
  int getPort() {
    jClient.getPort()
  }

  @Override
  HttpClient setHost(String host) {
    jClient.setHost(host)
    this
  }

  @Override
  String getHost() {
    jClient.getHost()
  }

  @Override
  HttpClient connectWebsocket(String uri, Closure wsConnect) {
    jClient.connectWebsocket(uri, {wsConnect(new DefaultWebSocket((JWebSocket) it))} as Handler)
    this
  }

  @Override
  HttpClient connectWebsocket(String uri, WebSocketVersion wsVersion, Closure wsConnect) {
    jClient.connectWebsocket(uri, wsVersion, {wsConnect(new DefaultWebSocket((JWebSocket) it))} as Handler)
    this
  }

  @Override
  HttpClient getNow(String uri, Closure responseHandler) {
    jClient.getNow(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler)
    this
  }

  @Override
  HttpClient getNow(String uri, MultiMap headers, Closure responseHandler) {
    jClient.getNow(uri, DefaultMultiMap.toJavaMultiMap(headers), {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler)
    this
  }

  @Override
  HttpClientRequest options(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.options(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest get(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.get(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest head(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.head(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest post(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.post(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest put(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.put(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest delete(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.delete(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest trace(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.trace(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest connect(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.connect(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest patch(String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.patch(uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  HttpClientRequest request(String method, String uri, Closure responseHandler) {
    return new DefaultHttpClientRequest(jClient.request(method, uri, {responseHandler(new DefaultHttpClientResponse((JHttpClientResponse) it))} as Handler))
  }

  @Override
  void close() {
    jClient.close()
  }

  @Override
  HttpClient setVerifyHost(boolean verifyHost) {
    jClient.setVerifyHost(verifyHost)
    this
  }

  @Override
  boolean isVerifyHost() {
    jClient.isVerifyHost()
  }

  @Override
  HttpClient setMaxWebSocketFrameSize(int maxSize) {
    jClient.setMaxWebSocketFrameSize( maxSize )
    this
  }
 
  @Override
  int getMaxWebSocketFrameSize() {
    jClient.getMaxWebSocketFrameSize()
  }

  @Override
  HttpClient setTrustAll(boolean trustAll) {
    jClient.setTrustAll(trustAll)
    this
  }

  @Override
  boolean isTrustAll() {
    jClient.isTrustAll()
  }

  @Override
  HttpClient setSSL(boolean ssl) {
    jClient.setSSL(ssl)
    this
  }

  @Override
  boolean isSSL() {
    jClient.isSSL()
  }

  @Override
  HttpClient setKeyStorePath(String path) {
    jClient.setKeyStorePath(path)
    this
  }

  @Override
  String getKeyStorePath() {
    jClient.getKeyStorePath()
  }

  @Override
  HttpClient setKeyStorePassword(String pwd) {
    jClient.setKeyStorePassword(pwd)
    this
  }

  @Override
  String getKeyStorePassword() {
    jClient.getKeyStorePassword()
  }

  @Override
  HttpClient setTrustStorePath(String path) {
    jClient.setTrustStorePath(path)
    this
  }

  @Override
  String getTrustStorePath() {
    jClient.getTrustStorePath()
  }

  @Override
  HttpClient setTrustStorePassword(String pwd) {
    jClient.setTrustStorePassword(pwd)
    this
  }

  @Override
  String getTrustStorePassword() {
    jClient.getTrustStorePassword()
  }

  @Override
  HttpClient setTCPNoDelay(boolean tcpNoDelay) {
    jClient.setTCPNoDelay(tcpNoDelay)
    this
  }

  @Override
  HttpClient setSendBufferSize(int size) {
    jClient.setSendBufferSize(size)
    this
  }

  @Override
  HttpClient setReceiveBufferSize(int size) {
    jClient.setReceiveBufferSize(size)
    this
  }

  @Override
  HttpClient setTCPKeepAlive(boolean keepAlive) {
    jClient.setTCPKeepAlive(keepAlive)
    this
  }

  @Override
  HttpClient setReuseAddress(boolean reuse) {
    jClient.setReuseAddress(reuse)
    this
  }

  @Override
  HttpClient setSoLinger(int linger) {
    jClient.setSoLinger(linger)
    this
  }

  @Override
  HttpClient setTrafficClass(int trafficClass) {
    jClient.setTrafficClass(trafficClass)
    this
  }

  @Override
  HttpClient setUsePooledBuffers(boolean pooledBuffers) {
    jClient.setUsePooledBuffers(pooledBuffers)
    this
  }
  
  @Override
  HttpClient setTryUseCompression(boolean tryUseCompression) {
    jClient.setTryUseCompression(tryUseCompression)
    this
  }

  @Override
  boolean isTCPNoDelay() {
    jClient.isTCPNoDelay()
  }

  @Override
  int getSendBufferSize() {
    jClient.getSendBufferSize()
  }

  @Override
  int getReceiveBufferSize() {
    jClient.getReceiveBufferSize()
  }

  @Override
  boolean isTCPKeepAlive() {
    jClient.isTCPKeepAlive()
  }

  @Override
  boolean isReuseAddress() {
    jClient.isReuseAddress()
  }

  @Override
  int getSoLinger() {
    jClient.getSoLinger()
  }

  @Override
  int getTrafficClass() {
    jClient.getTrafficClass()
  }

  @Override
  boolean isUsePooledBuffers() {
    jClient.isUsePooledBuffers()
  }
   
  @Override
  boolean getTryUseCompression() {
    jClient.getTryUseCompression()
  }
  
}
