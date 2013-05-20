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

package core.http

import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.testframework.TestUtils
import org.vertx.java.core.AsyncResult
import org.vertx.java.core.AsyncResultHandler
import org.vertx.java.core.Handler
import org.vertx.java.core.VoidHandler
import org.vertx.java.core.http.HttpClientRequest
import org.vertx.java.core.http.HttpClientResponse
import org.vertx.java.core.http.HttpServer
import org.vertx.java.core.http.HttpServerFileUpload
import org.vertx.java.core.http.HttpServerRequest
import org.vertx.java.core.http.HttpVersion

import java.util.Map.Entry
import java.util.concurrent.atomic.AtomicInteger

tu = new TestUtils(vertx)
tu.checkThread()

server = vertx.createHttpServer()
client = vertx.createHttpClient().setPort(8080)

def testGET() {
  httpMethod(false, "GET", false)
}

def testGetSSL() {
  httpMethod(true, "GET", false)
}

def testPUT() {
  httpMethod(false, "PUT", false)
}

def testPUTSSL() {
  httpMethod(true, "PUT", false)
}

def testPOST() {
  httpMethod(false, "POST", false)
}

def testPOSTSSL() {
  httpMethod(true, "POST", false)
}

def testHEAD() {
  httpMethod(false, "HEAD", false)
}

def testHEADSSL() {
  httpMethod(true, "HEAD", false)
}

def testOPTIONS() {
  httpMethod(false, "OPTIONS", false)
}

def testOPTIONSSSL() {
  httpMethod(true, "OPTIONS", false)
}
def testDELETE() {
  httpMethod(false, "DELETE", false)
}

def testDELETESSL() {
  httpMethod(true, "DELETE", false)
}

def testTRACE() {
  httpMethod(false, "TRACE", false)
}

def testTRACESSL() {
  httpMethod(true, "TRACE", false)
}

def testCONNECT() {
  httpMethod(false, "CONNECT", false)
}

def testCONNECTSSL() {
  httpMethod(true, "CONNECT", false)
}

def testPATCH() {
  httpMethod(false, "PATCH", false)
}

def testPATCHSSL() {
  httpMethod(true, "PATCH", false)
}




def testGETChunked() {
  httpMethod(false, "GET", true)
}

def testGetSSLChunked() {
  httpMethod(true, "GET", true)
}

def testPUTChunked() {
  httpMethod(false, "PUT", true)
}

def testPUTSSLChunked() {
  httpMethod(true, "PUT", true)
}

def testPOSTChunked() {
  httpMethod(false, "POST", true)
}

def testPOSTSSLChunked() {
  httpMethod(true, "POST", true)
}

def testHEADChunked() {
  httpMethod(false, "HEAD", true)
}

def testHEADSSLChunked() {
  httpMethod(true, "HEAD", true)
}

def testOPTIONSChunked() {
  httpMethod(false, "OPTIONS", true)
}

def testOPTIONSSSLChunked() {
  httpMethod(true, "OPTIONS", true)
}

def testDELETEChunked() {
  httpMethod(false, "DELETE", true)
}

def testDELETESSLChunked() {
  httpMethod(true, "DELETE", true)
}

def testTRACEChunked() {
  httpMethod(false, "TRACE", true)
}

def testTRACESSLChunked() {
  httpMethod(true, "TRACE", true)
}

def testCONNECTChunked() {
  httpMethod(false, "CONNECT", true)
}

def testCONNECTSSLChunked() {
  httpMethod(true, "CONNECT", true)
}

def testPATCHChunked() {
  httpMethod(false, "PATCH", true)
}

def testPATCHSSLChunked() {
  httpMethod(true, "PATCH", true)
}

def testFormFileUpload() {
  content = "Vert.x rocks!";
  server.requestHandler { req ->
    if (req.uri.startsWith("/form")) {
      req.response.chunked = true;
      req.uploadHandler { event ->
        event.dataHandler { buffer ->
          tu.azzert(content == buffer.toString());
        }
      }
      req.endHandler {
        attrs = req.formAttributes;
        tu.azzert(attrs.get("name") == "file");
        tu.azzert(attrs.get("filename") == "tmp-0.txt");
        tu.azzert(attrs.get("Content-Type") == "image/gif");
        req.response.end();
      }
    }
  }
  server.listen(8080, "0.0.0.0", { ar ->
    tu.azzert(ar.succeeded());
    client.port = 8080;
    req = client.post("/form", { resp ->
      // assert the response
      tu.azzert(200 == resp.statusCode);
      resp.bodyHandler { body ->
        tu.azzert(0 == body.length());
      }
      tu.testComplete();
    })
    boundary = "dLV9Wyq26L_-JQxk6ferf-RT153LhOO";
    buffer = new Buffer();
    b =
      "--" + boundary + "\r\n" +
              "Content-Disposition: form-data; name=\"file\"; filename=\"tmp-0.txt\"\r\n" +
              "Content-Type: image/gif\r\n" +
              "\r\n" +
              content + "\r\n" +
              "--" + boundary + "--\r\n";

    buffer.appendString(b);
    req.headers.set("content-length", String.valueOf(buffer.length()));
    req.headers.set("content-type", "multipart/form-data; boundary=" + boundary);
    req.write(buffer).end();
  });
}

