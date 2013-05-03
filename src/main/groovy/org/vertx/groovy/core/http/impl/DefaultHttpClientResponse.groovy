package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpClientResponse
import org.vertx.groovy.core.impl.DefaultMultiMap
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer

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
class DefaultHttpClientResponse implements HttpClientResponse {

  private org.vertx.java.core.http.HttpClientResponse jResponse
  private MultiMap headers
  private MultiMap trailers
  DefaultHttpClientResponse(org.vertx.java.core.http.HttpClientResponse jResponse) {
    this.jResponse = jResponse
  }

  @Override
  int getStatusCode() {
    jResponse.statusCode()
  }

  @Override
  String getStatusMessage() {
    jResponse.statusMessage()
  }

  @Override
  MultiMap getHeaders() {
    if (headers == null) {
      headers = new DefaultMultiMap(jResponse.headers())
    }
    headers
  }

  @Override
  MultiMap getTrailers() {
    if (trailers == null) {
      trailers = new DefaultMultiMap(jResponse.trailers())
    }
    trailers
  }

  @Override
  List<String> getCookies() {
    jResponse.cookies()
  }

  @Override
  HttpClientResponse bodyHandler(Closure handler) {
    jResponse.bodyHandler(({handler(new Buffer((JBuffer) it))} as Handler))
    this
  }

  @Override
  HttpClientResponse dataHandler(Closure handler) {
    jResponse.dataHandler(({handler(new Buffer((JBuffer) it))} as Handler))
    this
  }

  @Override
  HttpClientResponse pause() {
    jResponse.pause()
    this
  }

  @Override
  HttpClientResponse resume() {
    jResponse.resume()
    this
  }

  @Override
  HttpClientResponse endHandler(Closure endHandler) {
    jResponse.endHandler(endHandler as Handler)
    this
  }

  @Override
  HttpClientResponse exceptionHandler(Closure handler) {
    jResponse.exceptionHandler(handler as Handler)
    this
  }
}
