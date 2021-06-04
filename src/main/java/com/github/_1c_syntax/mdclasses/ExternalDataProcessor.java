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
import com.github._1c_syntax.mdclasses.mdo.MDExternalDataProcessor;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;

/**
 * Класс внешней обработки
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalDataProcessor extends ExternalMDClass {

  protected ExternalDataProcessor() {
    super();
  }

  protected ExternalDataProcessor(MDExternalDataProcessor mdo, ConfigurationSource source, Path path) {
    super(mdo, source, path);
  }

  /**
   * Метод для создания пустой внешней обработки
   */
  public static ExternalDataProcessor create() {
    return new ExternalDataProcessor();
  }

  /**
   * Метод создания внешней обработки по каталогу исходных файлов
   *
   * @param rootPath - Адрес корневого каталога конфигурации
   */
  public static ExternalDataProcessor create(Path rootPath) {
    var configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var externalDataProcessorMDO = MDOFactory.readMDClass(configurationSource,
        rootPath, MDOType.EXTERNAL_DATA_PROCESSOR);
      if (externalDataProcessorMDO.isPresent()) {
        var mdoExternalDataProcessor = (MDExternalDataProcessor) externalDataProcessorMDO.get();
        return new ExternalDataProcessor(mdoExternalDataProcessor, configurationSource, rootPath);
      }
    }

    return create();
  }
}
