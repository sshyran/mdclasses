/*
 * Copied from https://github.com/x-stream/xstream/blob/master/xstream/src/java/com/thoughtworks/xstream/io/xml/QNameMap.java
 * on terms described below.
 * Changed Collections.synchronizedMap to ConcurrentHashMap.
 */

/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007, 2014, 2015 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 01. October 2004 by James Strachan
 */
package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.io.xml.QNameMap;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Represents a mapping of {@link QName} instances to Java class names allowing class aliases and namespace aware
 * mappings of QNames to class names.
 *
 * @author James Strachan
 * @version $Revision$
 */
public class ConcurrentQNameMap extends QNameMap {

  // lets make the mapping a no-op unless we specify some mapping
  private Map<QName, String> qnameToJava;
  private Map<String, QName> javaToQName;
  private String defaultPrefix = "";
  private String defaultNamespace = "";

  /**
   * Returns the Java class name that should be used for the given QName. If no explicit mapping has been made then
   * the localPart of the QName is used which is the normal default in XStream.
   */
  @Override
  public String getJavaClassName(final QName qname) {
    if (qnameToJava != null) {
      final String answer = qnameToJava.get(qname);
      if (answer != null) {
        return answer;
      }
    }
    return qname.getLocalPart();
  }

  /**
   * Returns the Java class name that should be used for the given QName. If no explicit mapping has been made then
   * the localPart of the QName is used which is the normal default in XStream.
   */
  @Override
  public QName getQName(final String javaClassName) {
    if (javaToQName != null) {
      final QName answer = javaToQName.get(javaClassName);
      if (answer != null) {
        return answer;
      }
    }
    return new QName(defaultNamespace, javaClassName, defaultPrefix);
  }

  /**
   * Registers the mapping of the Java class name to the QName
   */
  @Override
  public synchronized void registerMapping(final QName qname, final String javaClassName) {
    if (javaToQName == null) {
      javaToQName = new ConcurrentHashMap<>();
    }
    if (qnameToJava == null) {
      qnameToJava = new ConcurrentHashMap<>();
    }
    javaToQName.put(javaClassName, qname);
    qnameToJava.put(qname, javaClassName);
  }

  /**
   * Registers the mapping of the type to the QName
   */
  @Override
  public synchronized void registerMapping(final QName qname, final Class type) {
    registerMapping(qname, type.getName());
  }

  @Override
  public String getDefaultNamespace() {
    return defaultNamespace;
  }

  @Override
  public void setDefaultNamespace(final String defaultNamespace) {
    this.defaultNamespace = defaultNamespace;
  }

  @Override
  public String getDefaultPrefix() {
    return defaultPrefix;
  }

  @Override
  public void setDefaultPrefix(final String defaultPrefix) {
    this.defaultPrefix = defaultPrefix;
  }
}