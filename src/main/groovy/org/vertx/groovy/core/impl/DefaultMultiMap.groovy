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

  org.vertx.java.core.MultiMap toJavaMultiMap() {
    return jmap
  }

  public static org.vertx.java.core.MultiMap toJavaMultiMap(MultiMap multiMap) {
     if (multiMap instanceof DefaultMultiMap) {
       (multiMap as DefaultMultiMap).toJavaMultiMap()
     } else {
       new JMultiMapWrapper(multiMap)
     }
  }

  private static class JMultiMapWrapper implements org.vertx.java.core.MultiMap {
    private MultiMap map

    JMultiMapWrapper(MultiMap map) {
      this.map = map;
    }

    @Override
    String get(String name) {
      map.get(name)
    }

    @Override
    List<String> getAll(String name) {
      map.getAll(name)
    }

    @Override
    List<Map.Entry<String, String>> entries() {
      map.entries
    }

    @Override
    boolean contains(String name) {
      map.contains(name)
    }

    @Override
    boolean isEmpty() {
      map.empty
    }

    @Override
    Set<String> names() {
      map.names
    }

    @Override
    org.vertx.java.core.MultiMap add(String name, String value) {
      map.add(name, value)
      this
    }

    @Override
    org.vertx.java.core.MultiMap add(String name, Iterable<String> values) {
      map.add(name, values)
      this
    }

    @Override
    org.vertx.java.core.MultiMap set(String name, String value) {
      map.set(name, value)
      this
    }

    @Override
    org.vertx.java.core.MultiMap set(String name, Iterable<String> values) {
      map.set(name, values)
      this
    }

    @Override
    org.vertx.java.core.MultiMap set(org.vertx.java.core.MultiMap headers) {
      map.set(new DefaultMultiMap(headers))
      this
    }

    @Override
    org.vertx.java.core.MultiMap set(Map<String, String> headers) {
      map.set(headers)
      this
    }

    @Override
    org.vertx.java.core.MultiMap remove(String name) {
      map.remove(name)
      this
    }

    @Override
    org.vertx.java.core.MultiMap clear() {
      map.clear()
      this
    }

    @Override
    int size() {
      map.size
    }

    @Override
    Iterator<Map.Entry<String, String>> iterator() {
      map.iterator()
    }
  }
}

