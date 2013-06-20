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
package org.vertx.groovy.core

import groovy.transform.CompileStatic
import org.vertx.java.core.AsyncResult as JAsyncResult

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class AsyncResult<T> {
  private final JAsyncResult<T> result;

  AsyncResult(JAsyncResult<T> result) {
    this.result = result
  }

  /**
   * The result of the operation. This will be null if the operation failed.
   */
  T getResult() {
    result.result()
  }

  /**
   * An exception describing failure. This will be null if the operation succeeded.
   */
  Throwable getCause() {
    result.cause()
  }

  /**
   * Did it succeeed?
   */
  boolean isSucceeded() {
    result.succeeded()
  }

  /**
   * Did it fail?
   */
  boolean isFailed() {
    result.failed()
  }
}
