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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDOForm;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDCommonForm;
import com.github._1c_syntax.mdclasses.mdo.children.Form;
import com.github._1c_syntax.mdclasses.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalDataProcessorTest {

  @Test
  void testEDT() {

    File srcPath = new File("src/test/resources/external/edt/dataProcessor" +
      "/src/ExternalDataProcessors/ТестоваяВнешняяОбработка/ТестоваяВнешняяОбработка.mdo");
    var externalDataProcessor = ExternalDataProcessor.create(srcPath.toPath());
    assertThat(externalDataProcessor).isNotInstanceOf(ExternalReport.class);
    assertThat(externalDataProcessor.getName()).isEqualTo("ТестоваяВнешняяОбработка");
    assertThat(externalDataProcessor.getUuid()).isEqualTo("4c1090aa-a76e-4693-87c0-6f4b7494467d");
    assertThat(externalDataProcessor.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);

    assertThat(externalDataProcessor.getModulesByType()).hasSize(2);
    assertThat(externalDataProcessor.getModulesByObject()).hasSize(2);
    assertThat(externalDataProcessor.getModules()).hasSize(2);

    assertThat(externalDataProcessor.getChildren()).hasSize(7);
    checkChildCount(externalDataProcessor, MDOType.EXTERNAL_DATA_PROCESSOR, 1);
    checkChildCount(externalDataProcessor, MDOType.COMMAND, 0);
    checkChildCount(externalDataProcessor, MDOType.FORM, 2);
    checkChildCount(externalDataProcessor, MDOType.TEMPLATE, 1);
    checkChildCount(externalDataProcessor, MDOType.ATTRIBUTE, 3);

    assertThat(externalDataProcessor.getChildrenByMdoRef()).hasSize(7);

    checkFillPath(externalDataProcessor.getChildren());
    checkFormData(externalDataProcessor.getChildren());

    var modulesByType = externalDataProcessor.getModulesByMDORef(
      "ExternalDataProcessor.ТестоваяВнешняяОбработка");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.ObjectModule);

    modulesByType = externalDataProcessor.getModulesByMDORef(
      "ExternalDataProcessor.ТестоваяВнешняяОбработка.Form.ОбычнаяФорма");
    assertThat(modulesByType).isEmpty();

  }

  @Test
  void testDesigner() {

    File srcPath = new File(
      "src/test/resources/external/original/ВнешняяОбработкаФайлы/ТестоваяВнешняяОбработка.xml");
    var externalDataProcessor = ExternalDataProcessor.create(srcPath.toPath());
    assertThat(externalDataProcessor).isNotInstanceOf(ExternalReport.class);
    assertThat(externalDataProcessor.getName()).isEqualTo("ТестоваяВнешняяОбработка");
    assertThat(externalDataProcessor.getUuid()).isEqualTo("4c1090aa-a76e-4693-87c0-6f4b7494467d");
    assertThat(externalDataProcessor.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);

    assertThat(externalDataProcessor.getModulesByType()).hasSize(2);
    assertThat(externalDataProcessor.getModulesByObject()).hasSize(2);
    assertThat(externalDataProcessor.getModules()).hasSize(2);

    assertThat(externalDataProcessor.getChildren()).hasSize(7);
    checkChildCount(externalDataProcessor, MDOType.EXTERNAL_DATA_PROCESSOR, 1);
    checkChildCount(externalDataProcessor, MDOType.COMMAND, 0);
    checkChildCount(externalDataProcessor, MDOType.FORM, 2);
    checkChildCount(externalDataProcessor, MDOType.TEMPLATE, 1);
    checkChildCount(externalDataProcessor, MDOType.ATTRIBUTE, 3);

    assertThat(externalDataProcessor.getChildrenByMdoRef()).hasSize(7);

    checkFillPath(externalDataProcessor.getChildren());
    checkFormData(externalDataProcessor.getChildren());

    var modulesByType = externalDataProcessor.getModulesByMDORef(
      "ExternalDataProcessor.ТестоваяВнешняяОбработка");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.ObjectModule);

    modulesByType = externalDataProcessor.getModulesByMDORef(
      "ExternalDataProcessor.ТестоваяВнешняяОбработка.Form.ОбычнаяФорма");
    assertThat(modulesByType).isEmpty();
  }

  @Test
  void testEmpty() {

    var externalDataProcessor = ExternalDataProcessor.create(null);

    assertThat(externalDataProcessor).isNotNull();
    assertThat(externalDataProcessor.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(externalDataProcessor.getRootPath()).isNotPresent();

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
    assertThat(externalDataProcessor.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);

    var externalDataProcessor2 = ExternalDataProcessor.create();

    assertThat(externalDataProcessor2).isNotNull();
    assertThat(externalDataProcessor2.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(externalDataProcessor2.getChildren()).isEmpty();

  }

  @Test
  void testErrors() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    var externalDataProcessor = ExternalDataProcessor.create(srcPath);
    assertThat(externalDataProcessor).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(externalDataProcessor.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);
  }

  private void checkFillPath(Set<AbstractMDObjectBase> child) {
    var elements = child.parallelStream()
      .filter(mdObjectBase -> mdObjectBase instanceof Form || mdObjectBase instanceof MDCommonForm)
      .filter(mdObjectBase -> mdObjectBase.getPath() == null)
      .filter(mdObjectBase -> !mdObjectBase.getPath().toFile().exists())
      .collect(Collectors.toList());
    assertThat(elements).isEmpty();
  }

  private void checkFormData(Set<AbstractMDObjectBase> child) {
    var elements = child.parallelStream()
      .filter(mdObjectBase -> mdObjectBase instanceof Form || mdObjectBase instanceof MDCommonForm)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getFormType() == FormType.MANAGED)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getData() == null)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getData() == null)
      .collect(Collectors.toList());
    assertThat(elements).isEmpty();
  }

  private void checkChildCount(ExternalDataProcessor externalDataProcessor, MDOType type, int count) {
    assertThat(externalDataProcessor.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getType() == type).hasSize(count);
  }
}
