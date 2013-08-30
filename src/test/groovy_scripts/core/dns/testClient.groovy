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

package core.dns

import org.vertx.groovy.core.dns.DnsClient
import org.vertx.groovy.testframework.TestUtils
import org.vertx.java.core.dns.DnsResponseCode
import org.vertx.testtools.TestDnsServer

tu = new TestUtils(vertx)
tu.checkThread()

// bytes representation of ::1
ipv6_bytes = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1] as byte[]

dnsServer = null

def prepareClient(TestDnsServer server) {
  dnsServer = server;
  dnsServer.start();
  InetSocketAddress addr = (InetSocketAddress) dnsServer.transports[0].acceptor.localAddress;
  vertx.createDnsClient(addr);
}

def testResolveA() {
  ip = "10.0.0.1";
  DnsClient client = prepareClient(TestDnsServer.testResolveA(ip));
  client.resolveA("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    tu.azzert(ip.equals(records.get(0).hostAddress))
    tu.testComplete()
  })
}

def testResolveAAAA() {
  ip = "::1"
  DnsClient client = prepareClient(TestDnsServer.testResolveAAAA(ip));
  client.resolveAAAA("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    tu.azzert(Arrays.equals(ipv6_bytes, records.get(0).address));
    tu.testComplete()
  })
}

def testResolveMX() {
  prio = 10
  name = "mail.vertx.io"
  DnsClient client = prepareClient(TestDnsServer.testResolveMX(prio, name));
  client.resolveMX("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    record = records.get(0);
    tu.azzert(prio == record.priority)
    tu.azzert(name.equals(record.name))
    tu.testComplete()
  })
}

def testResolveTXT() {
  txt = "Vert.x rocks"
  DnsClient client = prepareClient(TestDnsServer.testResolveTXT(txt));
  client.resolveTXT("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    tu.azzert(txt.equals(records.get(0)))
    tu.testComplete()
  })
}

public void testResolveNS() {
  ns = "ns.vertx.io"
  DnsClient client = prepareClient(TestDnsServer.testResolveNS(ns));
  client.resolveNS("::1", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    tu.azzert(ns.equals(records.get(0)))
    tu.testComplete()
  })
}

public void testResolveCNAME() {
  cname = "cname.vertx.io"
  DnsClient client = prepareClient(TestDnsServer.testResolveCNAME(cname));
  client.resolveCNAME("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    tu.azzert(cname.equals(records.get(0)))
    tu.testComplete()
  })
}

public void testResolvePTR() {
  ptr = "ptr.vertx.io"
  DnsClient client = prepareClient(TestDnsServer.testResolvePTR(ptr));
  client.resolvePTR("10.0.0.1.in-addr.arpa", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(ptr.equals(record))
    tu.testComplete()
  })
}


public void testResolveSRV() {
  priority = 10;
  weight = 1;
  port = 80;
  target = "vertx.io";
  DnsClient client = prepareClient(TestDnsServer.testResolveSRV(priority, weight, port, target));
  client.resolveSRV("vertx.io", { result ->
    tu.checkThread()
    records = result.result
    tu.azzert(records.size() == 1)
    record = records.get(0);

    tu.azzert(priority == record.priority);
    tu.azzert(weight == record.weight);
    tu.azzert(port == record.port);
    tu.azzert(target.equals(record.target));
    tu.testComplete()
  })
}

public void testLookup4() {
  ip = "10.0.0.1"
  DnsClient client = prepareClient(TestDnsServer.testLookup4(ip));
  client.lookup4("vertx.io", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(ip.equals(record.hostAddress))
    tu.testComplete()
  })
}

public void testLookup6() {
  DnsClient client = prepareClient(TestDnsServer.testLookup6());
  client.lookup6("vertx.io", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(Arrays.equals(ipv6_bytes, record.address))
    tu.testComplete()
  })
}

public void testLookup() {
  ip = "10.0.0.1"
  DnsClient client = prepareClient(TestDnsServer.testLookup(ip));
  client.lookup("vertx.io", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(ip.equals(record.hostAddress))
    tu.testComplete()
  })
}

public void testLookupNonExisting() {
  DnsClient client = prepareClient(TestDnsServer.testLookupNonExisting());
  client.lookup("notexist.vertx.io", { result ->
    tu.checkThread()
    cause = result.cause
    tu.azzert(cause.code() == DnsResponseCode.NXDOMAIN)
    tu.testComplete()
  })
}

public void testReverseLookupIpv4() {
  address = InetAddress.getByName("10.0.0.1").getAddress();
  ptr = "ptr.vertx.io";
  DnsClient client = prepareClient(TestDnsServer.testReverseLookup(ptr));
  client.reverseLookup("10.0.0.1", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(record instanceof Inet4Address);
    tu.azzert(ptr.equals(record.hostName));
    tu.azzert(Arrays.equals(address, record.address));
    tu.testComplete()
  })
}


public void testReverseLookupIpv6() {
  address = InetAddress.getByName("::1").getAddress();
  ptr = "ptr.vertx.io";
  DnsClient client = prepareClient(TestDnsServer.testReverseLookup(ptr));
  client.reverseLookup("::1", { result ->
    tu.checkThread()
    record = result.result
    tu.azzert(record instanceof Inet6Address);
    tu.azzert(ptr.equals(record.hostName));
    tu.azzert(Arrays.equals(address, record.address));
    tu.testComplete()
  })
}

tu.registerTests(this)
tu.appReady()

void vertxStop() {
  tu.unregisterAll()
  tu.appStopped()
  if (dnsServer != null) {
    dnsServer.stop()
  }
}