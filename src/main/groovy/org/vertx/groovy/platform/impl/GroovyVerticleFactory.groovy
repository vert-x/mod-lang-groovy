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

import org.vertx.java.core.Vertx as JVertx
import org.vertx.java.core.logging.Logger
import org.vertx.java.core.logging.impl.LoggerFactory;
import org.vertx.java.platform.Container as JContainer
import org.vertx.java.platform.Verticle as JVerticle
import org.vertx.java.platform.VerticleFactory
import org.vertx.groovy.core.Vertx
import org.vertx.groovy.platform.Container
import org.vertx.groovy.platform.Verticle

import java.lang.reflect.Method

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GroovyVerticleFactory implements VerticleFactory {

  private static Logger log = LoggerFactory.getLogger(GroovyVerticleFactory.class)
  private JVertx vertx
  private JContainer container
  private ClassLoader cl

  @Override
  public void init(JVertx vertx, JContainer container, ClassLoader cl) {
    this.vertx = vertx
    this.container = container
    this.cl = cl
  }

  public JVerticle createVerticle(String main) throws Exception {

    JVerticle verticle

    if (main.endsWith(".groovy")) {
      URL url = cl.getResource(main)
      if (url == null) {
        throw new IllegalStateException("Cannot find main script: " + main + " on classpath");
      }
      GroovyCodeSource gcs = new GroovyCodeSource(url)
      GroovyClassLoader gcl = new GroovyClassLoader(cl)
      Class clazz = gcl.parseClass(gcs)

      Method stop
      try {
        stop = clazz.getMethod("vertxStop", (Class<?>[])null)
      } catch (NoSuchMethodException e) {
        stop = null
      }
      final Method mstop = stop

      Method run
      try {
        run = clazz.getMethod("run", (Class<?>[])null)
      } catch (NoSuchMethodException e) {
        run = null
      }
      final Method mrun = run

      if (run == null) {
        throw new IllegalStateException("Groovy script must have run() method [whether implicit or not]")
      }

      Script script = (Script)clazz.newInstance()

      // Inject vertx into the script binding
      Binding binding = new Binding()
      binding.setVariable("vertx", new Vertx(vertx))
      binding.setVariable("container", new Container(container))
      script.setBinding(binding)

      verticle = new JVerticle() {
        public void start() {
          try {
            mrun.invoke(script, (Object[])null)
          } catch (Throwable t) {
            reportException(log, t)
          }
        }

        public void stop() {
          if (mstop != null) {
            try {
              mstop.invoke(script, (Object[])null)
            } catch (Throwable t) {
              reportException(log, t)
            }
          }
        }
      }
    } else {
      // Compiled Groovy - should already extend Verticle
      Class clazz = cl.loadClass(main)
      if (clazz instanceof Verticle) {
        verticle = new GroovyVerticle((JVerticle) clazz.newInstance())
      }
      else {
        verticle = (JVerticle) clazz.newInstance()
      }
    }

    return verticle
  }

  public void reportException(Logger logger, Throwable t) {
    logger.error("Exception in Groovy verticle", t)
  }

  public void close() {
  }
}

