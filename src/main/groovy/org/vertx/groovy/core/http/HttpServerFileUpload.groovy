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
package org.vertx.groovy.core.http

import groovy.transform.CompileStatic
import org.vertx.groovy.core.streams.ReadStream

import java.nio.charset.Charset

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
interface HttpServerFileUpload extends ReadStream<HttpServerFileUpload> {
  /**
   * Stream the content of this upload to the given filename.
   */
  HttpServerFileUpload streamToFileSystem(String filename);

  /**
   * Returns the filename which was used when upload the file.
   */
  String getFilename();

  /**
   * Returns the name of the attribute
   */
  String getName();

  /**
   * Returns the contentType for the upload
   */
  String getContentType();

  /**
   * Returns the contentTransferEncoding for the upload
   */
  String getContentTransferEncoding();

  /**
   * Returns the charset for the upload
   */
  Charset getCharset();

  /**
   * Returns the size of the upload (in bytes)
   */
  long getSize();
}
