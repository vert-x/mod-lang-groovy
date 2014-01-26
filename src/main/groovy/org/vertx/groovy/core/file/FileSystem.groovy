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

package org.vertx.groovy.core.file

import groovy.transform.CompileStatic
import org.vertx.groovy.core.impl.ClosureUtil
import org.vertx.java.core.buffer.Buffer as JBuffer
import org.vertx.java.core.file.AsyncFile as JAsyncFile
import org.vertx.java.core.file.FileProps
import org.vertx.java.core.file.FileSystem as JFileSystem
import org.vertx.java.core.file.FileSystemProps as JFileSystemProps
import org.vertx.groovy.core.buffer.Buffer

/**
 * Contains a broad set of operations for manipulating files.<p>
 * An asynchronous and a synchronous version of each operation is provided.<p>
 * The asynchronous versions take an {@code AsynchronousResultHandler} which is
 * called when the operation completes or an error occurs.<p>
 * The synchronous versions return the results, or throw exceptions directly.<p>
 * It is highly recommended the asynchronous versions are used unless you are sure the operation
 * will not block for a significant period of time<p>
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
class FileSystem {

  private final JFileSystem jFS

  public FileSystem(JFileSystem jFS) {
    this.jFS = jFS
  }

  /**
   * Copy a file from the path {@code from} to path {@code to}, asynchronously.<p>
   * If {@code recursive} is {@code true} and {@code from} represents a directory, then the directory and its contents
   * will be copied recursively to the destination {@code to}.<p>
   * The copy will fail if the destination if the destination already exists.<p>
   */
  FileSystem copy(String from, String to, boolean recursive = false, Closure handler) {
    jFS.copy(from, to, recursive, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #copy(String, String, boolean, Closure)}
   */
  FileSystem copySync(String from, String to, boolean recursive = false) {
    jFS.copySync(from, to, recursive)
    this
  }

  /**
   * Move a file from the path {@code from} to path {@code to}, asynchronously.<p>
   * The move will fail if the destination already exists.<p>
   */
  FileSystem move(String from, String to, Closure handler) {
    jFS.move(from, to, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #move(String, String, Closure)}
   */
  FileSystem moveSync(String from, String to) {
    jFS.moveSync(from, to)
    this
  }

  /**
   * Truncate the file represented by {@code path} to length {@code len} in bytes, asynchronously.<p>
   * The operation will fail if the file does not exist or {@code len} is less than {@code zero}.
   */
  FileSystem truncate(String path, long len, Closure handler) {
    jFS.truncate(path, len, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #truncate(String, long, Closure)}
   */
  FileSystem truncateSync(String path, long len) {
    jFS.truncateSync(path, len)
    this
  }

  /**
   * Change the permissions on the file represented by {@code path} to {@code perms}, asynchronously.
   * The permission String takes the form rwxr-x--- as
   * specified in {<a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>}.<p>
   * If the file is directory then all contents will also have their permissions changed recursively. Any directory permissions will
   * be set to {@code dirPerms}, whilst any normal file permissions will be set to {@code perms}.<p>
   */
  FileSystem chmod(String path, String perms, String dirPerms = null, Closure handler) {
    jFS.chmod(path, perms, dirPerms, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #chmod(String, String, String, Closure)}
   */
  FileSystem chmodSync(String path, String perms, String dirPerms = null) {
    jFS.chmodSync(path, perms, dirPerms)
    this
  }


  /**
   * Change the ownership on the file represented by {@code path} to {@code user} and {code group}, asynchronously.
   *
   */
  FileSystem chown(String path, String user, String group, Closure handler) {
    jFS.chown(path, user, group, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #chown(String, String, String, Handler)}
   *
   */
  FileSystem chownSync(String path, String user, String group) {
    jFS.chownSync(path, user, group);  
    this
  }


  /**
   * Obtain properties for the file represented by {@code path}, asynchronously.
   * If the file is a link, the link will be followed.
   */
  FileSystem props(String path, Closure handler) {
    jFS.props(path, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #props(String, Closure)}
   */
  FileProps propsSync(String path) {
    return jFS.propsSync(path)
  }

  /**
   * Obtain properties for the link represented by {@code path}, asynchronously.
   * The link will not be followed.
   */
  FileSystem lprops(String path, Closure handler) {
    jFS.lprops(path, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #lprops(String, Closure)}
   */
  FileProps lpropsSync(String path) {
    return jFS.lpropsSync(path)
  }

  /**
   * Create a hard link on the file system from {@code link} to {@code existing}, asynchronously.
   */
  FileSystem link(String link, String existing, Closure handler) {
    jFS.link(link, existing, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #link(String, String, Closure)}
   */
  FileSystem linkSync(String link, String existing) {
    jFS.linkSync(link, existing)
    this
  }

  /**
   * Create a symbolic link on the file system from {@code link} to {@code existing}, asynchronously.
   */
  FileSystem symlink(String link, String existing, Closure handler) {
    jFS.symlink(link, existing, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #link(String, String, Closure)}
   */
  FileSystem symlinkSync(String link, String existing) {
    jFS.symlinkSync(link, existing)
    this
  }

  /**
   * Unlinks the link on the file system represented by the path {@code link}, asynchronously.
   */
  FileSystem unlink(String link, Closure handler) {
    jFS.unlink(link, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #unlink(String, Closure)}
   */
  FileSystem unlinkSync(String link) {
    jFS.unlinkSync(link)
    this
  }

  /**
   * Returns the path representing the file that the symbolic link specified by {@code link} points to, asynchronously.
   */
  FileSystem readSymlink(String link, Closure handler) {
    jFS.readSymlink(link, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #readSymlink(String, Closure)}
   */
  String readSymlinkSync(String link) {
    return jFS.readSymlinkSync(link)
  }

  /**
   * Deletes the file represented by the specified {@code path}, asynchronously.<p>
   * If the path represents a directory and {@code recursive = true} then the directory and its contents will be
   * deleted recursively.
   */
  FileSystem delete(String path, boolean recursive = false, Closure handler) {
    jFS.delete(path, recursive, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #delete(String, boolean, Closure)}
   */
  FileSystem deleteSync(String path, boolean recursive) {
    jFS.deleteSync(path, recursive)
    this
  }

  /**
   * Create the directory represented by {@code path}, asynchronously.<p>
   * The new directory will be created with permissions as specified by {@code perms}.
   * The permission String takes the form rwxr-x--- as specified
   * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.<p>
   * If {@code createParents} is set to {@code true} then any non-existent parent directories of the directory
   * will also be created.<p>
   * The operation will fail if the directory already exists.<p>
   */
  FileSystem mkdir(String path, String perms = null, boolean createParents = false, Closure handler) {
    jFS.mkdir(path, perms, createParents, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #mkdir(String, String, boolean, Closure)}
   */
  FileSystem mkdirSync(String path, String perms = null, boolean createParents = false) {
    jFS.mkdirSync(path, perms, createParents)
    this
  }

  /**
   * Read the contents of the directory specified by {@code path}, asynchronously.<p>
   * The result is an array of String representing the paths of the files inside the directory.
   */
  FileSystem readDir(String path, String filter = null, Closure handler) {
    jFS.readDir(path, filter, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #readDir(String, String, Closure)}
   */
  String[] readDirSync(String path, String filter) {
    return jFS.readDirSync(path, filter)
  }

  /**
   * Reads the entire file as represented by the path {@code path} as a {@link Buffer}, asynchronously.<p>
   * Do not user this method to read very large files or you risk running out of available RAM.
   */
  FileSystem readFile(String path, Closure handler) {
    jFS.readFile(path, ClosureUtil.wrapAsyncResultHandler(handler, { JBuffer buffer ->
      new Buffer(buffer)
    }))
    this
  }

  /**
   * Synchronous version of {@link #readFile(String, Closure)}
   */
  Buffer readFileSync(String path) {
    return new Buffer(jFS.readFileSync(path))
  }

  /**
   * Creates the file, and writes the specified {@code Buffer data} to the file represented by the path {@code path},
   * asynchronously.
   */
  FileSystem writeFile(String path, Buffer data, Closure handler) {
    jFS.writeFile(path, data.toJavaBuffer(), ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Creates the file, and writes the specified {@code String data} to the file represented by the path {@code path},
   * asynchronously.
   */
  FileSystem writeFile(String path, String data, Closure handler) {
    jFS.writeFile(path, new org.vertx.java.core.buffer.Buffer(data), ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #writeFile(String, Buffer, Closure)}
   */
  FileSystem writeFileSync(String path, Buffer data) {
    jFS.writeFileSync(path, data.toJavaBuffer())
    this
  }

  /**
   * Synchronous version of {@link #writeFile(String, String, Closure)}
   */
  FileSystem writeFileSync(String path, String data) {
    jFS.writeFileSync(path, new org.vertx.java.core.buffer.Buffer(data))
    this
  }

  /**
   * Open the file represented by {@code path}, asynchronously.<p>
   * If {@code read} is {@code true} the file will be opened for reading. If {@code write} is {@code true} the file
   * will be opened for writing.<p>
   * If the file does not already exist and
   * {@code createNew} is {@code true} it will be created with the permissions as specified by {@code perms}, otherwise
   * the operation will fail.<p>
   * If {@code flush} is {@code true} then all writes will be automatically flushed through OS buffers to the underlying
   * storage on each write.
   */
  FileSystem open(String path, String perms = null, boolean read = true, boolean write = true, boolean createNew = true, boolean flush = false, Closure handler) {
    jFS.open(path, perms, read, write, createNew, flush, ClosureUtil.wrapAsyncResultHandler(handler, { JAsyncFile file ->
      new AsyncFile(file)
    }))
    this
  }

  /**
   * Synchronous version of {@link #open(String, String, boolean, boolean, boolean, boolean, Closure)}
   */
  AsyncFile openSync(String path, String perms = null, boolean read = true, boolean write = true, boolean createNew = true, boolean flush = false) {
    return new AsyncFile(jFS.openSync(path, perms, read, write, createNew, flush))
  }

  /**
   * Creates an empty file with the specified {@code path}, asynchronously.
   */
  FileSystem createFile(String path, String perms = null, Closure handler) {
    jFS.createFile(path, perms, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #createFile(String, String, Closure)}
   */
  FileSystem createFileSync(String path, String perms = null) {
    jFS.createFileSync(path, perms)
    this
  }

  /**
   * Determines whether the file as specified by the path {@code path} exists, asynchronously.
   */
  FileSystem exists(String path, Closure handler) {
    jFS.exists(path, ClosureUtil.wrapAsyncResultHandler(handler))
    this
  }

  /**
   * Synchronous version of {@link #exists(String, Closure)}
   */
  boolean existsSync(String path) {
    return jFS.existsSync(path)
  }

  /**
   * Returns properties of the file-system being used by the specified {@code path}, asynchronously.
   */
  FileSystem fsProps(String path, Closure handler) {
    jFS.fsProps(path, ClosureUtil.wrapAsyncResultHandler(handler, { JFileSystemProps props ->
      new FileSystemProps(props)
    }))
    this
  }

  /**
   * Synchronous version of {@link #fsProps(String, Closure)}
   */
  FileSystemProps fsPropsSync(String path) {
    new FileSystemProps(jFS.fsPropsSync(path))
  }
}
