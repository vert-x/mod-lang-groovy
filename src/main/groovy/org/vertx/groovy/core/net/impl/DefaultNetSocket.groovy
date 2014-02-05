package org.vertx.groovy.core.net.impl

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.net.NetSocket
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer
import org.vertx.java.core.net.NetSocket as JNetSocket

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
class DefaultNetSocket implements NetSocket {

  private JNetSocket jNetSocket

  DefaultNetSocket(JNetSocket jNetSocket) {
    this.jNetSocket = jNetSocket
  }

  @Override
  String getWriteHandlerID() {
    jNetSocket.writeHandlerID()
  }

  @Override
  NetSocket write(Buffer data) {
    jNetSocket.write(data.toJavaBuffer())
    this
  }

  @Override
  NetSocket setWriteQueueMaxSize(int maxSize) {
    jNetSocket.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    jNetSocket.writeQueueFull()
  }

  @Override
  NetSocket drainHandler(Closure handler) {
    jNetSocket.drainHandler(handler as Handler)
    this
  }

  @Override
  NetSocket write(String str) {
    jNetSocket.write(str)
    this
  }

  @Override
  NetSocket write(String str, String enc) {
    jNetSocket.write(str, enc)
    this
  }

  @Override
  NetSocket sendFile(String filename) {
    jNetSocket.sendFile(filename)
    this
  }

  @Override
  NetSocket sendFile(String filename, Closure handler) {
    jNetSocket.sendFile(filename, handler as Handler)
    this
  }

  @Override
  InetSocketAddress getRemoteAddress() {
    jNetSocket.remoteAddress()
  }

  @Override
  InetSocketAddress getLocalAddress() {
    jNetSocket.localAddress()
  }

  @Override
  void close() {
    jNetSocket.close()
  }

  @Override
  void closeHandler(Closure handler) {
    jNetSocket.closeHandler(handler as Handler)
  }

  @Override
  NetSocket leftShift(Buffer buff) {
    return write(buff)
  }

  @Override
  NetSocket leftShift(String str) {
    return write(str)
  }

  @Override
  NetSocket dataHandler(Closure handler) {
    jNetSocket.dataHandler({
      handler(new Buffer((JBuffer) it))
    } as Handler)
    return null
  }

  @Override
  NetSocket pause() {
    jNetSocket.pause()
    this
  }

  @Override
  NetSocket resume() {
    jNetSocket.resume()
    this
  }

  @Override
  NetSocket endHandler(Closure endHandler) {
    jNetSocket.endHandler(endHandler as Handler)
    this
  }

  @Override
  @CompileStatic(TypeCheckingMode.SKIP)
  NetSocket exceptionHandler(Closure handler) {
    jNetSocket.exceptionHandler(handler as Handler)
    this
  }
}
