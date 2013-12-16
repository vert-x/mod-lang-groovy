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
package org.vertx.groovy.core.datagram

import groovy.transform.CompileStatic
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.impl.ClosureUtil
import org.vertx.groovy.core.streams.DrainSupport;
import org.vertx.groovy.core.streams.ExceptionSupport
import org.vertx.groovy.core.streams.ReadSupport
import org.vertx.java.core.AsyncResultHandler
import org.vertx.java.core.Handler;
import org.vertx.java.core.NetworkSupport
import org.vertx.java.core.datagram.DatagramPacket as JDatagramPacket
import org.vertx.java.core.datagram.DatagramSocket as JDatagramSocket

/**
 * A Datagram socket which can be used to send {@link DatagramPacket}'s to remote Datagram servers and receive {@link DatagramPacket}s .
 *
 * Usually you use a Datragram Client to send UDP over the wire. UDP is connection-less which means you are not connected
 * to the remote peer in a persistent way. Because of this you have to supply the address and port of the remote peer
 * when sending data.
 *
 * You can send data to ipv4 or ipv6 addresses, which also include multicast addresses.
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@CompileStatic
class DatagramSocket implements DrainSupport<DatagramSocket>, ExceptionSupport<DatagramSocket>, ReadSupport<DatagramSocket>, NetworkSupport<DatagramSocket> {
  private final JDatagramSocket socket
  DatagramSocket(JDatagramSocket socket) {
    this.socket = socket;
  }
  @Override
  DatagramSocket setWriteQueueMaxSize(int maxSize) {
    socket.setWriteQueueMaxSize(maxSize)
    this
  }

  @Override
  boolean isWriteQueueFull() {
    socket.writeQueueFull()
  }

  @Override
  DatagramSocket drainHandler(Closure handler) {
    socket.drainHandler(handler as Handler)
    this
  }

  @Override
  DatagramSocket dataHandler(Closure handler) {
    socket.dataHandler({
      handler(new DatagramPacket((JDatagramPacket) it))
    } as Handler)
    this
  }

  @Override
  DatagramSocket resume() {
    socket.resume()
    this
  }

  @Override
  DatagramSocket exceptionHandler(Closure handler) {
    socket.exceptionHandler(handler as Handler)
    this
  }

  @Override
  DatagramSocket setSendBufferSize(int size) {
    socket.setSendBufferSize(size)
    this
  }

  @Override
  DatagramSocket setReceiveBufferSize(int size) {
    socket.setReceiveBufferSize(size)
    this
  }

  @Override
  DatagramSocket setReuseAddress(boolean reuse) {
    socket.setReuseAddress(reuse)
    this
  }

  @Override
  DatagramSocket setTrafficClass(int trafficClass) {
    socket.setTrafficClass(trafficClass)
    this
  }

  @Override
  int getSendBufferSize() {
    socket.getSendBufferSize()
  }

  @Override
  int getReceiveBufferSize() {
    socket.getReceiveBufferSize()
  }

  @Override
  boolean isReuseAddress() {
    socket.isReuseAddress()
  }

  @Override
  int getTrafficClass() {
    socket.getTrafficClass()
  }

  @Override
  DatagramSocket pause() {
    socket.pause()
    this
  }


  /**
   * Send the given {@link Buffer} to the remote host. The {@link Closure} will be notified once the
   * send completes.
   *
   *
   * @param packet    the {@link Buffer} to send
   * @param host      the host address of the remote peer
   * @param port      the host port of the remote peer
   * @param handler   the {@link Closure} to notify once the send completes.
   * @return self     itself for method chaining
   */
  DatagramSocket send(Buffer packet, String host, int port, Closure handler) {
    socket.send(packet.toJavaBuffer(), host, port, wrapAsyncHandler(handler))
    this
  }

  /**
   * Send the given {@link String} to the remote host. The {@link Closure} will be notified once the
   * send completes.
   *
   *
   * @param str       the {@link String} to send
   * @param host      the host address of the remote peer
   * @param port      the host port of the remote peer
   * @param handler   the {@link Closure} to notify once the send completes.
   * @return self     itself for method chaining
   */
  DatagramSocket send(String str, String host, int port, Closure handler) {
    socket.send(str, host, port, wrapAsyncHandler(handler))
    this
  }

  /**
   * Write the given {@link String} to the {@link InetSocketAddress} using the given encoding. The {@link Handler} will be notified once the
   * write completes.
   *
   *
   * @param str       the {@link String} to send
   * @param enc       the charset used for encoding
   * @param host      the host address of the remote peer
   * @param port      the host port of the remote peer
   * @param handler   the {@link Closure} to notify once the send completes.
   * @return self     itself for method chaining
   */
  DatagramSocket send(String str, String enc, String host, int port, Closure handler) {
    socket.send(str, enc, host, port, wrapAsyncHandler(handler))
    this
  }

  /**
   * Gets the {@link java.net.StandardSocketOptions#SO_BROADCAST} option.
   */
  boolean isBroadcast() {
    socket.isBroadcast()
  }

  /**
   * Sets the {@link java.net.StandardSocketOptions#SO_BROADCAST} option.
   */
  DatagramSocket setBroadcast(boolean broadcast) {
    socket.setBroadcast(broadcast)
    this
  }

  /**
   * Gets the {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP} option.
   *
   * @return {@code true} if and only if the loopback mode has been disabled
   */
  boolean isMulticastLoopbackMode() {
    socket.isMulticastLoopbackMode()
  }

  /**
   * Sets the {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP} option.
   *
   * @param loopbackModeDisabled
   *        {@code true} if and only if the loopback mode has been disabled
   */
  DatagramSocket setMulticastLoopbackMode(boolean loopbackModeDisabled) {
    socket.setMulticastLoopbackMode(loopbackModeDisabled)
    this
  }

  /**
   * Gets the {@link java.net.StandardSocketOptions#IP_MULTICAST_TTL} option.
   */
  int getMulticastTimeToLive() {
    socket.getMulticastTimeToLive()
  }

  /**
   * Sets the {@link java.net.StandardSocketOptions#IP_MULTICAST_TTL} option.
   */
  DatagramSocket setMulticastTimeToLive(int ttl) {
    socket.setMulticastTimeToLive(ttl)
    this
  }

  /**
   * Gets the {@link java.net.StandardSocketOptions#IP_MULTICAST_IF} option.
   */
  String getMulticastNetworkInterface() {
    socket.getMulticastNetworkInterface()
  }

  /**
   * Sets the {@link java.net.StandardSocketOptions#IP_MULTICAST_IF} option.
   */
  DatagramSocket setMulticastNetworkInterface(String iface) {
    socket.setMulticastNetworkInterface(iface)
    this
  }

  /**
   * Close the {@link DatagramSocket} implementation asynchronous and notifies the handler once done.
   */
  void close(Closure handler) {
    socket.close(ClosureUtil.wrapAsyncResultHandler(handler))
  }

  /**
   * Close the {@link DatagramSocket} implementation asynchronous.
   */
  void close() {
    socket.close()
  }

  /**
   * Return the {@link InetSocketAddress} to which this {@link DatagramSocket} is bound too.
   */
  InetSocketAddress getLocalAddress() {
    socket.localAddress()
  }

  /**
   * Joins a multicast group and so start listen for packets send to it. The {@link Closure} is notified once the operation completes.
   *
   *
   * @param   multicastAddress  the address of the multicast group to join
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket listenMulticastGroup(String multicastAddress, Closure handler) {
    socket.listenMulticastGroup(multicastAddress, wrapAsyncHandler(handler))
    this
  }

  /**
   * Joins a multicast group and so start listen for packets send to it on the given network interface.
   * The {@link Closure} is notified once the operation completes.
   *
   *
   * @param   multicastAddress  the address of the multicast group to join
   * @param   networkInterface  the network interface on which to listen for packets.
   * @param   source            the address of the source for which we will listen for mulicast packets
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket listenMulticastGroup(
          String multicastAddress, String networkInterface, String source, Closure handler) {
    socket.listenMulticastGroup(multicastAddress, networkInterface, source, wrapAsyncHandler(handler))
    this
  }

  /**
   * Leaves a multicast group and so stop listen for packets send to it.
   * The {@link Closure} is notified once the operation completes.
   *
   *
   * @param   multicastAddress  the address of the multicast group to leave
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket unlistenMulticastGroup(String multicastAddress, Closure handler) {
    socket.unlistenMulticastGroup(multicastAddress, wrapAsyncHandler(handler))
    this
  }


  /**
   * Leaves a multicast group and so stop listen for packets send to it on the given network interface.
   * The {@link Closure} is notified once the operation completes.
   *
   *
   * @param   multicastAddress  the address of the multicast group to join
   * @param   networkInterface  the network interface on which to listen for packets.
   * @param   source            the address of the source for which we will listen for mulicast packets
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket unlistenMulticastGroup(
          String multicastAddress, String networkInterface, String source,
          Closure handler) {
    socket.unlistenMulticastGroup(multicastAddress, networkInterface, source, wrapAsyncHandler(handler))
    this
  }

  /**
   * Block the given sourceToBlock address for the given multicastAddress and notifies the {@link Handler} once
   * the operation completes.
   *
   *
   * @param   multicastAddress  the address for which you want to block the sourceToBlock
   * @param   sourceToBlock     the source address which should be blocked. You will not receive an multicast packets
   *                            for it anymore.
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket blockMulticastGroup(
          String multicastAddress, String sourceToBlock, Closure handler) {
    socket.blockMulticastGroup(multicastAddress, sourceToBlock, wrapAsyncHandler(handler))
    this
  }

  /**
   * Block the given sourceToBlock address for the given multicastAddress on the given network interface and notifies
   * the {@link Closure} once the operation completes.
   *
   *
   * @param   multicastAddress  the address for which you want to block the sourceToBlock
   * @param   networkInterface  the network interface on which the blocking should accour.
   * @param   sourceToBlock     the source address which should be blocked. You will not receive an multicast packets
   *                            for it anymore.
   * @param   handler           then handler to notify once the operation completes
   * @return  this              returns itself for method-chaining
   */
  DatagramSocket blockMulticastGroup(
          String multicastAddress, String networkInterface,
          String sourceToBlock, Closure handler) {
    socket.blockMulticastGroup(multicastAddress, networkInterface, sourceToBlock, wrapAsyncHandler(handler))
    this
  }

  /**
   * @see #listen(java.net.InetSocketAddress, Closure)
   */
  DatagramSocket listen(String address, int port, Closure handler) {
    socket.listen(address, port, wrapAsyncHandler(handler))
    this
  }

  /**
   * @see #listen(java.net.InetSocketAddress, Closure)
   */
  DatagramSocket listen(int port, Closure handler) {
    socket.listen(port, wrapAsyncHandler(handler))
    this
  }

  /**
   * Makes this {@link DatagramSocket} listen to the given {@link InetSocketAddress}. Once the operation completes
   * the {@link Closure} is notified.
   *
   * @param local     the {@link InetSocketAddress} on which the {@link DatagramSocket} will listen for {@link DatagramPacket}s.
   * @param handler   the {@link Closure} to notify once the operation completes
   * @return this     itself for method-chaining
   */
  DatagramSocket listen(InetSocketAddress local, Closure handler) {
    socket.listen(local, wrapAsyncHandler(handler))
    this
  }

  private AsyncResultHandler wrapAsyncHandler(Closure hndlr) {
    if (hndlr == null) {
      null
    } else {
      ClosureUtil.wrapAsyncResultHandler(hndlr, {
        this
      });
    }
  }

}
