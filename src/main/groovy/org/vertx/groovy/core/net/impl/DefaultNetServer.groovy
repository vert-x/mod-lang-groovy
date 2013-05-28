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

import groovy.transform.CompileStatic
import org.vertx.groovy.core.impl.ClosureUtil;
import org.vertx.groovy.core.net.NetServer
import org.vertx.java.core.Handler
import org.vertx.java.core.Vertx
import org.vertx.java.core.net.NetSocket as JNetSocket
import org.vertx.java.core.net.NetServer as JNetServer

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultNetServer implements NetServer {

  private JNetServer jNetServer

  DefaultNetServer(Vertx vertx, Map props = null) {
    jNetServer = vertx.createNetServer()
    if (props != null) {
      props.each { String k, v ->
        setProperty(k, v)
      }
    }
  }

  @Override
  NetServer connectHandler(Closure hndlr) {
    jNetServer.connectHandler({hndlr(new DefaultNetSocket((JNetSocket) it))} as Handler)
    this
  }

  @Override
  NetServer listen(int port) {
    jNetServer.listen(port)
    this
  }

  @Override
  NetServer listen(int port, String host) {
    jNetServer.listen(port, host)
    this
  }

  @Override
  NetServer listen(int port, Closure bindHandler) {
    jNetServer.listen(port, ClosureUtil.wrapAsyncResultHandler(bindHandler, {
      this
    }))
    this
  }

  @Override
  NetServer listen(int port, String host, Closure bindHandler) {
    jNetServer.listen(port, host, ClosureUtil.wrapAsyncResultHandler(bindHandler, {
      this
    }))
    this
  }

  @Override
  void close() {
    jNetServer.close()
  }

  @Override
  void close(Closure hndlr) {
    jNetServer.close(ClosureUtil.wrapAsyncResultHandler(hndlr))
  }

  @Override
  int getPort() {
    jNetServer.port()
  }

  @Override
  String getHost() {
    jNetServer.host()
  }

  @Override
  NetServer setClientAuthRequired(boolean required) {
    jNetServer.setClientAuthRequired(required)
    this
  }

  @Override
  boolean isClientAuthRequired() {
    jNetServer.isClientAuthRequired()
  }

  @Override
  NetServer setSSL(boolean ssl) {
    jNetServer.setSSL(ssl)
    this
  }

  @Override
  boolean isSSL() {
    jNetServer.isSSL()
  }

  @Override
  NetServer setKeyStorePath(String path) {
    jNetServer.setKeyStorePath(path)
    this
  }

  @Override
  String getKeyStorePath() {
    jNetServer.getKeyStorePath()
  }

  @Override
  NetServer setKeyStorePassword(String pwd) {
    jNetServer.setKeyStorePassword(pwd)
    this
  }

  @Override
  String getKeyStorePassword() {
    jNetServer.keyStorePassword
  }

  @Override
  NetServer setTrustStorePath(String path) {
    jNetServer.setTrustStorePath(path)
    this
  }

  @Override
  String getTrustStorePath() {
    jNetServer.getTrustStorePath()
  }

  @Override
  NetServer setTrustStorePassword(String pwd) {
    jNetServer.setTrustStorePassword(pwd)
    this
  }

  @Override
  String getTrustStorePassword() {
    jNetServer.getTrustStorePassword()
  }

  @Override
  NetServer setAcceptBacklog(int backlog) {
    jNetServer.setAcceptBacklog(backlog)
    this
  }

  @Override
  int getAcceptBacklog() {
    jNetServer.getAcceptBacklog()
  }

  @Override
  NetServer setTCPNoDelay(boolean tcpNoDelay) {
    jNetServer.setTCPNoDelay(tcpNoDelay)
    this
  }

  @Override
  NetServer setSendBufferSize(int size) {
    jNetServer.setSendBufferSize(size)
    this
  }

  @Override
  NetServer setReceiveBufferSize(int size) {
    jNetServer.setReceiveBufferSize(size)
    this
  }

  @Override
  NetServer setTCPKeepAlive(boolean keepAlive) {
    jNetServer.setTCPKeepAlive(keepAlive)
    this
  }

  @Override
  NetServer setReuseAddress(boolean reuse) {
    jNetServer.setReuseAddress(reuse)
    this
  }

  @Override
  NetServer setSoLinger(int linger) {
    jNetServer.setSoLinger(linger)
    this
  }

  @Override
  NetServer setTrafficClass(int trafficClass) {
    jNetServer.setTrafficClass(trafficClass)
    this
  }

  @Override
  NetServer setUsePooledBuffers(boolean pooledBuffers) {
    jNetServer.setUsePooledBuffers(pooledBuffers)
    this
  }

  @Override
  boolean isTCPNoDelay() {
    jNetServer.isTCPNoDelay()
  }

  @Override
  int getSendBufferSize() {
    jNetServer.getSendBufferSize()
  }

  @Override
  int getReceiveBufferSize() {
    jNetServer.getReceiveBufferSize()
  }

  @Override
  boolean isTCPKeepAlive() {
    jNetServer.isTCPKeepAlive()
  }

  @Override
  boolean isReuseAddress() {
    jNetServer.isReuseAddress()
  }

  @Override
  int getSoLinger() {
    jNetServer.getSoLinger()
  }

  @Override
  int getTrafficClass() {
    jNetServer.getTrafficClass()
  }

  @Override
  boolean isUsePooledBuffers() {
    jNetServer.isUsePooledBuffers()
  }
}
