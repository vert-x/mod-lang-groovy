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



package core.websocket

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.testframework.TestUtils

tu = new TestUtils(vertx)
tu.checkThread()

server = vertx.createHttpServer()
client = vertx.createHttpClient(port: 8080)

def testEchoBinary() {
  echo(true)
}

def testEchoText() {
  echo(false)
}

def echo(binary) {

  server.websocketHandler { ws ->

    tu.checkThread()

    tu.azzert(ws.remoteAddress() != null )
    tu.azzert(ws.localAddress().getPort() == 8080)

    println "websocket remote address: ${ws.remoteAddress()}, local address ${ws.localAddress()}"

    ws.dataHandler { buff ->
      tu.checkThread()
      ws << buff
    }
  }

  server.listen(8080, {

    if (binary) {
      buff = TestUtils.generateRandomBuffer(1000)
    } else {
      str =  org.vertx.java.testframework.TestUtils.randomUnicodeString(1000)
      buff = new Buffer(str)
    }

    client.connectWebsocket("/someurl", { ws ->
      tu.checkThread()
      tu.azzert(ws.remoteAddress().getPort() == 8080 )
      tu.azzert(ws.localAddress() != null)

      received = new Buffer()

      ws.dataHandler { data ->
        tu.checkThread()
        received << data
        if (received.length == buff.length) {
          tu.azzert(TestUtils.buffersEqual(buff, received))
          tu.testComplete()
        }
      }

      if (binary) {
        ws.writeBinaryFrame(buff)
      } else {
        ws.writeTextFrame(str)
      }
    })

  })
}

def testWriteFromConnectHandler() {

  server.websocketHandler { ws ->
    tu.checkThread()
    tu.azzert(ws.remoteAddress() != null )
    tu.azzert(ws.localAddress().getPort() == 8080)

    ws.writeTextFrame("foo")
  }

  server.listen(8080, {
    client.connectWebsocket("/someurl", { ws ->
      tu.checkThread()
      tu.azzert(ws.remoteAddress().getPort() == 8080 )
      tu.azzert(ws.localAddress() != null)

      ws.dataHandler { buff ->
        tu.checkThread()
        tu.azzert("foo".equals(buff.toString()))
        tu.testComplete()
      }
    })
  })
}

def testClose() {

  server.websocketHandler { ws ->
    tu.checkThread()
    tu.azzert(ws.remoteAddress() != null )
    tu.azzert(ws.localAddress().getPort() == 8080)
    ws.dataHandler { buff ->
      ws.close()
    }
  }

  server.listen(8080, {
    client.connectWebsocket("/someurl", { ws ->
      tu.checkThread()
      tu.azzert(ws.remoteAddress().getPort() == 8080 )
      tu.azzert(ws.localAddress() != null)

      ws.closeHandler {
        tu.testComplete()
      }
      ws.writeTextFrame("foo")
    })
  })

}

def testCloseFromConnectHandler() {

  server.websocketHandler { ws ->
    tu.checkThread()
    tu.azzert(ws.remoteAddress() != null )
    tu.azzert(ws.localAddress().getPort() == 8080)
    ws.close()
  }

  server.listen(8080, {
    client.connectWebsocket("/someurl", { ws ->
      tu.checkThread()
      tu.azzert(ws.remoteAddress().getPort() == 8080 )
      tu.azzert(ws.localAddress() != null)
      
      ws.closeHandler {
        tu.testComplete()
      }
    })
  })
}

tu.registerTests(this)
tu.appReady()

void vertxStop() {
  client.close()
  server.close {
    tu.unregisterAll()
    tu.appStopped()
  }

}

