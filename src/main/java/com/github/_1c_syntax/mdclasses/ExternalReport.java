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
import com.github._1c_syntax.mdclasses.mdo.MDExternalReport;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;

/**
 * Класс внешнего отчета
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalReport extends ExternalMDClass {
  protected ExternalReport() {
    super();
  }

  protected ExternalReport(MDExternalReport mdo, ConfigurationSource source, Path path) {
    super(mdo, source, path);
  }

  /**
   * Метод для создания пустого внешнего отчета
   */
  public static ExternalReport create() {
    return new ExternalReport();
  }

  /**
   * Метод создания внешнего отчета по каталогу исходных файлов
   *
   * @param mdoPath - Адрес корневого файла отчета
   */
  public static ExternalReport create(Path mdoPath) {
    var configurationSource = MDOUtils.getConfigurationSourceByMDOPath(mdoPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var rootPath = MDOPathUtils.getRootPathByConfigurationMDO(mdoPath);
      if (rootPath.isPresent()) {
        var externalDataProcessorMDO = MDOFactory.readMDClass(configurationSource,
          mdoPath, MDOType.EXTERNAL_REPORT);
        if (externalDataProcessorMDO.isPresent()) {
          var mdoExternalReport = (MDExternalReport) externalDataProcessorMDO.get();
          return new ExternalReport(mdoExternalReport, configurationSource, rootPath.get());
        }
      }
    }

    return create();
  }
}
