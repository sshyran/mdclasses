/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.form.attribute.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.metadata.additional.SourcePosition;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppReader;
import lombok.SneakyThrows;
import org.xmlpull.mxp1.MXParser;

import java.util.HashMap;
import java.util.Map;

public class DynamicListExtInfoConverter implements Converter {
  private static final String TAG_QUERY_TEXT = "queryText";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Map<String, String> fields = new HashMap<>();
    var position = new SourcePosition(0);
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      fields.put(reader.getNodeName(), reader.getValue());
      if (reader.getNodeName().equals(TAG_QUERY_TEXT)) {
        var lines = reader.getValue().split("\n").length;
        position = getSourcePosition(reader);
        position.setLine(position.getLine() - lines + 1);
      }
      reader.moveUp();
    }

    var info = new DynamicListExtInfo();
    info.setQueryText(fields.getOrDefault("queryText", ""));
    info.setPosition(position);
    info.setMainTable(fields.getOrDefault("mainTable", ""));
    info.setAutoFillAvailableFields(getBooleanOrTrue(fields, "autoFillAvailableFields"));
    info.setCustomQuery(getBooleanOrTrue(fields, "customQuery", false));
    info.setAutoSaveUserSettings(getBooleanOrTrue(fields, "autoSaveUserSettings"));
    info.setGetInvisibleFieldPresentations(getBooleanOrTrue(fields, "getInvisibleFieldPresentations"));

    return info;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DynamicListExtInfo.class;
  }

  private boolean getBooleanOrTrue(Map<String, String> map, String name) {
    return getBooleanOrTrue(map, name, true);
  }

  private boolean getBooleanOrTrue(Map<String, String> map, String name, boolean defaultValue) {
    var value = map.getOrDefault(name, "");
    if (value.isEmpty()) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value);
  }

  @SneakyThrows
  private SourcePosition getSourcePosition(HierarchicalStreamReader reader) {
    var xppReader = (XppReader) reader;
    var filed = xppReader.getClass().getDeclaredField("parser");
    filed.setAccessible(true);
    var parser = (MXParser) filed.get(reader);
    return new SourcePosition(parser.getLineNumber());
  }

}
