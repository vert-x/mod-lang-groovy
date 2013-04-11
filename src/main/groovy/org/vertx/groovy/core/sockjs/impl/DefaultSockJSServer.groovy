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

import org.vertx.groovy.core.http.HttpServer
import org.vertx.groovy.core.sockjs.SockJSServer
import org.vertx.java.core.Handler
import org.vertx.java.core.Vertx
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
class DefaultSockJSServer implements SockJSServer {

  protected org.vertx.java.core.sockjs.SockJSServer jServer

  DefaultSockJSServer(Vertx vertx, HttpServer httpServer) {
    jServer = vertx.createSockJSServer(httpServer.toJavaServer())
  }

  /**
   * Install an application
   * @param config The application configuration
   * @param sockHandler A handler that will be called when new SockJS sockets are created
   */
  void installApp(Map config, Closure sockHandler) {
    jServer.installApp(new JsonObject(config), {
      org.vertx.java.core.sockjs.SockJSSocket jSock = it
      sockHandler(new DefaultSockJSSocket(jSock))
    } as Handler)
  }

  /**
   * Install an app which bridges the SockJS server to the event bus.
   * @param sjsConfig The config for the app
   * @param permitted A list of JSON objects which define inboundPermitted matches
   * @param authAddress The address of an authentication/authorisation busmod
   * @param bridgeAddress The address the bridge will listen at for login and lougout.
   */
  void bridge(Map sjsConfig, List<Map<String, Object>> inboundPermitted = [[:]],
              List<Map<String, Object>> outboundPermitted = [[:]],
              long authTimeout = 5 * 60 * 1000, String authAddress = null) {
    jServer.bridge(new JsonObject(sjsConfig), new JsonArray(inboundPermitted), new JsonArray(outboundPermitted),
        authTimeout, authAddress)
  }

}
