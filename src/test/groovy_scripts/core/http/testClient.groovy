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
  httpMethod([ssl:false, method: "GET", chunked:false])
}

def testGetSSL() {
  httpMethod([ssl:true, method: "GET", chunked:false])
}

def testPUT() {
  httpMethod([ssl:false, method:"PUT", chunked:false])
}

def testPUTSSL() {
  httpMethod([ssl:true, method:"PUT", chunked:false])
}

def testPOST() {
  httpMethod([ssl:false, method:"POST", chunked:false])
}

def testPOSTSSL() {
  httpMethod([ssl:true, method:"POST", chunked:false])
}

def testHEAD() {
  httpMethod([ssl:false, method:"HEAD", chunked:false])
}

def testHEADSSL() {
  httpMethod([ssl:true, method:"HEAD", chunked:false])
}

def testOPTIONS() {
  httpMethod([ssl:false, method:"OPTIONS", chunked:false])
}

def testOPTIONSSSL() {
  httpMethod([ssl:true, method:"OPTIONS", chunked:false])
}
def testDELETE() {
  httpMethod([ssl:false, method:"DELETE", chunked:false])
}

def testDELETESSL() {
  httpMethod([ssl:true, method:"DELETE", chunked:false])
}

def testTRACE() {
  httpMethod([ssl:false, method:"TRACE", chunked:false])
}

def testTRACESSL() {
  httpMethod([ssl:true, method:"TRACE", chunked:false])
}

def testCONNECT() {
  httpMethod([ssl:false, method:"CONNECT", chunked:false])
}

def testCONNECTSSL() {
  httpMethod([ssl:true, method:"CONNECT", chunked:false])
}

def testPATCH() {
  httpMethod([ssl:false, method:"PATCH", chunked:false])
}

def testPATCHSSL() {
  httpMethod([ssl:true, method:"PATCH", chunked:false])
}



def testGETChunked() {
  httpMethod([ssl:false, method: "GET", chunked:true])
}

def testGetSSLChunked() {
  httpMethod([ssl:true, method: "GET", chunked:true])
}

def testPUTChunked() {
  httpMethod([ssl:false, method:"PUT", chunked:true])
}

def testPUTSSLChunked() {
  httpMethod([ssl:true, method:"PUT", chunked:true])
}

def testPOSTChunked() {
  httpMethod([ssl:false, method:"POST", chunked:true])
}

def testPOSTSSLChunked() {
  httpMethod([ssl:true, method:"POST", chunked:true])
}

def testHEADChunked() {
  httpMethod([ssl:false, method:"HEAD", chunked:true])
}

def testHEADSSLChunked() {
  httpMethod([ssl:true, method:"HEAD", chunked:true])
}

def testOPTIONSChunked() {
  httpMethod([ssl:false, method:"OPTIONS", chunked:true])
}

def testOPTIONSSSLChunked() {
  httpMethod([ssl:true, method:"OPTIONS", chunked:true])
}

def testDELETEChunked() {
  httpMethod([ssl:false, method:"DELETE", chunked:true])
}

def testDELETESSLChunked() {
  httpMethod([ssl:true, method:"DELETE", chunked:true])
}

def testTRACEChunked() {
  httpMethod([ssl:false, method:"TRACE", chunked:true])
}

def testTRACESSLChunked() {
  httpMethod([ssl:true, method:"TRACE", chunked:true])
}

def testCONNECTChunked() {
  httpMethod([ssl:false, method:"CONNECT", chunked:true])
}

def testCONNECTSSLChunked() {
  httpMethod([ssl:true, method:"CONNECT", chunked:true])
}

def testPATCHChunked() {
  httpMethod([ssl:false, method:"PATCH", chunked:true])
}

def testPATCHSSLChunked() {
  httpMethod([ssl:true, method:"PATCH", chunked:true])
}



