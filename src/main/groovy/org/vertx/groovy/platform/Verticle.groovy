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
 * @author swilliams
 *
 */
@CompileStatic
abstract class Verticle {

  Vertx vertx

  Container container

  def start() {
  }

  def start(Future<Void> startedResult) {
    start()
    startedResult.setResult(null)
  }

  def stop() {
  }

}
