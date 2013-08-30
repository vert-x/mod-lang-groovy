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
package org.vertx.groovy.core.dns

import groovy.transform.CompileStatic
import org.vertx.java.core.dns.SrvRecord as JSrvRecord

/**
 * Represent a Service-Record (SRV) which was resolved for a domain.
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class SrvRecord {
  private final JSrvRecord record

  SrvRecord(JSrvRecord record) {
    this.record = record
  }

  /**
   * Returns the priority for this service record.
   */
  int getPriority() {
    record.priority()
  }

  /**
   * Returns the weight of this service record.
   */
  int getWeight() {
    record.weight()
  }

  /**
   * Returns the port the service is running on.
   */
  int getPort() {
    record.port()
  }

  /**
   * Returns the name for the server being queried.
   */
  String getName() {
    record.name()
  }

  /**
   * Returns the protocol for the service being queried (i.e. "_tcp").
   */
  String getProtocol() {
    record.protocol()
  }

  /**
   * Returns the service's name (i.e. "_http").
   */
  String getService() {
    record.service()
  }

  /**
   * Returns the name of the host for the service.
   */
  String getTarget() {
    record.target()
  }
}
