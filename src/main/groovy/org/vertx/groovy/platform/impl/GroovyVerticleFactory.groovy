/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package org.vertx.groovy.platform.impl

import org.codehaus.groovy.control.CompilerConfiguration
import org.vertx.java.core.Vertx as JVertx
import org.vertx.java.core.logging.Logger
import org.vertx.java.core.logging.impl.LoggerFactory;
import org.vertx.java.platform.Container as JContainer
import org.vertx.java.platform.Verticle as JVerticle
import org.vertx.java.platform.VerticleFactory
import org.vertx.groovy.core.Vertx
import org.vertx.groovy.platform.Container
import org.vertx.groovy.platform.Verticle

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.lang.reflect.Method

/**
 * <p>
 *   Factory for verticles in groovy.
 * </p><h2>
 *   Compiler Configuration
 * </h2><p>
 *   The groovy {@link CompilerConfiguration} may be configured through a
 *   script or properties file. If the {@code vertx.groovy.compilerConfiguration}
 *   system property is set, it searches for the provided resource. If unset,
 *   then it looks first for {@code compilerConfiguration.groovy}, then
 *   {@code compilerConfiguration.properties} on the classpath.
 * </p><p>
 *   The resource may be a groovy ConfigSlurper script, which may set
 *   properties and tweak the configurer programmatically. The key
 *   {@code customizer} should be a closure accepting the
 *   {@link CompilerConfiguration}. It may return a different
 *   CompilerConfiguration instance to replace it.
 * </p><p>
 *   Alternatively, the resource may be a simple java properties file.
 * </p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 * @author Alexander Klein
 * @author Danny Kirchmeier
 */
public class GroovyVerticleFactory implements VerticleFactory {

  private static final String CONFIGURATION_PROPERTY = "vertx.groovy.compilerConfiguration"
  private static Logger log = LoggerFactory.getLogger(GroovyVerticleFactory.class)

  private JVertx vertx
  private JContainer container
  private GroovyClassLoader gcl
  CompilerConfiguration compilerCfg

  @Override
  public void init(JVertx vertx, JContainer container, ClassLoader cl) {
    this.vertx = vertx
    this.container = container
    compilerCfg = createCompilerConfiguration(cl)
    this.gcl = new GroovyClassLoader(cl, compilerCfg)
  }

  private CompilerConfiguration createCompilerConfiguration(ClassLoader cl) {
    Closure customizer
    Properties properties = new Properties()
    URL url = findConfigurationResource(cl)
    if(url) {
      log.trace("Configuring groovy compiler with ${url}")
      try {
        if(url.file.toLowerCase().endsWith('.groovy')) {
          ConfigSlurper slurper = new ConfigSlurper(classLoader: new GroovyClassLoader(cl))
          ConfigObject cObject = slurper.parse(url)
          customizer = cObject.remove('customizer') as Closure
          properties.putAll(cObject.toProperties())
        } else {
          url.withInputStream {
              properties.load(it)
          }
        }
      } catch(e) {
        log.error("Error loading Groovy CompilerConfiguration properties from $url", e)
      }
    } else {
      log.trace("No groovy configuration file found.")
    }

    CompilerConfiguration compilerCfg = new CompilerConfiguration(CompilerConfiguration.DEFAULT)
    compilerCfg.configure(properties)
    if(customizer) {
      def result = customizer.call(compilerCfg)
      // Expectation: If result isn't a CompilerConfiguration, the original one has been modified
      if(result instanceof CompilerConfiguration)
        compilerCfg = result
    }
    return compilerCfg
  }

  private URL findConfigurationResource(ClassLoader cl) {
    try{
      String prop = System.getProperty(CONFIGURATION_PROPERTY)
      if(prop){
        return cl.getResource(prop);
      }
    } catch(SecurityException ignored){
    }

    URL url = cl.getResource('compilerConfiguration.groovy')
    if(url) return url

    url = cl.getResource('compilerConfiguration.properties')
    if(url) return url

    return null;
  }

  public JVerticle createVerticle(String main) throws Exception {

    JVerticle verticle

    if (main.endsWith('.groovy')) {
      URL url = gcl.getResource(main)
      if (url == null) {
        throw new IllegalStateException("Cannot find main script: '${main}' on classpath");
      }
      GroovyCodeSource gcs = new GroovyCodeSource(url)
      Class clazz = gcl.parseClass(gcs)

      Method stop
      try {
        stop = clazz.getMethod('vertxStop', (Class<?>[])null)
      } catch (NoSuchMethodException e) {
        stop = null
      }
      final Method mstop = stop

      Method mrun
      try {
        mrun = clazz.getMethod('run', (Class<?>[])null)
      } catch (NoSuchMethodException e) {
        throw new IllegalStateException('Groovy script must have run() method [whether implicit or not]')
      }

      // Inject vertx into the script binding

      def script = clazz.newInstance() // Casting to groovy.lang.Script fails because classloaders are different
      script.setBinding(createBinding())

      verticle = new JVerticle() {
        public void start() {
          // The throwables need to be thrown up to the PlatformManager so the deployment can fail
          try {
            mrun.invoke(script, (Object[]) null)
          } catch (RuntimeException e) {
            throw e;
          } catch (Exception e) {
            throw new RuntimeException(e);
          } catch (Error e) {
            throw e;
          }
        }

        public void stop() {
          if (mstop != null) {
            // The throwables need to be thrown up to the PlatformManager so the stop can fail
            try {
              mstop.invoke(script, (Object[]) null)
            } catch (RuntimeException e) {
              throw e;
            } catch (Exception e) {
              throw new RuntimeException(e);
            } catch (Error e) {
              throw e;
            }
          }
        }
      }
    } else {
      // Compiled Groovy - should already extend Verticle
      Class clazz = gcl.loadClass(main)
      if (Verticle.class.isAssignableFrom(clazz)) {
        verticle = new GroovyVerticle((Verticle) clazz.newInstance())
      }
      else {
        verticle = (JVerticle) clazz.newInstance()
      }
      verticle?.setVertx(vertx)
      verticle?.setContainer(container)
    }

    return verticle
  }

  public void reportException(Logger logger, Throwable t) {
    logger.error('Exception in Groovy verticle', t)
  }

  public void close() {
    gcl?.close()
  }

  /*
   * Inject vertx into the script binding
   */
  @CompileStatic(TypeCheckingMode.SKIP)
  private Binding createBinding() {

    def binding = new Binding()
    binding.setVariable('vertx', new Vertx(vertx))
    binding.setVariable('container', new Container(container))
    binding
  }

}

