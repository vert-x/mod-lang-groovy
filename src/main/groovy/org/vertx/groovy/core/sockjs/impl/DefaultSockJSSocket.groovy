package org.vertx.groovy.core.sockjs.impl

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.sockjs.SockJSSocket
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer

import java.net.InetSocketAddress

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultSockJSSocket implements SockJSSocket {

  private final org.vertx.java.core.sockjs.SockJSSocket jSocket

  DefaultSockJSSocket(org.vertx.java.core.sockjs.SockJSSocket jSocket) {
    this.jSocket = jSocket
  }

  @Override
  String getWriteHandlerID() {
    jSocket.writeHandlerID()
  }

  @Override
  void close() {
    jSocket.close()
  }

  @Override
  SockJSSocket leftShift(Buffer buff) {
    return write(buff)
  }

  @Override
  SockJSSocket leftShift(String str) {
    return write(new Buffer(str))
  }

  @Override
  SockJSSocket dataHandler(Closure handler) {
    jSocket.dataHandler({handler(new Buffer((JBuffer) it))} as Handler)
    this
  }

  @Override
  SockJSSocket pause() {
    jSocket.pause()
    this
  }

  @Override
  SockJSSocket resume() {
    jSocket.resume()
    this
  }

  @Override
  SockJSSocket endHandler(Closure endHandler) {
    jSocket.endHandler(endHandler as Handler)
    this
  }

  @Override
  SockJSSocket write(Buffer data) {
    jSocket.write(data.toJavaBuffer())
    this
  }

  @Override
  SockJSSocket setWriteQueueMaxSize(int maxSize) {
    jSocket.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    jSocket.writeQueueFull()
  }

  @Override
  SockJSSocket drainHandler(Closure handler) {
    jSocket.drainHandler(handler as Handler)
    this
  }

  @Override
  @CompileStatic(TypeCheckingMode.SKIP)
  SockJSSocket exceptionHandler(Closure handler) {
    jSocket.exceptionHandler(handler as Handler)
    this
  }

  @Override
  InetSocketAddress localAddress() {
    jSocket.localAddress()
  }

  @Override
  InetSocketAddress remoteAddress() {
    jSocket.remoteAddress()
  }

}
