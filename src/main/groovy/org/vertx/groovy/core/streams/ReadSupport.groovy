/*
 * Copyright 2013 the original author or authors.
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

import groovy.transform.CompileStatic


@CompileStatic
interface ReadSupport<T> {

  /**
   * Set a data handler. As data is read, the handler will be called with the data.
   */
  T dataHandler(Closure handler);

  /**
   * Pause the {@code ReadStream}. While the stream is paused, no data will be sent to the {@code dataHandler}
   */
  T pause();

  /**
   * Resume reading. If the {@code ReadStream} has been paused, reading will recommence on it.
   */
  T resume();

}