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
package com.github._1c_syntax.mdclasses.mdo.attributes;

import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.metadata.MetadataStorage;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

/**
 * Класс для всех атрибутов (включая табличные части) объектов
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractMDOAttribute extends AbstractMDObjectBase {

  /**
   * Вид атрибута
   */
  private AttributeKind kind = AttributeKind.CUSTOM;

  /**
   * Вариант индексирования реквизита
   */
  private IndexingType indexing = IndexingType.DONT_INDEX;

  /**
   * Режим пароля. Только для аттрибутов с типом `Строка`
   */
  private boolean passwordMode;

  protected AbstractMDOAttribute(DesignerMDO designerMDO) {
    super(designerMDO);
    indexing = designerMDO.getProperties().getIndexing();
    passwordMode = designerMDO.getProperties().isPasswordMode();
  }

  public Object buildMDObject(MdoReference owner, Path ownerPath) {
    TransformationUtils.setValue(builder, "kind", kind);
    TransformationUtils.setValue(builder, "indexing", indexing);
    TransformationUtils.setValue(builder, "passwordMode", passwordMode);
    TransformationUtils.setValue(builder, "owner", owner);

    if (!(this instanceof Recalculation)) {
      TransformationUtils.setValue(builder, "type", getType());
    }
    return super.buildMDObject(ownerPath);
  }

  @Override
  public MDOType getType() {
    return MDOType.ATTRIBUTE;
  }

  @Override
  public String getMetadataName() {
    return MetadataStorage.getAttribute(getClass()).name();
  }

  @Override
  public String getMetadataNameRu() {
    return getMetadataName();
  }

  public AttributeType getAttributeType() {
    return MetadataStorage.getAttribute(getClass()).type();
  }
}
