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
 **/
package org.vertx.groovy.core.impl

import groovy.transform.CompileStatic
import org.vertx.groovy.core.MultiMap

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class DefaultMultiMap implements MultiMap {
  private org.vertx.java.core.MultiMap jmap;

  public DefaultMultiMap(org.vertx.java.core.MultiMap jmap) {
    this.jmap = jmap
  }

  @Override
  public Iterator iterator() {
    jmap.iterator()
  }

  @Override
  public String get(String name) {
    jmap.get(name)
  }

  @Override
  public List<String> getAll(String name) {
    jmap.getAll(name)
  }

  @Override
  public List<Map.Entry<String, String>> getEntries() {
    jmap.entries()
  }

  @Override
  public boolean contains(String name) {
    jmap.contains(name)
  }

  @Override
  public boolean isEmpty() {
    jmap.isEmpty()
  }

  @Override
  public Set<String> getNames() {
    jmap.names();
  }

  @Override
  public MultiMap add(String name, String value) {
    jmap.add(name, value)
    this
  }

  @Override
  public MultiMap add(String name, Iterable<String> values) {
    jmap.add(name, values)
    this
  }

  @Override
  public MultiMap set(String name, String value) {
    jmap.set(name, value)
    this
  }

  @Override
  public MultiMap set(String name, Iterable<String> values) {
    jmap.set(name, values)
    this
  }

  @Override
  public MultiMap set(MultiMap headers) {
    jmap.set(toJavaMultiMap(headers))
    this
  }

  @Override
  public MultiMap set(Map<String, String> headers) {
    jmap.set(headers)
    this
  }

  @Override
  public MultiMap remove(String name) {
    jmap.remove(name)
    this
  }

  @Override
  public MultiMap clear() {
    jmap.clear()
    this
  }

  @Override
  public int getSize() {
    jmap.size()
  }

  @Override
  MultiMap leftShift(Map.Entry<String, ?> entry) {
    if (entry.value instanceof String) {
      return set(entry.key, entry.value as String);
    } else if (entry.value instanceof Iterable) {
      return set(entry.key, entry.value as Iterable)
    }
    throw new IllegalArgumentException();
  }

  @Override
  MultiMap leftShift(MultiMap map) {
    return set(map)
  }

  static org.vertx.java.core.MultiMap toJavaMultiMap(MultiMap multiMap) {
     if (multiMap instanceof DefaultMultiMap) {
       (multiMap as DefaultMultiMap).jmap
     } else {
       throw new IllegalStateException("Invalid multimap!")
     }
  }

}

