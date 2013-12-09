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
 */
package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpServerRequest
import org.vertx.groovy.core.http.HttpServerResponse
import org.vertx.groovy.core.impl.DefaultMultiMap
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer
import org.vertx.java.core.http.HttpServerFileUpload
import org.vertx.java.core.http.HttpServerRequest as JHttpServerRequest
import org.vertx.java.core.http.HttpVersion

import javax.net.ssl.SSLPeerUnverifiedException
import javax.security.cert.X509Certificate


/**
 * 
 * @author <a href="http://tfox.org">Tim Fox</a>
 *
 */
@CompileStatic
class DefaultHttpServerRequest implements HttpServerRequest {

  // Putting it as a public final groovy property to be able to use `DefaultHttpServerRequest.jRequest` notation
  final JHttpServerRequest jRequest

  private MultiMap headers;
  private MultiMap params;
  private MultiMap attrs;
  private HttpServerResponse response

  DefaultHttpServerRequest(JHttpServerRequest jRequest) {
    this.jRequest = jRequest
    this.response = new DefaultHttpServerResponse(jRequest.response())
  }

  @Override
  HttpVersion getVersion() {
    jRequest.version()
  }

  @Override
  String getMethod() {
    jRequest.method()
  }

  @Override
  String getUri() {
    jRequest.uri()
  }

  @Override
  String getPath() {
    jRequest.path()
  }

  @Override
  String getQuery() {
    jRequest.query()
  }

  @Override
  HttpServerResponse getResponse() {
    response
  }

  @Override
  MultiMap getHeaders() {
    if (headers == null) {
      headers = new DefaultMultiMap(jRequest.headers());
    }
    headers
  }

  @Override
  MultiMap getParams() {
    if (params == null) {
      params = new DefaultMultiMap(jRequest.params());
    }
    params
  }

  @Override
  InetSocketAddress getRemoteAddress() {
    jRequest.remoteAddress()
  }

  @Override
  X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
    jRequest.peerCertificateChain()
  }

  @Override
  URI getAbsoluteURI() {
    jRequest.absoluteURI()
  }

  @Override
  HttpServerRequest bodyHandler(Closure handler) {
    jRequest.bodyHandler(({handler(new Buffer((JBuffer) it))} as Handler))
    this
  }

  @Override
  HttpServerRequest dataHandler(Closure handler) {
    jRequest.dataHandler(({handler(new Buffer((JBuffer) it))} as Handler))
    this
  }

  @Override
  HttpServerRequest pause() {
    jRequest.pause()
    this
  }

  @Override
  HttpServerRequest resume() {
    jRequest.resume()
    this
  }

  @Override
  HttpServerRequest endHandler(Closure endHandler) {
    jRequest.endHandler(endHandler as Handler)
    this
  }

  @Override
  HttpServerRequest exceptionHandler(Closure handler) {
    jRequest.exceptionHandler(handler as Handler)
    this
  }

  @Override
  HttpServerRequest setExpectMultiPart(boolean expect) {
    jRequest.expectMultiPart(expect);
    this
  }

  @Override
  HttpServerRequest uploadHandler(Closure handler) {
    jRequest.uploadHandler(({handler(new DefaultHttpServerFileUpload((HttpServerFileUpload) it))} as Handler))
    this
  }

  @Override
  MultiMap getFormAttributes() {
    if (attrs == null) {
      attrs = new DefaultMultiMap(jRequest.formAttributes());
    }
    attrs
  }

  /**
   * Get the Java instance
   *
   * @deprecated use  `DefaultHttpServerRequest.jRequest` notation instead.  
   */
  @Deprecated 
  JHttpServerRequest toJavaRequest() {
    jRequest
  }

}
