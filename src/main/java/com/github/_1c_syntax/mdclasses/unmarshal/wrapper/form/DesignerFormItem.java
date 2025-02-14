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
package com.github._1c_syntax.mdclasses.unmarshal.wrapper.form;

import com.github._1c_syntax.mdclasses.mdo.support.BWAValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"id", "name"})
@NoArgsConstructor
public class DesignerFormItem {
  @XStreamAsAttribute
  private String name;
  @XStreamAsAttribute
  private int id;
  private String type;
  @XStreamAlias("DataPath")
  private String dataPath;
  @XStreamAlias("ChildItems")
  private DesignerChildItems childItems;
  @XStreamAlias("ContextMenu")
  private DesignerFormItem contextMenu;
  @XStreamAlias("ExtendedTooltip")
  private DesignerFormItem extendedTooltip;
  @XStreamAlias("AutoCommandBar")
  private DesignerFormItem autoCommandBar;
  @XStreamAlias("SearchStringAddition")
  private DesignerFormItem searchStringAddition;
  @XStreamAlias("ViewStatusAddition")
  private DesignerFormItem viewStatusAddition;
  @XStreamAlias("Events")
  private DesignerEvents events;
  @XStreamAlias("PasswordMode")
  private BWAValue passwordMode = BWAValue.AUTO;
}
