package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap;
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpClientRequest
import org.vertx.groovy.core.impl.DefaultMultiMap
import org.vertx.java.core.Handler
import org.vertx.java.core.http.HttpClientRequest as JHttpClientRequest

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
class DefaultHttpClientRequest implements HttpClientRequest {

  private JHttpClientRequest jRequest
  private MultiMap headers
  DefaultHttpClientRequest(JHttpClientRequest jRequest) {
    this.jRequest = jRequest
  }

  @Override
  HttpClientRequest setChunked(boolean chunked) {
    jRequest.setChunked(chunked)
    this
  }

  @Override
  boolean isChunked() {
    jRequest.isChunked()
  }

  @Override
  MultiMap getHeaders() {
    if (headers == null) {
      headers = new DefaultMultiMap(jRequest.headers())
    }
    headers
  }

  @Override
  HttpClientRequest putHeader(String name, String value) {
    jRequest.putHeader(name, value)
    this
  }

  @Override
  HttpClientRequest putHeader(String name, Iterable<String> value) {
    jRequest.putHeader(name, value)
    this
  }

  @Override
  HttpClientRequest write(Buffer chunk) {
    jRequest.write(chunk.toJavaBuffer())
    this
  }

  @Override
  HttpClientRequest setWriteQueueMaxSize(int maxSize) {
    jRequest.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    jRequest.writeQueueFull()
  }

  @Override
  HttpClientRequest drainHandler(Closure handler) {
    jRequest.drainHandler(handler as Handler)
    this
  }

  @Override
  HttpClientRequest write(String chunk) {
    jRequest.write(chunk)
    this
  }

  @Override
  HttpClientRequest write(String chunk, String enc) {
    jRequest.write(chunk, enc)
    this
  }

  @Override
  HttpClientRequest continueHandler(Closure handler) {
    jRequest.continueHandler(handler as Handler)
    this
  }

  @Override
  HttpClientRequest sendHead() {
    jRequest.sendHead()
    this
  }

  @Override
  void end(String chunk) {
    jRequest.end(chunk)
  }

  @Override
  void end(String chunk, String enc) {
    jRequest.end(chunk, enc)
  }

  @Override
  void end(Buffer chunk) {
    jRequest.end(chunk.toJavaBuffer())
  }

  @Override
  void end() {
    jRequest.end()
  }

  @Override
  HttpClientRequest setTimeout(long timeoutMs) {
    jRequest.setTimeout(timeoutMs)
    this
  }

  @Override
  HttpClientRequest leftShift(Buffer buff) {
    return write(buff)
  }

  @Override
  HttpClientRequest leftShift(String str) {
    return write(str)
  }

  @Override
  HttpClientRequest exceptionHandler(Closure handler) {
    jRequest.exceptionHandler(handler as Handler)
    this
  }
}
