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

package org.vertx.groovy.core.streams

import groovy.transform.CompileStatic;

import org.vertx.groovy.core.buffer.Buffer

/**
 *
 * Represents a stream of data that can be written to.<p>
 *
 * Any class that implements this interface can be used by a {@link Pump} to pump data from a {@code ReadStream}
 * to it.<p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
interface WriteStream<T> extends DrainSupport, ExceptionSupport<T> {

  /**
   * Write some data to the stream. The data is put on an internal write queue, and the write actually happens
   * asynchronously. To avoid running out of memory by putting too much on the write queue,
   * check the {@link #isWriteQueueFull} method before writing. This is done automatically if using a {@link org.vertx.java.core.streams.Pump}.
   */
  T write(Buffer data);
}