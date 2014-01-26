package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.WebSocket
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer
import org.vertx.java.core.http.WebSocket as JWebSocket

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
class DefaultWebSocket implements WebSocket {

  protected JWebSocket jWebSocket

  DefaultWebSocket(JWebSocket jWebSocket) {
    this.jWebSocket = jWebSocket
  }

  @Override
  String getBinaryHandlerID() {
    jWebSocket.binaryHandlerID()
  }

  @Override
  String getTextHandlerID() {
    jWebSocket.textHandlerID()
  }

  @Override
  WebSocket writeBinaryFrame(Buffer data) {
    jWebSocket.writeBinaryFrame(data.toJavaBuffer())
    this
  }

  @Override
  WebSocket writeTextFrame(String str) {
    jWebSocket.writeTextFrame(str)
    this
  }

  @Override
  WebSocket closeHandler(Closure handler) {
    jWebSocket.closeHandler(handler as Handler)
    this
  }

  @Override
  void close() {
    jWebSocket.close()
  }

  @Override
  WebSocket leftShift(Buffer buff) {
    return write(buff)
  }

  @Override
  WebSocket leftShift(String str) {
    return write(new Buffer(str))
  }

  @Override
  WebSocket dataHandler(Closure handler) {
    jWebSocket.dataHandler({handler(new Buffer((JBuffer) it))} as Handler)
    this
  }

  @Override
  WebSocket pause() {
    jWebSocket.pause()
    this
  }

  @Override
  WebSocket resume() {
    jWebSocket.resume()
    this
  }

  @Override
  WebSocket endHandler(Closure endHandler) {
    jWebSocket.endHandler(endHandler as Handler)
    this
  }

  @Override
  WebSocket write(Buffer data) {
    jWebSocket.write(data.toJavaBuffer())
    this
  }

  @Override
  WebSocket setWriteQueueMaxSize(int maxSize) {
    jWebSocket.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    jWebSocket.writeQueueFull()
  }

  @Override
  WebSocket drainHandler(Closure handler) {
    jWebSocket.drainHandler(handler as Handler)
    this
  }

  @Override
  @CompileStatic(TypeCheckingMode.SKIP)
  WebSocket exceptionHandler(Closure handler) {
    jWebSocket.exceptionHandler(handler as Handler)
    this
  }

  @Override
  InetSocketAddress localAddress() {
    jWebSocket.localAddress()
  }

  @Override
  InetSocketAddress remoteAddress() {
    jWebSocket.remoteAddress()
  }

}
