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
import org.vertx.java.core.dns.MxRecord as JMxRecord

/**
 * Represent a Mail-Exchange-Record (MX) which was resolved for a domain.
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class MxRecord {

  private final JMxRecord record

  MxRecord(JMxRecord record) {
    this.record = record
  }


  /**
   * The priority of the MX record.
   */
  int getPriority() {
      record.priority()
  }

  /**
   * The name of the MX record
   */
  String getName() {
    record.name();
  }
}
