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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый класс всех типов и дочерних объектов 1С
 */
@Data
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NoArgsConstructor
public abstract class AbstractMDObjectBase implements MDObject {

  /**
   * уникальный идентификатор объекта
   */
  @XStreamAsAttribute
  protected String uuid = "";

  /**
   * Имя объекта
   */
  protected String name = "";

  /**
   * Синонимы объекта
   */
  @XStreamImplicit(itemFieldName = "synonym")
  protected List<LanguageContent> synonyms = Collections.emptyList();

  /**
   * Строка с комментарием объекта
   */
  protected String comment = "";

  /**
   * MDO-Ссылка на объект
   */
  protected MdoReference mdoReference;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  protected ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Путь к файлу объекта
   */
  protected Path path;

  /**
   * Список подсистем, в состав которых входит объект
   */
  protected List<MDSubsystem> includedSubsystems = Collections.emptyList();

  /**
   * Используется для заполнения объекта на основании информации формата конфигуратора
   *
   * @param designerMDO - Служебный объект, содержащий данные в формате конфигуратора.
   */
  protected AbstractMDObjectBase(DesignerMDO designerMDO) {
    uuid = designerMDO.getUuid();
    name = designerMDO.getProperties().getName();
    comment = designerMDO.getProperties().getComment();
    objectBelonging = designerMDO.getProperties().getObjectBelonging();

    synonyms = designerMDO.getProperties().getSynonyms().stream()
      .map(synonym -> new LanguageContent(synonym.getLanguage(), synonym.getContent()))
      .collect(Collectors.toList());
  }

  /**
   * Метод должен вызываться в конце чтения объекта для его дозаполнения.
   * При необходимости, можно переопределить в дочерних объектах для персональной дообработки
   */
  public void supplement() {
    if (getMdoReference() == null) {
      setMdoReference(MdoReference.create(getMdoType(), getName()));
    }
  }

  /**
   * Метод должен вызываться в конце чтения объекта для его дозаполнения.
   * При необходимости, можно переопределить в дочерних объектах для персональной дообработки
   */
  public void supplement(AbstractMDObjectBase parent) {
    if (getMdoReference() == null) {
      setMdoReference(MdoReference.create(parent.getMdoReference(), getMdoType(), getName()));
    }
  }

  /**
   * Для добавления ссылки на подсистему, в которую включен объект
   *
   * @param subsystem - Подсистема, в которую включен объект
   */
  public void addIncludedSubsystem(MDSubsystem subsystem) {
    if (includedSubsystems.equals(Collections.emptyList())) {
      includedSubsystems = new ArrayList<>();
    }
    includedSubsystems.add(subsystem);
  }

  /**
   * @deprecated Оставлен для совместимости со старой версией для пользователей библиотеки.
   */
  @Deprecated(since = "0.11.0", forRemoval = true)
  public MDOType getType() {
    return getMdoType();
  }
}
