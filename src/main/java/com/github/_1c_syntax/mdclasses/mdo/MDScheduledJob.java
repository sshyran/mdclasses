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

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.bsl.mdo.support.Handler;
import com.github._1c_syntax.bsl.reader.common.converter.MethodHandlerConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.SCHEDULED_JOB,
  name = "ScheduledJob",
  nameRu = "РегламентноеЗадание",
  groupName = "ScheduledJobs",
  groupNameRu = "РегламентныеЗадания"
)
public class MDScheduledJob extends AbstractMDObjectBase {

  /**
   * Полное имя метода, включающее имя общего модуля
   */
  @XStreamConverter(MethodHandlerConverter.class)
  @XStreamAlias("methodName")
  private Handler handler;

  private String description = "";
  private String key = "";
  private boolean use;
  private boolean predefined;
  private int restartCountOnFailure;
  private int restartIntervalOnFailure;

  public MDScheduledJob(DesignerMDO designerMDO) {
    super(designerMDO);
    this.handler = new Handler(designerMDO.getProperties().getMethodName());
    this.description = designerMDO.getProperties().getDescription();
    this.key = designerMDO.getProperties().getKey();
    this.use = designerMDO.getProperties().isUse();
    this.predefined = designerMDO.getProperties().isPredefined();
    this.restartCountOnFailure = designerMDO.getProperties().getRestartCountOnFailure();
    this.restartIntervalOnFailure = designerMDO.getProperties().getRestartIntervalOnFailure();
  }
}
