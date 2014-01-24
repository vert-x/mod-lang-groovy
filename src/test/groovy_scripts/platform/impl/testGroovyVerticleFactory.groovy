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

package platform.impl

import org.vertx.groovy.platform.impl.GroovyVerticleFactory
import org.vertx.groovy.testframework.TestUtils
import org.vertx.java.platform.Container as JContainer

tu = new TestUtils(vertx)

def testDefaultCompilerConfigurationInitialization() {
    GroovyVerticleFactory factory = new GroovyVerticleFactory();

    factory.init(null, null, Thread.currentThread().getContextClassLoader());

    tu.azzert factory.compilerCfg.asBoolean()
    tu.azzert factory.compilerCfg.scriptBaseClass == 'groovy.lang.Script'
    tu.azzert factory.compilerCfg.sourceEncoding == 'ISO1252'
    tu.azzert factory.compilerCfg.compilationCustomizers.size() == 1
    tu.azzert factory.compilerCfg.compilationCustomizers[0].imports.size() == 1
    tu.azzert factory.compilerCfg.compilationCustomizers[0].imports[0].classNode.name == 'org.codehaus.groovy.control.CompilerConfiguration'

    tu.testComplete()
}

def testSystemPropertyCompilerConfigurationInitialization() {
    GroovyVerticleFactory factory = new GroovyVerticleFactory();

    System.setProperty("vertx.groovy.compilerConfiguration", 'no.properties')
    System.setProperty("groovy.source.encoding", 'UTF-8')
    factory.init(null, null, Thread.currentThread().getContextClassLoader());

    tu.azzert factory.compilerCfg.asBoolean()
    tu.azzert factory.compilerCfg.scriptBaseClass == null
    tu.azzert factory.compilerCfg.sourceEncoding == 'UTF-8'

    tu.testComplete()
}

def testUnknownCompilerConfigurationInitialization() {
    GroovyVerticleFactory factory = new GroovyVerticleFactory();

    System.setProperty("vertx.groovy.compilerConfiguration", 'no.properties')
    factory.init(null, null, Thread.currentThread().getContextClassLoader());

    tu.azzert factory.compilerCfg.asBoolean()
    tu.azzert factory.compilerCfg.scriptBaseClass == null
    tu.azzert factory.compilerCfg.sourceEncoding == 'US-ASCII'

    tu.testComplete()
}

def testCustomCompilerConfigurationInitialization() {
    GroovyVerticleFactory factory = new GroovyVerticleFactory();

    System.setProperty("vertx.groovy.compilerConfiguration", 'compilerConfiguration2.properties')
    factory.init(null, null, Thread.currentThread().getContextClassLoader());

    tu.azzert factory.compilerCfg.asBoolean()
    tu.azzert factory.compilerCfg.scriptBaseClass == 'groovy.lang.Script'
    tu.azzert factory.compilerCfg.sourceEncoding == 'US-ASCII'

    tu.testComplete()
}

def testCustomCompilerConfigurationInitializationWithCustomizer() {
    GroovyVerticleFactory factory = new GroovyVerticleFactory();

  System.setProperty("vertx.groovy.compilerConfiguration", 'compilerConfiguration.groovy')
    factory.init(null, null, Thread.currentThread().getContextClassLoader());

    tu.azzert factory.compilerCfg.asBoolean()
    tu.azzert factory.compilerCfg.scriptBaseClass == 'groovy.lang.Script'
    tu.azzert factory.compilerCfg.sourceEncoding == 'ISO1252'
    tu.azzert factory.compilerCfg.compilationCustomizers.size() == 1
    tu.azzert factory.compilerCfg.compilationCustomizers[0].imports.size() == 1
    tu.azzert factory.compilerCfg.compilationCustomizers[0].imports[0].classNode.name == 'org.codehaus.groovy.control.CompilerConfiguration'

    tu.testComplete()
}

tu.registerTests(this)
tu.appReady()

void vertxStop() {
    tu.unregisterAll()
    tu.appStopped()
}
