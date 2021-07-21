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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.test_utils.AbstractMDClassTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest extends AbstractMDClassTest<Configuration> {
  ConfigurationTest() {
    super(Configuration.class);
  }

  @ParameterizedTest(name = "{index}: {1} (path {0})")
  @CsvSource(
    {
      "src/test/resources/metadata/original,Конфигурация,46c7c1d0-b04d-4295-9b04-ae3207c18d29, DESIGNER, " +
        "Version_8_3_10, Russian, ManagedApplication, Русский, Automatic, DONT_USE, USE, DONT_USE, true, " +
        "false, 2",
      "src/test/resources/metadata/original_ordinary,Конфигурация,46c7c1d0-b04d-4295-9b04-ae3207c18d29, DESIGNER, " +
        "Version_8_3_10, Russian, OrdinaryApplication, Русский, AutomaticAndManaged, DONT_USE, USE, DONT_USE, true, " +
        "false, 0",
      "src/test/resources/metadata/edt,Конфигурация,46c7c1d0-b04d-4295-9b04-ae3207c18d29, EDT, " +
        "Version_8_3_10, Russian, ManagedApplication, Русский, Automatic, USE, USE_WITH_WARNINGS, DONT_USE, true, " +
        "true, 2",
      "src/test/resources/metadata/edt_en,Configuration,04c0322d-92da-49ab-87e5-82c8dcd50888, EDT, " +
        "Version_8_3_14, English, ManagedApplication, English, AutomaticAndManaged, DONT_USE, USE, DONT_USE, false, " +
        "false, 0",
      "src/test/resources/metadata/original_3_18,Конфигурация,ade513fa-b8dc-4656-b1b6-68fde4fe18de, DESIGNER, " +
        "Version_8_3_18, Russian, ManagedApplication, Русский, AutomaticAndManaged, DONT_USE, USE, DONT_USE, true, " +
        "true, 0"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdc = getMDClass(argumentsAccessor.getString(0));
    mdcTest(mdc, argumentsAccessor);
    assertThat(mdc.getConfigurationSource().name()).isEqualTo(argumentsAccessor.getString(3));
    assertThat(CompatibilityMode.compareTo(mdc.getCompatibilityMode(), argumentsAccessor.getString(4)))
      .isZero();
    assertThat(CompatibilityMode.compareTo(
      mdc.getConfigurationExtensionCompatibilityMode(), argumentsAccessor.getString(4)))
      .isZero();
    assertThat(mdc.getScriptVariant().value()).isEqualTo(argumentsAccessor.getString(5));
    assertThat(mdc.getDefaultRunMode()).isEqualTo(
      ApplicationRunMode.getByName(argumentsAccessor.getString(6)));
    assertThat(mdc.getDefaultLanguage().getName()).isEqualTo(argumentsAccessor.getString(7));
    assertThat(mdc.getDataLockControlMode().value()).isEqualTo(argumentsAccessor.getString(8));
    assertThat(mdc.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(mdc.getModalityUseMode()).isEqualTo(UseMode.valueOf(argumentsAccessor.getString(9)));
    assertThat(mdc.getSynchronousExtensionAndAddInCallUseMode())
      .isEqualTo(UseMode.valueOf(argumentsAccessor.getString(10)));
    assertThat(mdc.getSynchronousPlatformExtensionAndAddInCallUseMode())
      .isEqualTo(UseMode.valueOf(argumentsAccessor.getString(11)));
    assertThat(mdc.isUseManagedFormInOrdinaryApplication())
      .isEqualTo(argumentsAccessor.getBoolean(12));
    assertThat(mdc.isUseOrdinaryFormInManagedApplication())
      .isEqualTo(argumentsAccessor.getBoolean(13));

    assertThat(mdc.getCopyrights().getContent()).hasSize(argumentsAccessor.getInteger(14));
    assertThat(mdc.getDetailedInformation().getContent()).hasSize(argumentsAccessor.getInteger(14));
    assertThat(mdc.getBriefInformation().getContent()).hasSize(argumentsAccessor.getInteger(14));
  }

  @ParameterizedTest(name = "{index}: path {0}")
  @CsvSource(
    {
      "src/test/resources/metadata/original, 61," +
        "1,6,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,10,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1",
      "src/test/resources/metadata/original_ordinary, 1," +
        "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0",
      "src/test/resources/metadata/edt, 61," +
        "2,6,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,10,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0",
      "src/test/resources/metadata/edt_en, 3," +
        "0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0",
      "src/test/resources/metadata/original_3_18, 24," +
        "0,1,0,1,0,1,0,0,1,1,0,0,0,0,0,0,0,10,0,0,0,0,0,1,0,0,1,0,2,3,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0"
    }
  )
  void testChildren(ArgumentsAccessor argumentsAccessor) {
    var mdc = getMDClass(argumentsAccessor.getString(0));

    assertThat(mdc.getChildren()).hasSize(argumentsAccessor.getInteger(1));

    List<MDObject> children = new ArrayList<>();

    assertThat(mdc.getSubsystems()).hasSize(argumentsAccessor.getInteger(2));
    children.addAll(mdc.getSubsystems());

    assertThat(mdc.getCommonModules()).hasSize(argumentsAccessor.getInteger(3));
    children.addAll(mdc.getCommonModules());

    assertThat(mdc.getSessionParameters()).hasSize(argumentsAccessor.getInteger(4));
    children.addAll(mdc.getSessionParameters());

    assertThat(mdc.getRoles()).hasSize(argumentsAccessor.getInteger(5));
    children.addAll(mdc.getRoles());

    assertThat(mdc.getCommonAttributes()).hasSize(argumentsAccessor.getInteger(6));
    children.addAll(mdc.getCommonAttributes());

    assertThat(mdc.getExchangePlans()).hasSize(argumentsAccessor.getInteger(7));
    children.addAll(mdc.getExchangePlans());

    assertThat(mdc.getFilterCriteria()).hasSize(argumentsAccessor.getInteger(8));
    children.addAll(mdc.getFilterCriteria());

    assertThat(mdc.getEventSubscriptions()).hasSize(argumentsAccessor.getInteger(9));
    children.addAll(mdc.getEventSubscriptions());

    assertThat(mdc.getScheduledJobs()).hasSize(argumentsAccessor.getInteger(10));
    children.addAll(mdc.getScheduledJobs());

    assertThat(mdc.getBots()).hasSize(argumentsAccessor.getInteger(11));
    children.addAll(mdc.getBots());

    assertThat(mdc.getFunctionalOptions()).hasSize(argumentsAccessor.getInteger(12));
    children.addAll(mdc.getFunctionalOptions());

    assertThat(mdc.getFunctionalOptionsParameters()).hasSize(argumentsAccessor.getInteger(13));
    children.addAll(mdc.getFunctionalOptionsParameters());

    assertThat(mdc.getDefinedTypes()).hasSize(argumentsAccessor.getInteger(14));
    children.addAll(mdc.getDefinedTypes());

    assertThat(mdc.getSettingsStorages()).hasSize(argumentsAccessor.getInteger(15));
    children.addAll(mdc.getSettingsStorages());

    assertThat(mdc.getCommonForms()).hasSize(argumentsAccessor.getInteger(16));
    children.addAll(mdc.getCommonForms());

    assertThat(mdc.getCommonCommands()).hasSize(argumentsAccessor.getInteger(17));
    children.addAll(mdc.getCommonCommands());

    assertThat(mdc.getCommandGroups()).hasSize(argumentsAccessor.getInteger(18));
    children.addAll(mdc.getCommandGroups());

    assertThat(mdc.getCommonTemplates()).hasSize(argumentsAccessor.getInteger(19));
    children.addAll(mdc.getCommonTemplates());

    assertThat(mdc.getCommonPictures()).hasSize(argumentsAccessor.getInteger(20));
    children.addAll(mdc.getCommonPictures());

    assertThat(mdc.getXdtoPackages()).hasSize(argumentsAccessor.getInteger(21));
    children.addAll(mdc.getXdtoPackages());

    assertThat(mdc.getWebServices()).hasSize(argumentsAccessor.getInteger(22));
    children.addAll(mdc.getWebServices());

    assertThat(mdc.getHttpServices()).hasSize(argumentsAccessor.getInteger(23));
    children.addAll(mdc.getHttpServices());

    assertThat(mdc.getWsReferences()).hasSize(argumentsAccessor.getInteger(24));
    children.addAll(mdc.getWsReferences());

    assertThat(mdc.getIntegrationServices()).hasSize(argumentsAccessor.getInteger(25));
    children.addAll(mdc.getIntegrationServices());

    assertThat(mdc.getStyleItems()).hasSize(argumentsAccessor.getInteger(26));
    children.addAll(mdc.getStyleItems());

    assertThat(mdc.getStyles()).hasSize(argumentsAccessor.getInteger(27));
    children.addAll(mdc.getStyles());

    assertThat(mdc.getLanguages()).hasSize(argumentsAccessor.getInteger(28));
    children.addAll(mdc.getLanguages());

    assertThat(mdc.getConstants()).hasSize(argumentsAccessor.getInteger(29));
    children.addAll(mdc.getConstants());

    assertThat(mdc.getCatalogs()).hasSize(argumentsAccessor.getInteger(30));
    children.addAll(mdc.getCatalogs());

    assertThat(mdc.getDocuments()).hasSize(argumentsAccessor.getInteger(31));
    children.addAll(mdc.getDocuments());

    assertThat(mdc.getDocumentNumerators()).hasSize(argumentsAccessor.getInteger(32));
    children.addAll(mdc.getDocumentNumerators());

    assertThat(mdc.getSequences()).hasSize(argumentsAccessor.getInteger(33));
    children.addAll(mdc.getSequences());

    assertThat(mdc.getDocumentJournals()).hasSize(argumentsAccessor.getInteger(34));
    children.addAll(mdc.getDocumentJournals());

    assertThat(mdc.getEnums()).hasSize(argumentsAccessor.getInteger(35));
    children.addAll(mdc.getEnums());

    assertThat(mdc.getReports()).hasSize(argumentsAccessor.getInteger(36));
    children.addAll(mdc.getReports());

    assertThat(mdc.getDataProcessors()).hasSize(argumentsAccessor.getInteger(37));
    children.addAll(mdc.getDataProcessors());

    assertThat(mdc.getChartOfCharacteristicTypes()).hasSize(argumentsAccessor.getInteger(38));
    children.addAll(mdc.getChartOfCharacteristicTypes());

    assertThat(mdc.getChartOfAccounts()).hasSize(argumentsAccessor.getInteger(39));
    children.addAll(mdc.getChartOfAccounts());

    assertThat(mdc.getChartOfCalculationTypes()).hasSize(argumentsAccessor.getInteger(40));
    children.addAll(mdc.getChartOfCalculationTypes());

    assertThat(mdc.getInformationRegisters()).hasSize(argumentsAccessor.getInteger(41));
    children.addAll(mdc.getInformationRegisters());

    assertThat(mdc.getAccumulationRegisters()).hasSize(argumentsAccessor.getInteger(42));
    children.addAll(mdc.getAccumulationRegisters());

    assertThat(mdc.getAccountingRegisters()).hasSize(argumentsAccessor.getInteger(43));
    children.addAll(mdc.getAccountingRegisters());

    assertThat(mdc.getCalculationRegisters()).hasSize(argumentsAccessor.getInteger(44));
    children.addAll(mdc.getCalculationRegisters());

    assertThat(mdc.getBusinessProcesses()).hasSize(argumentsAccessor.getInteger(45));
    children.addAll(mdc.getBusinessProcesses());

    assertThat(mdc.getTasks()).hasSize(argumentsAccessor.getInteger(46));
    children.addAll(mdc.getTasks());

    assertThat(mdc.getInterfaces()).hasSize(argumentsAccessor.getInteger(47));
    children.addAll(mdc.getInterfaces());

    mdc.getChildren().forEach(mdObject -> assertThat(children).contains(mdObject));
    children.forEach(mdObject -> assertThat(mdc.getChildren()).contains(mdObject));

//
//    //    assertThat(mdc.getModulesByType()).hasSize(19);
//
////    assertThat(mdc.getModulesBySupport()).isEmpty();
////    assertThat(mdc.getModulesByObject()).hasSize(19);
////    assertThat(mdc.getModules()).hasSize(19);
////    assertThat(mdc.getCommonModules()).hasSize(6);
////    assertThat(mdc.getLanguages()).hasSize(3);
////    assertThat(mdc.getRoles()).hasSize(1);
//
//    assertThat(mdc.getChildren()).hasSize(61);
//    assertThat(mdc.getPlainChildren()).hasSize(61);
////    checkChildCount(mdc, MDOType.COMMAND, 1);
////    checkChildCount(mdc, MDOType.FORM, 8);
////    checkChildCount(mdc, MDOType.TEMPLATE, 2);
////    checkChildCount(mdc, MDOType.ATTRIBUTE, 34);
////    checkChildCount(mdc, MDOType.WS_OPERATION, 2);
////    checkChildCount(mdc, MDOType.HTTP_SERVICE_URL_TEMPLATE, 1);
////    checkChildCount(mdc, MDOType.HTTP_SERVICE_METHOD, 2);
//
//
////    assertThat(mdc.getChildrenByMdoRef()).hasSize(111);
//
////    assertThat(mdc.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
////    assertThat(mdc.getCommonModule("ГлобальныйОбщийМодуль3")).isNotPresent();
//
////    checkFillPath(mdc.getChildren());
////    checkFormData(mdc.getChildren());
//
////    var modulesByType = mdc.getModulesByMDORef("CommonModule.ГлобальныйОбщийМодуль");
////    assertThat(modulesByType).hasSize(1)
////      .containsKey(ModuleType.CommonModule);
////
////    modulesByType = mdc.getModulesByMDORef("WSReference.WSСсылка");
////    assertThat(modulesByType).isEmpty();
////
////    modulesByType = mdc.getModulesByMDORef(mdc.getCommonModule("ГлобальныйОбщийМодуль")
////      .get().getMdoReference());
////    assertThat(modulesByType).hasSize(1)
////      .containsKey(ModuleType.CommonModule);
////
////    checkOrderedCommonModules(mdc);
  }

}