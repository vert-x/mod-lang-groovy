/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vertx.groovy.platform

import groovy.transform.CompileStatic;

import org.vertx.groovy.core.Vertx
import org.vertx.java.core.Future

/**
 * Represents a Verticle that runs in the container.<p>
 * If you are creating a Groovy compiled Verticle you should extend this class
 * to create your verticle.
 *
 * @author swilliams
 *
 */
@CompileStatic
abstract class Verticle {

  /**
   * The Vertx instance
   */
  Vertx vertx

  /**
   * The Container instance
   */
  Container container

  /**
   * Override this to implement a synchronous start
   */
  def start() {
  }

  /**
   * Override this to implement an asynchronous start, and use the startedResult to signal when startup is complete
   * @param startedResult
   */
  def start(Future<Void> startedResult) {
    start()
    startedResult.setResult(null)
  }

  /**
   * This will be called when the verticle is stopped
   */
  def stop() {
  }

}
