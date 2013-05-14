/**
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
 */
package org.vertx.groovy.core.http.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpServerFileUpload
import org.vertx.java.core.Handler
import org.vertx.java.core.http.HttpServerFileUpload as JHttpServerFileUpload

import java.nio.charset.Charset

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class DefaultHttpServerFileUpload implements HttpServerFileUpload {
  private final JHttpServerFileUpload jupload;

  DefaultHttpServerFileUpload(JHttpServerFileUpload jupload) {
    this.jupload = jupload
  }

  @Override
  HttpServerFileUpload streamToFileSystem(String filename) {
    jupload.streamToFileSystem(filename);
    this
  }

  @Override
  String getFilename() {
    jupload.filename()
  }

  @Override
  String getName() {
    jupload.name()
  }

  @Override
  String getContentType() {
    jupload.contentType()
  }

  @Override
  String getContentTransferEncoding() {
    jupload.contentTransferEncoding()
  }

  @Override
  Charset getCharset() {
    jupload.charset()
  }

  @Override
  long getSize() {
    jupload.size()
  }

  @Override
  HttpServerFileUpload dataHandler(Closure handler) {
    jupload.dataHandler(({handler(new Buffer((org.vertx.java.core.buffer.Buffer) it))} as Handler))
    this
  }

  @Override
  HttpServerFileUpload pause() {
    jupload.pause()
    this
  }

  @Override
  HttpServerFileUpload resume() {
    jupload.resume()
    this
  }

  @Override
  HttpServerFileUpload endHandler(Closure endHandler) {
    jupload.endHandler(endHandler as Handler)
    this
  }

  @Override
  HttpServerFileUpload exceptionHandler(Closure handler) {
    jupload.exceptionHandler(handler as Handler)
    this
  }
}
