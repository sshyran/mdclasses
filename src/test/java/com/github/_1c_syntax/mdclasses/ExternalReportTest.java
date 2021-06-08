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

class ExternalReportTest {
  @Test
  void testEDT() {

    File srcPath = new File("src/test/resources/external/edt/reportAndDataProcessor" +
      "/src/ExternalReports/ТестовыйВнешнийОтчет/ТестовыйВнешнийОтчет.mdo");
    var externalReport = ExternalReport.create(srcPath.toPath());
    assertThat(externalReport).isNotInstanceOf(ExternalDataProcessor.class);
    assertThat(externalReport.getName()).isEqualTo("ТестовыйВнешнийОтчет");
    assertThat(externalReport.getUuid()).isEqualTo("0e130a7c-7f6b-41c4-94bc-ad4473b89915");
    assertThat(externalReport.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);

    assertThat(externalReport.getModulesByType()).hasSize(2);
    assertThat(externalReport.getModulesByObject()).hasSize(2);
    assertThat(externalReport.getModules()).hasSize(2);

    assertThat(externalReport.getChildren()).hasSize(10);
    checkChildCount(externalReport, MDOType.EXTERNAL_REPORT, 1);
    checkChildCount(externalReport, MDOType.COMMAND, 0);
    checkChildCount(externalReport, MDOType.FORM, 5);
    checkChildCount(externalReport, MDOType.TEMPLATE, 1);
    checkChildCount(externalReport, MDOType.ATTRIBUTE, 3);

    assertThat(externalReport.getChildrenByMdoRef()).hasSize(10);

    checkFillPath(externalReport.getChildren());
    checkFormData(externalReport.getChildren());

    var modulesByType = externalReport.getModulesByMDORef(
      "ExternalReport.ТестовыйВнешнийОтчет");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.ObjectModule);

    modulesByType = externalReport.getModulesByMDORef(
      "ExternalReport.ТестовыйВнешнийОтчет.Form.ФормаВарианта");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.FormModule);

  }

  @Test
  void testDesigner() {

    File srcPath = new File(
      "src/test/resources/external/original/ВнешнийОтчетФайлы/ТестовыйВнешнийОтчет.xml");
    var externalReport = ExternalReport.create(srcPath.toPath());
    assertThat(externalReport).isNotInstanceOf(ExternalDataProcessor.class);
    assertThat(externalReport.getName()).isEqualTo("ТестовыйВнешнийОтчет");
    assertThat(externalReport.getUuid()).isEqualTo("0e130a7c-7f6b-41c4-94bc-ad4473b89915");
    assertThat(externalReport.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);

    assertThat(externalReport.getModulesByType()).hasSize(2);
    assertThat(externalReport.getModulesByObject()).hasSize(2);
    assertThat(externalReport.getModules()).hasSize(2);

    assertThat(externalReport.getChildren()).hasSize(10);
    checkChildCount(externalReport, MDOType.EXTERNAL_REPORT, 1);
    checkChildCount(externalReport, MDOType.COMMAND, 0);
    checkChildCount(externalReport, MDOType.FORM, 5);
    checkChildCount(externalReport, MDOType.TEMPLATE, 1);
    checkChildCount(externalReport, MDOType.ATTRIBUTE, 3);

    assertThat(externalReport.getChildrenByMdoRef()).hasSize(10);

    checkFillPath(externalReport.getChildren());
    checkFormData(externalReport.getChildren());

    var modulesByType = externalReport.getModulesByMDORef(
      "ExternalReport.ТестовыйВнешнийОтчет");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.ObjectModule);

    modulesByType = externalReport.getModulesByMDORef(
      "ExternalReport.ТестовыйВнешнийОтчет.Form.ФормаВарианта");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.FormModule);
  }

  @Test
  void testEmpty() {

    var externalReport = ExternalReport.create(null);

    assertThat(externalReport).isNotNull();
    assertThat(externalReport.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(externalReport.getRootPath()).isNotPresent();

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
    assertThat(externalReport.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);

    var externalReport1 = ExternalReport.create();

    assertThat(externalReport1).isNotNull();
    assertThat(externalReport1.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(externalReport1.getChildren()).isEmpty();

  }

  @Test
  void testErrors() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    var externalReport = ExternalReport.create(srcPath);
    assertThat(externalReport).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(externalReport.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);
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

  private void checkChildCount(ExternalReport externalReport, MDOType type, int count) {
    assertThat(externalReport.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getType() == type).hasSize(count);
  }


}