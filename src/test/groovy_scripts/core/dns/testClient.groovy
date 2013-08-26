package core.dns

import org.vertx.groovy.testframework.TestUtils

tu = new TestUtils(vertx)
tu.checkThread()

def testResolveA() {
  // TODO: Write me
  tu.testComplete()
}

def testResolveAAAA() {
  // TODO: Write me
  tu.testComplete()
}

def testResolveMX() {
  // TODO: Write me
  tu.testComplete()
}

def testResolveTXT() {
  // TODO: Write me
  tu.testComplete()
}

public void testResolveNS() {
  // TODO: Write me
  tu.testComplete()
}

public void testResolveCNAME() {
  // TODO: Write me
  tu.testComplete()
}

public void testResolvePTR() {
  // TODO: Write me
  tu.testComplete()
}


public void testResolveSRV() {
  // TODO: Write me
  tu.testComplete()
}

public void testLookup4() {
  // TODO: Write me
  tu.testComplete()
}

public void testLookup6() {
  // TODO: Write me
  tu.testComplete()
}

public void testLookup() {
  // TODO: Write me
  tu.testComplete()
}

public void testLookupNonExisting() {
  // TODO: Write me
  tu.testComplete()
}

public void testReverseLookupIpv4() {
  // TODO: Write me
  tu.testComplete()
}


public void testReverseLookupIpv6() {
  // TODO: Write me
  tu.testComplete()
}


tu.registerTests(this)
tu.appReady()

