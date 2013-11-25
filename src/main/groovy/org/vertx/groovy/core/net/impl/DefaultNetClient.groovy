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

package org.vertx.groovy.core.net.impl

import groovy.transform.CompileStatic;

import org.vertx.groovy.core.net.NetClient
import org.vertx.groovy.core.net.NetSocket
import org.vertx.java.core.AsyncResult
import org.vertx.java.core.AsyncResultHandler
import org.vertx.java.core.Vertx
import org.vertx.java.core.impl.DefaultFutureResult
import org.vertx.java.core.net.NetClient as JNetClient
import org.vertx.java.core.net.NetSocket as JNetSocket

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultNetClient implements NetClient {

  private JNetClient jNetClient

  DefaultNetClient(Vertx vertx, Map props = null) {
    jNetClient = vertx.createNetClient()
    if (props != null) {
      props.each { String k, v ->
        setProperty(k, v)
      }
    }
  }

  @Override
  NetClient connect(int port, Closure hndlr) {
    jNetClient.connect(port, wrapConnectHandler(hndlr))
    this
  }

  @Override
  NetClient connect(int port, String host, Closure hndlr) {
    jNetClient.connect(port, host, wrapConnectHandler(hndlr))
    this
  }

  @Override
  NetClient setReconnectAttempts(int attempts) {
    jNetClient.setReconnectAttempts(attempts)
    this
  }

  @Override
  int getReconnectAttempts() {
    jNetClient.getReconnectAttempts()
  }

  @Override
  NetClient setReconnectInterval(long interval) {
    jNetClient.setReconnectInterval(interval)
    this
  }

  @Override
  long getReconnectInterval() {
    jNetClient.getReconnectInterval()
  }

  @Override
  NetClient setConnectTimeout(int timeout) {
    jNetClient.setConnectTimeout(timeout)
    this
  }

  @Override
  int getConnectTimeout() {
    jNetClient.getConnectTimeout()
  }

  @Override
  void close() {
    jNetClient.close()
  }

  @Override
  NetClient setTrustAll(boolean trustAll) {
    jNetClient.setTrustAll(trustAll)
    this
  }

  @Override
  boolean isTrustAll() {
    jNetClient.isTrustAll()
  }

  @Override
  NetClient setSSL(boolean ssl) {
    jNetClient.setSSL(ssl)
    this
  }

  @Override
  boolean isSSL() {
    jNetClient.isSSL()
  }

  @Override
  NetClient setKeyStorePath(String path) {
    jNetClient.setKeyStorePath(path)
    this
  }

  @Override
  String getKeyStorePath() {
    jNetClient.getKeyStorePath()
  }

  @Override
  NetClient setKeyStorePassword(String pwd) {
    jNetClient.setKeyStorePassword(pwd)
    this
  }

  @Override
  String getKeyStorePassword() {
    jNetClient.getKeyStorePassword()
  }

  @Override
  NetClient setTrustStorePath(String path) {
    jNetClient.setTrustStorePath(path)
    this
  }

  @Override
  String getTrustStorePath() {
    jNetClient.getTrustStorePath()
  }

  @Override
  NetClient setTrustStorePassword(String pwd) {
    jNetClient.setTrustStorePassword(pwd)
    this
  }

  @Override
  String getTrustStorePassword() {
    jNetClient.getTrustStorePassword()
  }

  @Override
  NetClient setTCPNoDelay(boolean tcpNoDelay) {
    jNetClient.setTCPNoDelay(tcpNoDelay)
    this
  }

  @Override
  NetClient setSendBufferSize(int size) {
    jNetClient.setSendBufferSize(size)
    this
  }

  @Override
  NetClient setReceiveBufferSize(int size) {
    jNetClient.setReceiveBufferSize(size)
    this
  }

  @Override
  NetClient setTCPKeepAlive(boolean keepAlive) {
    jNetClient.setTCPKeepAlive(keepAlive)
    this
  }

  @Override
  NetClient setReuseAddress(boolean reuse) {
    jNetClient.setReuseAddress(reuse)
    this
  }

  @Override
  NetClient setSoLinger(int linger) {
    jNetClient.setSoLinger(linger)
    this
  }

  @Override
  NetClient setTrafficClass(int trafficClass) {
    jNetClient.setTrafficClass(trafficClass)
    this
  }

  @Override
  NetClient setUsePooledBuffers(boolean pooledBuffers) {
    jNetClient.setUsePooledBuffers(pooledBuffers)
    this
  }

  @Override
  boolean isTCPNoDelay() {
    jNetClient.isTCPNoDelay()
  }

  @Override
  int getSendBufferSize() {
    jNetClient.getSendBufferSize()
  }

  @Override
  int getReceiveBufferSize() {
    jNetClient.getReceiveBufferSize()
  }

  @Override
  boolean isTCPKeepAlive() {
    jNetClient.isTCPKeepAlive()
  }

  @Override
  boolean isReuseAddress() {
    jNetClient.isReuseAddress()
  }

  @Override
  int getSoLinger() {
    jNetClient.getSoLinger()
  }

  @Override
  int getTrafficClass() {
    jNetClient.getTrafficClass()
  }

  @Override
  boolean isUsePooledBuffers() {
    jNetClient.isUsePooledBuffers()
  }

  private AsyncResultHandler wrapConnectHandler(Closure hndlr) {
    { AsyncResult ar ->
      if (ar.succeeded()) {
        hndlr(new DefaultFutureResult<NetSocket>(new DefaultNetSocket((JNetSocket) ar.result())))
      } else {
        hndlr(ar)
      }
    } as AsyncResultHandler
  }

}
