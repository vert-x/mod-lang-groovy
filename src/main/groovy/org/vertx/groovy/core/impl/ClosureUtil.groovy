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
package org.vertx.groovy.core.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.AsyncResult
import org.vertx.java.core.AsyncResult as JAsyncResult
import org.vertx.java.core.AsyncResultHandler
import org.vertx.java.core.impl.DefaultFutureResult

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class ClosureUtil {

  static AsyncResultHandler wrapAsyncResultHandler(Closure hndlr) {
    if (hndlr == null) {
      null
    } else {
      { JAsyncResult ar ->
        hndlr(new AsyncResult(ar))
      } as AsyncResultHandler
    }
  }

  static AsyncResultHandler wrapAsyncResultHandler(Closure hndlr, Closure converter) {
    { JAsyncResult ar ->
      if (ar.failed()) {
        hndlr(new AsyncResult(ar))
      } else {
        hndlr(new AsyncResult(new DefaultFutureResult(converter(ar.result()) as Object)))
      }
    } as AsyncResultHandler
  }
}
