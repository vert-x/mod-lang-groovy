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

package org.vertx.groovy.platform

import groovy.transform.CompileStatic

import org.vertx.java.core.Handler
import org.vertx.java.core.json.JsonObject
import org.vertx.java.core.logging.Logger
import org.vertx.java.platform.Container as JContainer

/**
 * This class represents the container in which a verticle runs.<p>
 * An instance of this class will be created by the system and made available to
 * a running Verticle.
 * It contains methods to programmatically deploy other verticles, undeploy
 * verticles, get the configuration for a verticle and get the logger for a
 * verticle.<p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class Container {

  private JContainer jContainer

  Container(JContainer jContainer) {
    this.jContainer = jContainer
  }

  
  /**
   * Deploy a worker verticle programmatically
   * @param main The main of the verticle
   * @param config JSON config to provide to the verticle
   * @param instances The number of instances to deploy (defaults to 1)
   * @param multithreaded If true then a multi-threaded worker verticle will be deployed
   */
  void deployWorkerVerticle(String main, Map<String, Object> config = [:], int instances = 1, boolean multithreaded = false) {
    jContainer.deployWorkerVerticle(main, new JsonObject(config), instances, multithreaded, null)
  }

  /**
   * Deploy a worker verticle programmatically
   * @param main The main of the verticle
   * @param config JSON config to provide to the verticle
   * @param instances The number of instances to deploy (defaults to 1)
   * @param multithreaded If true then a multi-threaded worker verticle will be deployed
   * @param doneHandler The handler will be called passing in AsyncResult holding the deployment id or an exception
   * if it failed to deploy
   */
  void deployWorkerVerticle(String main, Map<String, Object> config = [:], int instances = 1, boolean multithreaded = false, Closure doneHandler) {
    jContainer.deployWorkerVerticle(main, new JsonObject(config), instances, multithreaded, doneHandler as Handler)
  }

  /**
   * Deploy a verticle programmatically
   * @param main The main of the verticle
   * @param instances The number of instances to deploy (defaults to 1)
   */
  void deployVerticle(String main, int instances) {
    jContainer.deployVerticle(main, new JsonObject( new HashMap<String, Object>()), instances, null)
  }

    /**
   * Deploy a verticle programmatically
   * @param main The main of the verticle
   * @param config JSON config to provide to the verticle
   * @param instances The number of instances to deploy (defaults to 1)
   */
  void deployVerticle(String main, Map<String, Object> config = [:], int instances = 1) {
    jContainer.deployVerticle(main, new JsonObject(config), instances, null)
  }


  /**
   * Deploy a verticle programmatically
   * @param main The main of the verticle
   * @param config JSON config to provide to the verticle
   * @param instances The number of instances to deploy (defaults to 1)
   * @param doneHandler The handler will be called passing in AsyncResult holding the deployment id or an exception
   * if it failed to deploy
   */
  void deployVerticle(String main, Map<String, Object> config = [:], int instances = 1, Closure doneHandler) {
    jContainer.deployVerticle(main, new JsonObject(config), instances, doneHandler as Handler)
  }

  /**
   * Deploy a module programmatically
   * @param moduleName The main of the module
   * @param instances The number of instances to deploy (defaults to 1)
   */
  void deployModule(String moduleName,  int instances) {
    jContainer.deployModule(moduleName, new JsonObject(new HashMap<String, Object>()), instances, null)
  }

  /**
   * Deploy a module programmatically
   * @param moduleName The main of the module
   * @param config JSON config to provide to the module
   * @param instances The number of instances to deploy (defaults to 1)
   */
  void deployModule(String moduleName, Map<String, Object> config = [:], int instances = 1) {
    jContainer.deployModule(moduleName, new JsonObject(config), instances, null)
  }

  /**
   * Deploy a module programmatically
   * @param moduleName The main of the module
   * @param config JSON config to provide to the module
   * @param instances The number of instances to deploy (defaults to 1)
   * @param doneHandler The handler will be called passing in AsyncResult holding the deployment id or an exception
   * if it failed to deploy
   */
  void deployModule(String moduleName, Map<String, Object> config = [:], int instances = 1, Closure doneHandler) {
    jContainer.deployModule(moduleName, new JsonObject(config), instances, doneHandler as Handler)
  }

  /**
   * Undeploy a verticle
   * @param deploymentID The deployment ID
   * @param doneHandler The handler will be called passing in AsyncResult
   * if it failed to undeploy
   */
  void undeployVerticle(String deploymentID, Closure doneHandler = {...args-> }) {
    jContainer.undeployVerticle(deploymentID, doneHandler as Handler)
  }

  /**
   * Undeploy a module
   * @param deploymentID The deployment ID
   * @param doneHandler The handler will be called passing in AsyncResult
   * if it failed to undeploy
   */
  void undeployModule(String deploymentID, Closure doneHandler = {...args-> }) {
    jContainer.undeployModule(deploymentID, doneHandler as Handler)
  }

  /**
   * Get the verticle configuration
   * @return a {@link java.util.Map} representing the JSON configuration
   */
  Map<String, Object> getConfig() {
    jContainer.config().toMap()
  }

  /**
   * Get the verticle logger
   * @return The logger
   */
  Logger getLogger() {
    jContainer.logger()
  }

  /**
   * Cause the container to exit
   */
  void exit() {
    jContainer.exit()
  }

  /**
   * Get an unmodifiable map of system, environment variables.
   * @return The map
   */
  Map<String, String> getEnv() {
    jContainer.env()
  }
}
