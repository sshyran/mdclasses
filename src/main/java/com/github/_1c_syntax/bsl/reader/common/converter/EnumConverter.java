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

import com.github._1c_syntax.bsl.mdo.support.EnumWithValue;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для каждого конкретного перечисления надо создать собственный класс, унаследованный от EnumWithValues.
 * Необходимо в конструкторе передать класс перечисления и зарегистрировать созданный класс конвертора в
 * *XStreamFactory.
 */
public class EnumConverter<T extends Enum<T> & EnumWithValue> extends AbstractSingleValueConverter {

  private final Class<T> enumClazz;

  public EnumConverter(Class<T> enumClazz) {
    this.enumClazz = enumClazz;
  }

  @Override
  public Object fromString(String sourceString) {
    return fromValue(enumClazz, sourceString);
  }

  private static <T extends Enum<T> & EnumWithValue> T fromValue(Class<T> clazz, String value) {
    for (T item : clazz.getEnumConstants()) {
      if (item.value().equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(value);
  }

  @Override
  public boolean canConvert(Class type) {
    return enumClazz.isAssignableFrom(type);
  }
}
