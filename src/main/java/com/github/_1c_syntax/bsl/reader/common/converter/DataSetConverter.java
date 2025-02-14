/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.QuerySource;
import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DataSetConverter implements Converter {

  private static final String NAME_NODE_NAME = "name";
  private static final String FIELD_NODE_NAME = "field";
  private static final String TYPE_ATTRIBUTE_NAME = "type";
  private static final String DATA_SOURCE_NODE_NAME = "dataSource";
  private static final String QUERY_SOURCE_NODE_NAME = "query";
  private static final String ITEM_SOURCE_NODE_NAME = "item";
  private static final String DATA_PATH_NODE_NAME = "dataPath";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var dataSet = DataCompositionSchema.DataSet.builder();
    dataSet.type(DataSetType.fromValue(reader.getAttribute(TYPE_ATTRIBUTE_NAME)));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (NAME_NODE_NAME.equals(nodeName)) {
        dataSet.name(reader.getValue());
      } else if (DATA_SOURCE_NODE_NAME.equals(nodeName)) {
        dataSet.dataSource(reader.getValue());
      } else if (ITEM_SOURCE_NODE_NAME.equals(nodeName)) {
        dataSet.item((DataCompositionSchema.DataSet)
          context.convertAnother(reader, DataCompositionSchema.DataSet.class));
      } else if (FIELD_NODE_NAME.equals(nodeName)) {
        dataSet.field(readField(reader));
      } else if (QUERY_SOURCE_NODE_NAME.equals(nodeName)) {
        var querySource = (QuerySource) context.convertAnother(reader, QuerySource.class);
        dataSet.querySource(querySource);
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return dataSet.build();
  }

  private static DataCompositionSchema.DataSetField readField(HierarchicalStreamReader reader) {
    var dataPath = "";
    var field = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var fieldNodeName = reader.getNodeName();
      if (FIELD_NODE_NAME.equals(fieldNodeName)) {
        field = reader.getValue();
      } else if (DATA_PATH_NODE_NAME.equals(fieldNodeName)) {
        dataPath = reader.getValue();
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return new DataCompositionSchema.DataSetField(dataPath, field);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DataCompositionSchema.DataSet.class;
  }
}
