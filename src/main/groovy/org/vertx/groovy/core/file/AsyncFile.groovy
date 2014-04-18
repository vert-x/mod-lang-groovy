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

package org.vertx.groovy.core.file

import groovy.transform.CompileStatic;
import groovy.transform.TypeCheckingMode;

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.impl.ClosureUtil
import org.vertx.groovy.core.streams.ReadStream
import org.vertx.groovy.core.streams.WriteStream
import org.vertx.java.core.Handler
import org.vertx.java.core.buffer.Buffer as JBuffer
import org.vertx.java.core.file.AsyncFile as JAsyncFile

/**
 * Represents a file on the file-system which can be read from, or written to asynchronously.<p>
 * It also implements {@link org.vertx.groovy.core.streams.ReadStream} and
 * {@link org.vertx.java.core.streams.WriteStream}. This allows the data to be pumped to and from
 * other streams, e.g. an {@link org.vertx.groovy.core.http.HttpClientRequest} instance,
 * using the {@link org.vertx.groovy.core.streams.Pump} class<p>
 * Instances of this class are not thread-safe<p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class AsyncFile implements ReadStream<AsyncFile>, WriteStream<AsyncFile> {

  private final JAsyncFile jFile

  AsyncFile(JAsyncFile jFile) {
    this.jFile = jFile
  }

  /**
   * Close the file. The actual close happens asynchronously.
   */
  void close() {
    jFile.close()
  }

  /**
   * Close the file. The actual close happens asynchronously.
   * The handler will be called when the close is complete, or an error occurs.
   */
  void close(Closure handler) {
    jFile.close(ClosureUtil.wrapAsyncResultHandler(handler))
  }

  /**
   * Write a {@link Buffer} to the file at position {@code position} in the file, asynchronously.
   * If {@code position} lies outside of the current size
   * of the file, the file will be enlarged to encompass it.<p>
   * When multiple writes are invoked on the same file
   * there are no guarantees as to order in which those writes actually occur.<p>
   * The handler will be called when the close is complete, or an error occurs.
   */
  AsyncFile write(Buffer buffer, int position, Closure handler) {
    jFile.write(buffer.toJavaBuffer(), position, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Reads {@code length} bytes of data from the file at position {@code position} in the file, asynchronously.
   * The read data will be written into the specified {@code Buffer buffer} at position {@code offset}.<p>
   * The index {@code position + length} must lie within the confines of the file.<p>
   * When multiple reads are invoked on the same file there are no guarantees as to order in which those reads actually occur.<p>
   * The handler will be called when the close is complete, or if an error occurs.
   */
  AsyncFile read(Buffer buffer, int offset, int position, int length, Closure handler) {
    jFile.read(buffer.toJavaBuffer(), offset, position, length, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  AsyncFile write(Buffer data) {
    jFile.write(data.toJavaBuffer())
    this
  }

  void leftShift(Buffer data) {
    write(data)
  }

  AsyncFile setWriteQueueMaxSize(int maxSize) {
    jFile.setWriteQueueMaxSize(maxSize)
    this
  }

  boolean isWriteQueueFull() {
    return jFile.writeQueueFull()
  }

  AsyncFile drainHandler(Closure handler) {
    jFile.drainHandler(handler as Handler)
    this
  }

  AsyncFile dataHandler(Closure handler) {
    jFile.dataHandler({handler(new Buffer((JBuffer) it))} as Handler)
    this
  }

  AsyncFile pause() {
    jFile.pause()
    this
  }

  AsyncFile resume() {
    jFile.resume()
    this
  }

  @CompileStatic(TypeCheckingMode.SKIP)
  AsyncFile exceptionHandler(Closure handler) {
    jFile.exceptionHandler(handler as Handler)
    this
  }

  AsyncFile endHandler(Closure handler) {
    jFile.endHandler(handler as Handler)
    this
  }

  /**
   * Flush any writes made to this file to underlying persistent storage.<p>
   * If the file was opened with {@code flush} set to {@code true} then calling this method will have no effect.<p>
   * The actual flush will happen asynchronously.
   */
  AsyncFile flush() {
    jFile.flush()
    this
  }

  /**
   * Same as {@link #flush} but the handler will be called when the flush is complete
   * or an error occurs
   * @param handler
   */
  AsyncFile flush(Closure handler) {
    jFile.flush(ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }
}
