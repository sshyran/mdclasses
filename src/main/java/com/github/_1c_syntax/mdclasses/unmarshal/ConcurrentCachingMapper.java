/*
 * Copied from https://github.com/x-stream/xstream/blob/master/xstream/src/java/com/thoughtworks/xstream/mapper/CachingMapper.java
 * on terms described below.
 * Changed Collections.synchronizedMap to ConcurrentHashMap and some code clean-up.
 */

/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009, 2011, 2013, 2014 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 22. January 2005 by Joe Walnes
 */
package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.mapper.CachingMapper;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.security.ForbiddenClassException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapper that caches which names map to which classes. Prevents repetitive searching and class loading.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class ConcurrentCachingMapper extends CachingMapper {

  private final transient Map<String, Object> realClassCache = new ConcurrentHashMap<>(128);

  public ConcurrentCachingMapper(Mapper wrapped) {
    super(wrapped);
  }

  @Override
  public Class<?> realClass(String elementName) {
    Object cached = realClassCache.get(elementName);
    if (cached != null) {
      if (cached instanceof Class) {
        return (Class<?>) cached;
      }
      throw (XStreamException) cached;
    }

    try {
      Class<?> result = super.realClass(elementName);
      realClassCache.put(elementName, result);
      return result;
    } catch (ForbiddenClassException | CannotResolveClassException e) {
      realClassCache.put(elementName, e);
      throw e;
    }
  }

  @Override
  public void flushCache() {
    realClassCache.clear();
  }

}
