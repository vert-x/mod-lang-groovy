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

package core.net

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.testframework.TestUtils

tu = new TestUtils(vertx)
tu.checkThread()

void testEcho() {
  echo(false)
}

void testEchoSSL() {
  echo(true)  
}

void echo(boolean ssl) {

  server = vertx.createNetServer()

  if (ssl) {
    server.SSL = true
    server.keyStorePath = "./src/test/keystores/server-keystore.jks"
    server.keyStorePassword = "wibble"
    server.trustStorePath = "./src/test/keystores/server-truststore.jks"
    server.trustStorePassword = "wibble"
    server.clientAuthRequired = true
  }

  client = vertx.createNetClient()

  if (ssl) {
    client.SSL = true
    client.keyStorePath = "./src/test/keystores/client-keystore.jks"
    client.keyStorePassword = "wibble"
    client.trustStorePath = "./src/test/keystores/client-truststore.jks"
    client.trustStorePassword = "wibble"
  }

  server.connectHandler { socket ->

    tu.checkThread()
    socket.dataHandler { buffer ->
      tu.checkThread()
      socket << buffer
    }
  }.listen(8080, { asyncResult0 ->
    tu.checkThread()
    tu.azzert asyncResult0.succeeded
    tu.azzert asyncResult0.result == server

    client.connect(8080, "localhost", { asyncResult ->
      tu.checkThread()

      tu.azzert asyncResult.succeeded

      socket = asyncResult.result

      sends = 10
      size = 100

      sent = new Buffer()
      received = new Buffer()

      socket.dataHandler { buffer ->
        tu.checkThread()

        received << buffer

        if (received.length == sends * size) {
          tu.azzert(TestUtils.buffersEqual(sent, received))

          server.close {
            client.close()
            tu.testComplete()
          }

        }
      }

      socket.endHandler {
        tu.checkThread()
      }

      socket.closeHandler {
        tu.checkThread()
      }

      socket.drainHandler {
        tu.checkThread()
      }

      socket.pause()

      socket.resume()

      sends.times {
        Buffer data = TestUtils.generateRandomBuffer(size)
        sent << data
        socket << data
      }
    })
  })
}

tu.registerTests(this)
tu.appReady()

void vertxStop() {
  tu.unregisterAll()
  tu.appStopped()
}

