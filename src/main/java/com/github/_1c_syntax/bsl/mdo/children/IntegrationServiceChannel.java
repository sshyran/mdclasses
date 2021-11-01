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
package com.github._1c_syntax.bsl.mdo.children;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(of = {"name", "uuid", "mdoReference"})
@EqualsAndHashCode(of = {"name", "uuid", "mdoReference"})
public class IntegrationServiceChannel implements MDObject, MDChildObject {
  /**
   * Имя
   */
  String name;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging objectBelonging;

  /**
   * Тип метаданных
   */
  MDOType mdoType;

  /**
   * Имя метаданных объекта
   */
  String metadataName;

  /**
   * Имя метаданных объекта на русском языке
   */
  String metadataNameRu;

  /**
   * Синонимы объекта
   */
  MultiLanguageString synonym;

  /**
   * MDO-Ссылка на объект
   */
  MdoReference mdoReference;

  /**
   * Направление сообщения
   */
  MessageDirection messageDirection;

  /**
   * Обработчик получения сообщения
   */
  String handler;

  /**
   * Родительский объект
   */
  MdoReference owner;

  /**
   * Вариант поддержки родительской конфигурации
   */
  SupportVariant supportVariant;

  /**
   * Имя канала внешнего сервиса интеграции
   */
  String externalIntegrationServiceChannelName;
}
