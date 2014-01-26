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

package org.vertx.groovy.core.sockjs.impl

import groovy.transform.CompileStatic

import org.vertx.groovy.core.http.HttpServer
import org.vertx.groovy.core.sockjs.SockJSServer
import org.vertx.java.core.Handler
import org.vertx.java.core.Vertx
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject
import org.vertx.java.core.sockjs.EventBusBridgeHook
import org.vertx.java.core.sockjs.SockJSServer as JSockJSServer
import org.vertx.java.core.sockjs.SockJSSocket as JSockJSSocket


/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class DefaultSockJSServer implements SockJSServer {

  protected JSockJSServer jServer

  DefaultSockJSServer(Vertx vertx, HttpServer httpServer) {
    jServer = vertx.createSockJSServer(httpServer.toJavaServer())
  }

  SockJSServer installApp(Map config, Closure sockHandler) {
    jServer.installApp(new JsonObject(config), {
      sockHandler(new DefaultSockJSSocket((JSockJSSocket) it))
    } as Handler)
    this
  }

  SockJSServer bridge(Map sjsConfig) {
    jServer.bridge(new JsonObject(sjsConfig), new JsonArray([[:]]), new JsonArray([[:]]),
        5 * 60 * 1000, null)
    this
  }

  SockJSServer bridge(Map sjsConfig, List<Map<String, Object>> inboundPermitted,
                      List<Map<String, Object>> outboundPermitted) {
    jServer.bridge(new JsonObject(sjsConfig), new JsonArray((List<Object>)inboundPermitted), new JsonArray((List<Object>)outboundPermitted),
        5 * 60 * 1000, null)
    this
  }

  SockJSServer bridge(Map sjsConfig, List<Map<String, Object>> inboundPermitted,
                      List<Map<String, Object>> outboundPermitted,
                      long authTimeout, String authAddress) {
    jServer.bridge(new JsonObject(sjsConfig), new JsonArray((List<Object>)inboundPermitted), new JsonArray((List<Object>)outboundPermitted),
        authTimeout, authAddress)
    this
  }

  SockJSServer bridge(Map sjsConfig, List<Map<String, Object>> inboundPermitted, 
                      List<Map<String, Object>> outboundPermitted, Map bridgeConfig) {
    jServer.bridge(new JsonObject(sjsConfig), new JsonArray((List<Object>)inboundPermitted), 
                    new JsonArray((List<Object>)outboundPermitted),
                    new JsonObject(bridgeConfig))
    this  
  }

  SockJSServer setHook(EventBusBridgeHook hook) {
    jServer.setHook(hook)
    this
  }
}
