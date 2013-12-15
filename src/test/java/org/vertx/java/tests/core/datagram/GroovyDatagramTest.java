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
package org.vertx.java.tests.core.datagram;

import org.vertx.java.testframework.TestBase;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class GroovyDatagramTest extends TestBase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    startApp("core/datagram/testClient.groovy");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSendReceive() {
    startTest(getMethodName());
  }

  public void testListenHostPort() {
    startTest(getMethodName());
  }

  public void testListenPort() {
    startTest(getMethodName());
  }

  public void testListenInetSocketAddress() {
    startTest(getMethodName());
  }

  public void testListenSamePortMultipleTimes() {
    startTest(getMethodName());
  }


  public void testEcho() {
     startTest(getMethodName());
  }

  public void testSendAfterCloseFails() {
    startTest(getMethodName());
  }

  public void testBroadcast() {
    startTest(getMethodName());
  }

  public void testBroadcastFailsIfNotConfigured() {
    startTest(getMethodName());
  }

  public void testConfigureAfterSendString() {
    startTest(getMethodName());
  }

  public void testConfigureAfterSendStringWithEnc() {
    startTest(getMethodName());
  }

  public void testConfigureAfterSendBuffer() {
    startTest(getMethodName());
  }

  public void testConfigureAfterListen() {
    startTest(getMethodName());
  }

  public void testConfigureAfterListenWithInetSocketAddress() {
    startTest(getMethodName());
  }

  public void testConfigure() {
    startTest(getMethodName());
  }

  public void testMulticastJoinLeave() {
    startTest(getMethodName());
  }
}
