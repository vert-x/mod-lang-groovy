package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap;
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpServerResponse
import org.vertx.groovy.core.impl.DefaultMultiMap
import org.vertx.java.core.Handler

import org.vertx.java.core.http.HttpServerResponse as JHttpServerResponse

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
class DefaultHttpServerResponse implements HttpServerResponse {

  private JHttpServerResponse jResponse
  private MultiMap headers
  private MultiMap trailers

  DefaultHttpServerResponse(org.vertx.java.core.http.HttpServerResponse jResponse) {
    this.jResponse = jResponse
  }

  @Override
  int getStatusCode() {
    jResponse.statusCode
  }

  @Override
  HttpServerResponse setStatusCode(int statusCode) {
    jResponse.setStatusCode(statusCode)
    this
  }

  @Override
  String getStatusMessage() {
    jResponse.getStatusMessage()
  }

  @Override
  HttpServerResponse setStatusMessage(String statusMessage) {
    jResponse.setStatusMessage(statusMessage)
    this
  }

  @Override
  HttpServerResponse setChunked(boolean chunked) {
    jResponse.setChunked(chunked)
    this
  }

  @Override
  boolean isChunked() {
    jResponse.isChunked()
  }

  @Override
  MultiMap getHeaders() {
    if (headers == null) {
      headers = new DefaultMultiMap(jResponse.headers())
    }
    headers
  }

  @Override
  HttpServerResponse putHeader(String name, String value) {
    jResponse.putHeader(name, value)
    this
  }

  @Override
  HttpServerResponse putHeader(String name, Iterable<String> value) {
    jResponse.putHeader(name, value)
    this
  }

  @Override
  MultiMap getTrailers() {
    if (trailers == null) {
      trailers = new DefaultMultiMap(jResponse.trailers())
    }
    trailers
  }

  @Override
  HttpServerResponse putTrailer(String name, String value) {
    jResponse.putTrailer(name, value)
    this
  }

  @Override
  HttpServerResponse putTrailer(String name, Iterable<String> value) {
    jResponse.putTrailer(name, value)
    this
  }

  @Override
  void closeHandler(Closure handler) {
    jResponse.closeHandler(handler as Handler)
  }

  @Override
  HttpServerResponse write(Buffer chunk) {
    jResponse.write(chunk.toJavaBuffer())
    this
  }

  @Override
  HttpServerResponse setWriteQueueMaxSize(int maxSize) {
    jResponse.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    jResponse.writeQueueFull()
  }

  @Override
  HttpServerResponse drainHandler(Closure handler) {
    jResponse.drainHandler(handler as Handler)
    this
  }

  @Override
  HttpServerResponse write(String chunk, String enc) {
    jResponse.write(chunk, enc)
    this
  }

  @Override
  HttpServerResponse write(String chunk) {
    jResponse.write(chunk)
    this
  }

  @Override
  void end(String chunk) {
    jResponse.end(chunk)
  }

  @Override
  void end(String chunk, String enc) {
    jResponse.end(chunk, enc)
  }

  @Override
  void end(Buffer chunk) {
    jResponse.end(chunk.toJavaBuffer())
  }

  @Override
  void end() {
    jResponse.end()
  }

  @Override
  HttpServerResponse sendFile(String filename) {
    jResponse.sendFile(filename)
    this
  }

  @Override
  HttpServerResponse sendFile(String filename, String notFoundFile) {
    jResponse.sendFile(filename, notFoundFile)
    this
  }

  @Override
  void close() {
    jResponse.close()
  }

  @Override
  HttpServerResponse leftShift(Buffer buff) {
    return write(buff)
  }

  @Override
  HttpServerResponse leftShift(String str) {
    return write(str)
  }

  @Override
  HttpServerResponse exceptionHandler(Closure handler) {
    jResponse.exceptionHandler(handler as Handler)
    this
  }
}
