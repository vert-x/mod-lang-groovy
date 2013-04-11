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
package org.vertx.groovy.testframework

import org.vertx.groovy.core.Vertx
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.java.core.Handler

class TestUtils {

  TestUtils(Vertx vertx) {
    jTestUtils = new org.vertx.java.testframework.TestUtils(vertx.toJavaVertx())
  }

  private org.vertx.java.testframework.TestUtils jTestUtils

  static Buffer generateRandomBuffer(int length) {
    return new Buffer(org.vertx.java.testframework.TestUtils.generateRandomBuffer(length));
  }

  static Buffer generateRandomBuffer(int length, boolean avoid, byte avoidByte) {
    return new Buffer(org.vertx.java.testframework.TestUtils.generateRandomBuffer(length, avoid, avoidByte));
  }

  static boolean buffersEqual(Buffer b1, Buffer b2) {
    return org.vertx.java.testframework.TestUtils.buffersEqual(b1.toJavaBuffer(), b2.toJavaBuffer())
  }

  // Provide a version of register which takes a closure
  void register(testName, Closure handler) {
    jTestUtils.register(testName, handler as Handler)
  }

  void azzert(boolean result) {
    jTestUtils.azzert(result)
  }

  void azzert(boolean result, String message) {
    jTestUtils.azzert(result, message)
  }

  void appReady() {
    jTestUtils.appReady()
  }

  void appStopped() {
    jTestUtils.appStopped()
  }

  void testComplete() {
    jTestUtils.testComplete()
  }

  void startTest(String testName) {
    jTestUtils.startTest(testName)
  }

  void exception(Throwable t, String message) {
    jTestUtils.exception(t, message)
  }

  void trace(String message) {
    jTestUtils.trace(message)
  }

  void registerTests(Object obj) {
    jTestUtils.registerTests(obj)
  }

  void unregisterAll() {
    jTestUtils.unregisterAll()
  }

  void sendEvent(String eventName) {
    jTestUtils.sendEvent(eventName)
  }

  void checkThread() {
    jTestUtils.checkThread()
  }
}
