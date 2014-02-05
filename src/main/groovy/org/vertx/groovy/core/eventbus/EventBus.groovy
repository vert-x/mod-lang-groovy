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

package org.vertx.groovy.core.eventbus

import org.vertx.groovy.core.AsyncResult
import org.vertx.java.core.AsyncResult as JAsyncResult
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.impl.ClosureUtil
import org.vertx.java.core.Handler
import org.vertx.java.core.eventbus.EventBus as JEventBus
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject
import org.vertx.java.core.eventbus.Message as JMessage

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap

/**
 * A distributed lightweight event bus which can encompass multiple vert.x instances.
 * The event bus implements both publish / subscribe network and point to point messaging.<p>
 *
 * For publish / subscribe, messages can be published to an address using one of the {@code publish} methods. An
 * address is a simple {@code String} instance.
 * Handlers are registered against an address. There can be multiple handlers registered against each address, and a particular handler can
 * be registered against multiple addresses. The event bus will route a sent message to all handlers which are
 * registered against that address.<p>
 *
 * For point to point messaging, messages can be sent to an address using one of the {@code send} methods.
 * The messages will be delivered to a single handler, if one is registered on that address. If more than one
 * handler is registered on the same address, Vert.x will choose one and deliver the message to that. Vert.x will
 * aim to fairly distribute messages in a round-robin way, but does not guarantee strict round-robin under all
 * circumstances.<p>
 *
 * All messages sent over the bus are transient. On event of failure of all or part of the event bus messages
 * may be lost. Applications should be coded to cope with lost messages, e.g. by resending them, and making application
 * services idempotent.<p>
 *
 * The order of messages received by any specific handler from a specific sender should match the order of messages
 * sent from that sender.<p>
 *
 * When sending a message, a reply handler can be provided. If so, it will be called when the reply from the receiver
 * has been received. Reply messages can also be replied to, etc, ad infinitum<p>
 *
 * Different event bus instances can be clustered together over a network, to give a single logical event bus.<p>
 *
 * Instances of this class are thread-safe
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class EventBus {

  // Putting it as a public final groovy property to be able to use `EventBus.jEventBus` notation
  final JEventBus jEventBus

  public EventBus(JEventBus jEventBus) {
    this.jEventBus = jEventBus
  }

  private Map handlerMap = new ConcurrentHashMap()

  /**
   * Send a message on the event bus.
   * Message can be a java.util.Map (Representing a JSON message), a String, boolean,
   * byte, short, int, long, float, double or {@link org.vertx.java.core.buffer.Buffer}
   * @param address The address to send it to
   * @param message The message
   * @param replyHandler Reply handler will be called when any reply from the recipient is received
   * @return self which allow method chaining
   */
  EventBus send(String address, message, Closure replyHandler = null) {
    if (message != null) {
      jEventBus.send(address, convertMessage(message), wrapHandler(replyHandler))
    } else {
      // Just choose an overloaded method...
      jEventBus.send(address, (String)null, wrapHandler(replyHandler))
    }
    this
  }

  /**
   * Send a message on the event bus.
   * Message can be a java.util.Map (Representing a JSON message), a String, boolean,
   * byte, short, int, long, float, double or {@link org.vertx.java.core.buffer.Buffer}
   * @param address The address to send it to
   * @param message The message
   * @param timeout The timeout
   * @param replyHandler Reply handler will be called when any reply from the recipient is received
   * @return self which allow method chaining
   */
  EventBus sendWithTimeout(String address, message, long timeout, Closure replyHandler = null) {
    if (message != null) {
      jEventBus.sendWithTimeout(address, convertMessage(message), timeout, wrapAsyncHandler(replyHandler))
    } else {
      // Just choose an overloaded method...
      jEventBus.sendWithTimeout(address, (String)null, timeout, wrapAsyncHandler(replyHandler))
    }
    this
  }

  /**
   * Publish a message on the event bus.
   * Message can be a java.util.Map (Representing a JSON message), a String, boolean,
   * byte, short, int, long, float, double or {@link org.vertx.java.core.buffer.Buffer}
   * @param address The address to publish it to
   * @param message The message
   * @return self which allow method chaining
   */
  EventBus publish(String address, message) {
    if (message != null) {
      jEventBus.publish(address, convertMessage(message))
    } else {
      // Just choose an overloaded method...
      jEventBus.publish(address, (String)null)
    }
    this
  }

  /**
   * Registers a handler against the specified address. When a message arrives the handler
   * will receive an instance of {@link Message}
   * @param address The address to register it at
   * @param handler The handler
   * @param resultHandler Optional completion handler. If specified, then when the register has been
   * propagated to all nodes of the event bus, the handler will be called.
   * @return A unique handler id
   */
  EventBus registerHandler(String address, Closure handler, Closure resultHandler = null) {
    def wrapped = wrapHandler(handler)
    handlerMap.put(handler, wrapped)
    jEventBus.registerHandler(address, wrapped, ClosureUtil.wrapAsyncResultHandler(resultHandler))
    this
  }

  /**
   * Registers a local handler against the specified address. The handler info won't
   * be propagated across the cluster. When a message arrives the handler
   * will receive an instance of {@link Message}
   * @param address The address to register it at
   * @param handler The handler
   * @return A unique handler id
   */
  EventBus registerLocalHandler(String address, Closure handler) {
    def wrapped = wrapHandler(handler)
    handlerMap.put(handler, wrapped)
    jEventBus.registerLocalHandler(address, wrapped)
    this
  }

  /**
   * Unregisters a handler given the address and the handler
   * @param address The address the handler was registered to
   * @param handler The handler
   * @param resultHandler Optional completion handler. If specified, then when the unregister has been
   * propagated to all nodes of the event bus, the handler will be called.
   */
  EventBus unregisterHandler(String address, Closure handler, Closure resultHandler = null) {
    def wrapped = handlerMap.remove(handler)
    if (wrapped != null) {
      jEventBus.unregisterHandler(address, wrapped as Handler, ClosureUtil.wrapAsyncResultHandler(resultHandler))
    }
    this
  }

  /**
   * Sets a default timeout, in ms, for replies. If a messages is sent specify a reply handler
   * but without specifying a timeout, then the reply handler is timed out, i.e. it is automatically unregistered
   * if a message hasn't been received before timeout.
   * The default value for default send timeout is -1, which means "never timeout".
   * @param timeoutMs
   */
  EventBus setDefaultReplyTimeout (long timeoutMs) {
    jEventBus.setDefaultReplyTimeout(timeoutMs)
    this
  }

  /**
   * Return the value for default send timeout
   */
  long getDefaultReplyTimeout() {
    return jEventBus.getDefaultReplyTimeout()
  }

  /**
   * Get the Java instance
   *
   * @deprecated use  `EventBus.jEventBus` notation instead.
   */
  @Deprecated
  JEventBus javaEventBus() {
    jEventBus
  }

  protected static convertMessage(message) {
    if (message instanceof Map) {
      message = new JsonObject(normalizeMap(message))
    } else if (message instanceof List) {
        message = new JsonArray(normalizeList(message))
    } else if (message instanceof GString) {
        message = message.toString()
    } else if (message instanceof Buffer) {
      message = ((Buffer)message).toJavaBuffer()
    }
    message
  }

  private static Map normalizeMap(Map map) {
      map.collectEntries{k, v -> [normalize(k), normalize(v)]};
  }
  private static List normalizeList(List list) {
      list.collect{v -> normalize(v)};
  }
  private static Object normalize(Object o) {
    if (o instanceof Map) {
        normalizeMap(o);
    } else if (o instanceof List) {
        normalizeList(o);
    } else if (o instanceof GString) {
        o.toString();
    } else {
        o;
    }
  }


  protected static Handler wrapHandler(Closure handler) {
    if (handler != null) {
      return { handler(new Message((JMessage) it)) } as Handler
    } else {
      return null
    }
  }

  protected static Handler wrapAsyncHandler(Closure handler) {
    if (handler != null) {
      return new Handler<JAsyncResult<JMessage>>() {
        void handle (JAsyncResult<JMessage> jresult) {
          JMessage jmsg = jresult?.result()
          Message msg = jmsg ? new Message(jmsg) : null
          AsyncResult<Message> ar = new AsyncResult(jresult) {
            Message getResult () {
              return msg
            }
          }
          handler(ar)
        }
      }
    } else {
      return null
    }
  }

}
