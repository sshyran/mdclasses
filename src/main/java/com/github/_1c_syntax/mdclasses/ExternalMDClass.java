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
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.MDOHasChildren;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Базовый класс "самостоятельных" объектов (контейнеров)
 */
@Data
public abstract class ExternalMDClass implements MDClass {

  /**
   * Имя внешней обработки
   */
  private String name;

  /**
   * Уникальный идентификатор конфигурации
   */
  private String uuid;

  /**
   * Вариант исходников конфигурации
   */
  private ConfigurationSource configurationSource;

  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы
   */
  private Map<URI, ModuleType> modulesByType;
  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы, сгруппированные по представлению mdoRef
   */
  private Map<String, Map<ModuleType, URI>> modulesByMDORef;
  /**
   * Объекты конфигурации в связке со ссылкой на файлы
   */
  private Map<URI, AbstractMDObjectBSL> modulesByObject;
  /**
   * Модули конфигурации
   */
  private List<MDOModule> modules;
  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  private Set<AbstractMDObjectBase> children;
  /**
   * Дочерние объекты конфигурации с MDO ссылками на них
   */
  private Map<MDOReference, AbstractMDObjectBase> childrenByMdoRef;
  /**
   * Корневой файл
   */
  private Path rootPath;

  protected ExternalMDClass() {
    configurationSource = ConfigurationSource.EMPTY;
    children = Collections.emptySet();
    childrenByMdoRef = Collections.emptyMap();
    modulesByType = Collections.emptyMap();
    modulesByObject = Collections.emptyMap();
    modules = Collections.emptyList();
    modulesByMDORef = Collections.emptyMap();

    rootPath = null;
    name = "";
    uuid = "";
  }

  protected ExternalMDClass(AbstractMDObjectComplex mdo, ConfigurationSource source, Path path) {
    configurationSource = source;
    children = getAllChildren(mdo);
    childrenByMdoRef = new HashMap<>();
    children.forEach((AbstractMDObjectBase child) -> this.childrenByMdoRef.put(child.getMdoReference(), child));

    rootPath = path;

    name = mdo.getName();
    uuid = mdo.getUuid();

    Map<URI, ModuleType> modulesType = new HashMap<>();
    Map<URI, AbstractMDObjectBSL> modulesObject = new HashMap<>();
    Map<String, Map<ModuleType, URI>> modulesMDORef = new CaseInsensitiveMap<>();
    List<MDOModule> modulesList = new ArrayList<>();

    children.forEach((AbstractMDObjectBase child) -> {
      if (child instanceof AbstractMDObjectBSL) {
        computeModules(modulesType,
          modulesObject,
          modulesList,
          modulesMDORef,
          (AbstractMDObjectBSL) child);
      }
    });

    modulesByType = modulesType;
    modulesByObject = modulesObject;
    modules = modulesList;
    modulesByMDORef = modulesMDORef;
  }

  @Override
  public Optional<Path> getRootPath() {
    return Optional.ofNullable(rootPath);
  }

  @Override
  public ModuleType getModuleType(URI uri) {
    return modulesByType.getOrDefault(uri, ModuleType.UNKNOWN);
  }

  @Override
  public Map<ModuleType, URI> getModulesByMDORef(String mdoRef) {
    return modulesByMDORef.getOrDefault(mdoRef, Collections.emptyMap());
  }

  @Override
  public Map<ModuleType, URI> getModulesByMDORef(MDOReference mdoRef) {
    return getModulesByMDORef(mdoRef.getMdoRef());
  }

  // todo надо рефакторить!!!!
  private static void computeModules(Map<URI, ModuleType> modulesType,
                                     Map<URI, AbstractMDObjectBSL> modulesObject,
                                     List<MDOModule> modulesList,
                                     Map<String, Map<ModuleType, URI>> modulesMDORef, AbstractMDObjectBSL mdo) {
    Map<ModuleType, URI> modulesTypesAndURIs = new EnumMap<>(ModuleType.class);
    mdo.getModules().forEach((MDOModule module) -> {
      var uri = module.getUri();
      modulesType.put(uri, module.getModuleType());
      modulesTypesAndURIs.put(module.getModuleType(), uri);
      modulesObject.put(uri, mdo);
      modulesList.add(module);
    });
    modulesMDORef.put(mdo.getMdoReference().getMdoRef(), modulesTypesAndURIs);
  }

  private static Set<AbstractMDObjectBase> getAllChildren(AbstractMDObjectComplex mdo) {
    Set<AbstractMDObjectBase> allChildren = new HashSet<>(mdo.getChildren());

    allChildren.addAll(allChildren.stream()
      .filter(MDOHasChildren.class::isInstance)
      .map(MDOHasChildren.class::cast)
      .map(MDOHasChildren::getChildren)
      .flatMap(Collection::stream)
      .collect(Collectors.toSet()));

    allChildren.add(mdo);
    return allChildren;
  }

}