def testFormUploadAttributes() {
  server.requestHandler { req ->
    if (req.uri.startsWith("/form")) {
      req.response.chunked = true;
      req.uploadHandler { event ->
        event.dataHandler { buffer ->
          tu.azzert(false);
        }
      }
      req.endHandler {
        attrs = req.formAttributes;
        tu.azzert(attrs.get("framework") == "vertx");
        tu.azzert(attrs.get("runson") == "jvm");
        req.response.end();
      }
    }
  }
  server.listen(8080, "0.0.0.0", { ar ->
    tu.azzert(ar.succeeded());
    client.port = 8080;
    req = client.post("/form", { resp ->
      // assert the response
      tu.azzert(200 == resp.statusCode);
      resp.bodyHandler { body ->
        tu.azzert(0 == body.length());
      }
      tu.testComplete();
    })
    buffer = new Buffer();
    buffer.appendString("framework=vertx&runson=jvm");
    req.headers.set("content-length", String.valueOf(buffer.length()));
    req.headers.set("content-type", "application/x-www-form-urlencoded");
    req.write(buffer).end();
  });
}


def httpMethod(ssl, method, chunked)  {

  if (ssl) {
    server.SSL = true
    server.keyStorePath = "./src/test/keystores/server-keystore.jks"
    server.keyStorePassword = "wibble"
    server.trustStorePath = "./src/test/keystores/server-truststore.jks"
    server.trustStorePassword = "wibble"
    server.clientAuthRequired = true
  }

  path = "/someurl/blah.html"
  query = "param1=vparam1&param2=vparam2"
  uri = "http://localhost:8080" + path + "?" + query

  server.requestHandler { req ->
    tu.checkThread()
    tu.azzert(req.version == HttpVersion.HTTP_1_1)
    tu.azzert(req.uri.equals(uri))
    tu.azzert(req.method == method)
    tu.azzert(req.path == path)
    tu.azzert(req.query == query)
    tu.azzert(req.headers.get("header1") == "vheader1")
    tu.azzert(req.headers.get("header2") == "vheader2")
    tu.azzert(req.params.get("param1") == "vparam1")
    tu.azzert(req.params.get("param2") == "vparam2")
    headers = req.headers
    tu.azzert(headers.contains('header1'))
    tu.azzert(headers.contains('header2'))
    tu.azzert(headers.contains('header3'))
    tu.azzert(!headers.empty)

    headers.remove('header3')
    tu.azzert(!headers.contains('header3'))

    req.response.headers.set('rheader1', 'vrheader1')
    req.response.headers.set('rheader2', 'vrheader2')

    body = new Buffer()
    req.dataHandler { data ->
      tu.checkThread()
      body << data
    }

    if (method != 'HEAD' && method != 'CONNECT') {
     req.response.setChunked(chunked)
    }

    req.endHandler {
      tu.checkThread()
      if (method != 'HEAD' && method != 'CONNECT') {
        if (!chunked) {
          req.response.headers.set('content-length', Integer.toString(body.length))
        }
        req.response << body
      }
      if (chunked) {
        req.response.trailers.set('trailer1', 'vtrailer1')
        req.response.trailers.set('trailer2', 'vtrailer2')
      }
      req.response.end()
    }
  }

  if (ssl) {
    client.SSL = true
    client.keyStorePath = "./src/test/keystores/client-keystore.jks"
    client.keyStorePassword = "wibble"
    client.trustStorePath = "./src/test/keystores/client-truststore.jks"
    client.trustStorePassword = "wibble"
  }

  server.listen(8080, { asyncResult ->
    tu.azzert(asyncResult.succeeded())
    tu.azzert(asyncResult.result() == server)
    sentBuff = TestUtils.generateRandomBuffer(1000)

    request = client.request(method, uri, { resp ->
      tu.checkThread()
      tu.azzert(200 == resp.statusCode)
      tu.azzert(resp.headers.get('rheader1') == 'vrheader1')
      tu.azzert(resp.headers.get('rheader2') == 'vrheader2')
      body = new Buffer()
      resp.dataHandler { data ->
        tu.checkThread()
        body << data
      }

      resp.endHandler {
        tu.checkThread()
        if (method != 'HEAD' && method != 'CONNECT') {
          tu.azzert(TestUtils.buffersEqual(sentBuff, body))
          if (chunked) {
            tu.azzert(resp.trailers.get('trailer1') == 'vtrailer1')
            tu.azzert(resp.trailers.get('trailer2') == 'vtrailer2')
          }
        }
        resp.headers.clear()
        tu.azzert(resp.headers.empty)
        tu.testComplete()
      }
    })

    request.chunked = true
    request.headers.set('header1', 'vheader1')
    request.headers.set('header2', 'vheader2')
    if (!chunked) {
      request.headers.set('content-length', Integer.toString(sentBuff.length))
    }
    request.headers.add('header3', 'vheader3_1').add('header3', 'vheader3')

    headers = request.headers
    size = headers.size
    names = headers.names
    tu.azzert(size == names.size())
    headers.each { Entry e -> tu.azzert(headers.getAll(e.key).contains(e.value)) }

    request << sentBuff

    request.end()
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

