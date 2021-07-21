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
package com.github._1c_syntax.mdclasses.mdo.children.xdtopackage;

import com.github._1c_syntax.bsl.mdo.support.XdtoPackageData;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для хранения описания типов объектов XDTO пакета
 */
@Data
@NoArgsConstructor
public class XdtoObjectType {

  /**
   * Имя типа объекта
   */
  @XStreamAsAttribute
  private String name = "";

  /**
   * Ссылка на базовый тип
   */
  @XStreamAsAttribute
  private String base = "";

  /**
   * Список атрибутов объекта
   */
  @XStreamImplicit(itemFieldName = "property")
  private List<XdtoProperty> properties = Collections.emptyList();

  public Object buildMDObject() {
    return new XdtoPackageData.ObjectType(name, base,
      properties.stream()
        .map(XdtoProperty::buildMDObject)
        .map(XdtoPackageData.Property.class::cast)
        .collect(Collectors.toList()));
  }
}