def testGETCompressed() {
  httpMethod([ssl:false, method: "GET", chunked:false, compression:true])
}
def testGETChunkedCompressed() {
  httpMethod([ssl:false, method: "GET", chunked:true, compression:true])
}
def testGETSSLCompressed() {
  httpMethod([ssl:true, method: "GET", chunked:false, compression:true])
}
def testGETSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "GET", chunked:true, compression:true])
}
def testPUTCompressed() {
  httpMethod([ssl:false, method: "PUT", chunked:false, compression:true])
}
def testPUTChunkedCompressed() {
  httpMethod([ssl:false, method: "PUT", chunked:true, compression:true])
}
def testPUTSSLCompressed() {
  httpMethod([ssl:true, method: "PUT", chunked:false, compression:true])
}
def testPUTSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "PUT", chunked:true, compression:true])
}
def testPOSTCompressed() {
  httpMethod([ssl:false, method: "POST", chunked:false, compression:true])
}
def testPOSTChunkedCompressed() {
  httpMethod([ssl:false, method: "POST", chunked:true, compression:true])
}
def testPOSTSSLCompressed() {
  httpMethod([ssl:true, method: "POST", chunked:false, compression:true])
}
def testPOSTSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "POST", chunked:true, compression:true])
}
def testOPTIONSCompressed() {
  httpMethod([ssl:false, method: "OPTIONS", chunked:false, compression:true])
}
def testOPTIONSChunkedCompressed() {
  httpMethod([ssl:false, method: "OPTIONS", chunked:true, compression:true])
}
def testOPTIONSSSLCompressed() {
  httpMethod([ssl:true, method: "OPTIONS", chunked:false, compression:true])
}
def testOPTIONSSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "OPTIONS", chunked:true, compression:true])
}
def testHEADCompressed() {
  httpMethod([ssl:false, method: "HEAD", chunked:false, compression:true])
}
def testHEADChunkedCompressed() {
  httpMethod([ssl:false, method: "HEAD", chunked:true, compression:true])
}
def testHEADSSLCompressed() {
  httpMethod([ssl:true, method: "HEAD", chunked:false, compression:true])
}
def testHEADSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "HEAD", chunked:true, compression:true])
}
def testDELETECompressed() {
  httpMethod([ssl:false, method: "DELETE", chunked:false, compression:true])
}
def testDELETEChunkedCompressed() {
  httpMethod([ssl:false, method: "DELETE", chunked:true, compression:true])
}
def testDELETESSLCompressed() {
  httpMethod([ssl:true, method: "DELETE", chunked:false, compression:true])
}
def testDELETESSLChunkedCompressed() {
  httpMethod([ssl:true, method: "DELETE", chunked:true, compression:true])
}
def testTRACECompressed() {
  httpMethod([ssl:false, method: "TRACE", chunked:false, compression:true])
}
def testTRACEChunkedCompressed() {
  httpMethod([ssl:false, method: "TRACE", chunked:true, compression:true])
}
def testTRACESSLCompressed() {
  httpMethod([ssl:true, method: "TRACE", chunked:false, compression:true])
}
def testTRACESSLChunkedCompressed() {
  httpMethod([ssl:true, method: "TRACE", chunked:true, compression:true])
}
def testCONNECTCompressed() {
  httpMethod([ssl:false, method: "CONNECT", chunked:false, compression:true])
}
def testCONNECTChunkedCompressed() {
  httpMethod([ssl:false, method: "CONNECT", chunked:true, compression:true])
}
def testCONNECTSSLCompressed() {
  httpMethod([ssl:true, method: "CONNECT", chunked:false, compression:true])
}
def testCONNECTSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "CONNECT", chunked:true, compression:true])
}
def testPATCHCompressed() {
  httpMethod([ssl:false, method: "PATCH", chunked:false, compression:true])
}
def testPATCHChunkedCompressed() {
  httpMethod([ssl:false, method: "PATCH", chunked:true, compression:true])
}
def testPATCHSSLCompressed() {
  httpMethod([ssl:true, method: "PATCH", chunked:false, compression:true])
}
def testPATCHSSLChunkedCompressed() {
  httpMethod([ssl:true, method: "PATCH", chunked:true, compression:true])
}

def testFormFileUpload() {
  content = "Vert.x rocks!";
  server.requestHandler { req ->
    if (req.uri.startsWith("/form")) {
      req.response.chunked = true;
      req.expectMultiPart = true;
      req.uploadHandler { upload ->
        tu.azzert("tmp-0.txt" == upload.filename)
        tu.azzert("image/gif" == upload.contentType)
        upload.dataHandler { buffer ->
          tu.azzert(content == buffer.toString());
        }
      }
      req.endHandler {
        attrs = req.formAttributes;
        tu.azzert(attrs.isEmpty());
        req.response.end();
      }
    }
  }
  server.listen(8080, "0.0.0.0", { ar ->
    tu.azzert(ar.succeeded);
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
      req.response.chunked = true
      req.expectMultiPart = true
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
    tu.azzert(ar.succeeded);
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


def httpMethod(parameters)  {

  def method = parameters.method
  def ssl = parameters.ssl
  def chunked = parameters.chunked
  def compression = parameters.compression

  if (ssl) {
    server.SSL = true
    server.keyStorePath = "./src/test/keystores/server-keystore.jks"
    server.keyStorePassword = "wibble"
    server.trustStorePath = "./src/test/keystores/server-truststore.jks"
    server.trustStorePassword = "wibble"
    server.clientAuthRequired = true
  }

  if (compression) {
    server.setCompressionSupported(true)
    client.setTryUseCompression(true)
    tu.azzert(server.isCompressionSupported())
    tu.azzert(client.getTryUseCompression())
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
    tu.azzert(asyncResult.succeeded)
    tu.azzert(asyncResult.result == server)
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

