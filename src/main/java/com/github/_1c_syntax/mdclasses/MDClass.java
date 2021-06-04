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
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс для "самостоятельных" объектов, контейнеров метаданных.
 */
public interface MDClass {

  /**
   * Имя объекта
   */
  String getName();

  /**
   * Уникальный идентификатор объекта
   */
  String getUuid();

  /**
   * Вариант исходников
   */
  ConfigurationSource getConfigurationSource();

  /**
   * Модули в связке со ссылкой на файлы
   */
  Map<URI, ModuleType> getModulesByType();

  /**
   * Модули в связке со ссылкой на файлы, сгруппированные по представлению mdoRef
   */
  Map<String, Map<ModuleType, URI>> getModulesByMDORef();

  /**
   * Объекты в связке со ссылкой на файлы
   */
  Map<URI, AbstractMDObjectBSL> getModulesByObject();

  /**
   * Модули
   */
  List<MDOModule> getModules();

  /**
   * Дочерние объекты (все, включая дочерние дочерних)
   */
  Set<AbstractMDObjectBase> getChildren();

  /**
   * Дочерние объекты конфигурации с MDO ссылками на них
   */
  Map<MDOReference, AbstractMDObjectBase> getChildrenByMdoRef();

  /**
   * Корневой каталог объекта
   *
   * @return Возвращает пустой Optional если путь не указан
   */
  Optional<Path> getRootPath();

  /**
   * Возвращает тип модуля по ссылке на его файл
   */
  ModuleType getModuleType(URI uri);

  /**
   * Модули объекта в связке со ссылкой на файлы по ссылке mdoRef
   *
   * @param mdoRef Строковая ссылка на объект
   * @return Соответствие ссылки на файл и его тип
   */
  Map<ModuleType, URI> getModulesByMDORef(String mdoRef);

  /**
   * Модули объекта в связке со ссылкой на файлы по ссылке mdoRef
   *
   * @param mdoRef Ссылка на объект
   * @return Соответствие ссылки на файл и его тип
   */
  Map<ModuleType, URI> getModulesByMDORef(MDOReference mdoRef);
}
