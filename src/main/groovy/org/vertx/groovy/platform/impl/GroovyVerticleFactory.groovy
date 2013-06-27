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
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GroovyVerticleFactory implements VerticleFactory {

  private static Logger log = LoggerFactory.getLogger(GroovyVerticleFactory.class)
  private JVertx vertx
  private JContainer container
  private GroovyClassLoader gcl

  @Override
  public void init(JVertx vertx, JContainer container, ClassLoader cl) {
    this.vertx = vertx
    this.container = container

    CompilerConfiguration configuration = new CompilerConfiguration()
    if (Boolean.getBoolean('vertx.lang.groovy.indy')) {
      // enable InvokeDynamic mode
      configuration.getOptimizationOptions().put("int", false);
      configuration.getOptimizationOptions().put("indy", true);
    }
    this.gcl = new GroovyClassLoader(cl)
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

