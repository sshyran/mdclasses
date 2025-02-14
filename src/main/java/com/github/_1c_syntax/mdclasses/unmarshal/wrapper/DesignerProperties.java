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
package com.github._1c_syntax.mdclasses.unmarshal.wrapper;

import com.github._1c_syntax.bsl.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Враппер над свойствами объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerProperties {

  @XStreamAlias("Name")
  private String name;
  @XStreamAlias("Synonym")
  private List<DesignerContentItem> synonyms = Collections.emptyList();
  @XStreamAlias("Comment")
  private String comment = "";
  @XStreamAlias("ObjectBelonging")
  protected ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  @XStreamAlias("Predefined")
  private boolean predefined;

  @XStreamAlias("ExternalIntegrationServiceAddress")
  private String externalIntegrationServiceAddress = "";
  @XStreamAlias("MessageDirection")
  private MessageDirection messageDirection = MessageDirection.SEND;
  @XStreamAlias("ReceiveMessageProcessing")
  private String receiveMessageProcessing = "";
  @XStreamAlias("ExternalIntegrationServiceChannelName")
  private String externalIntegrationServiceChannelName = "";

  @XStreamAlias("Server")
  private boolean server;
  @XStreamAlias("Global")
  private boolean global;
  @XStreamAlias("ClientManagedApplication")
  private boolean clientManagedApplication;
  @XStreamAlias("ExternalConnection")
  private boolean externalConnection;
  @XStreamAlias("ClientOrdinaryApplication")
  private boolean clientOrdinaryApplication;
  @XStreamAlias("ServerCall")
  private boolean serverCall;
  @XStreamAlias("Privileged")
  private boolean privileged;
  @XStreamAlias("ReturnValuesReuse")
  private ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;

  @XStreamAlias("Handler")
  private String handler = "";

  @XStreamAlias("Content")
  private DesignerXRItems content = new DesignerXRItems();

  @XStreamAlias("RegisterRecords")
  private DesignerXRItems registerRecords = new DesignerXRItems();

  @XStreamAlias("ScriptVariant")
  private ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  @XStreamAlias("CompatibilityMode")
  private CompatibilityMode compatibilityMode = new CompatibilityMode();
  @XStreamAlias("ConfigurationExtensionCompatibilityMode")
  private CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();
  @XStreamAlias("ModalityUseMode")
  private UseMode modalityUseMode = UseMode.USE;
  @XStreamAlias("SynchronousExtensionAndAddInCallUseMode")
  private UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE;
  @XStreamAlias("SynchronousPlatformExtensionAndAddInCallUseMode")
  private UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  @XStreamAlias("DefaultRunMode")
  private String defaultRunMode = "";
  @XStreamAlias("DefaultLanguage")
  private String defaultLanguage = "";
  @XStreamAlias("DataLockControlMode")
  private DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;
  @XStreamAlias("ObjectAutonumerationMode")
  private String objectAutonumerationMode = "";
  @XStreamAlias("ProcedureName")
  private String wsOperationProcedureName = "";
  @XStreamAlias("LanguageCode")
  private String languageCode = "";
  @XStreamAlias("ConfigurationExtensionPurpose")
  private ConfigurationExtensionPurpose configurationExtensionPurpose = ConfigurationExtensionPurpose.PATCH;
  @XStreamAlias("NamePrefix")
  private String namePrefix = "";

  @XStreamAlias("UseManagedFormInOrdinaryApplication")
  private boolean useManagedFormInOrdinaryApplication;
  @XStreamAlias("UseOrdinaryFormInManagedApplication")
  private boolean useOrdinaryFormInManagedApplication;

  @XStreamAlias("Copyright")
  private List<DesignerContentItem> copyrights = Collections.emptyList();

  @XStreamAlias("BriefInformation")
  private List<DesignerContentItem> briefInformation = Collections.emptyList();
  @XStreamAlias("DetailedInformation")
  private List<DesignerContentItem> detailedInformation = Collections.emptyList();

  @XStreamAlias("IncludeInCommandInterface")
  private boolean includeInCommandInterface;

  @XStreamAlias("MethodName")
  private String methodName = "";

  @XStreamAlias("TemplateType")
  private TemplateType templateType = TemplateType.SPREADSHEET_DOCUMENT;
  @XStreamAlias("FormType")
  private FormType formType = FormType.ORDINARY;

  @XStreamAlias("Namespace")
  private String namespace;

  @XStreamAlias("AutoUse")
  private UseMode autoUse = UseMode.DONT_USE;
  @XStreamAlias("DataSeparation")
  private DataSeparation dataSeparation = DataSeparation.DONT_USE;
  @XStreamAlias("Indexing")
  private IndexingType indexing = IndexingType.DONT_INDEX;

  @XStreamAlias("DistributedInfoBase")
  private boolean distributedInfoBase;

  @XStreamAlias("IncludeConfigurationExtensions")
  private boolean includeConfigurationExtensions;

  @XStreamAlias("PasswordMode")
  private boolean passwordMode;

  @XStreamAlias("UseInTotals")
  private boolean useInTotals = true;
  @XStreamAlias("DenyIncompleteValues")
  private boolean denyIncompleteValues;
  @XStreamAlias("Master")
  private boolean master;

  @XStreamAlias("Description")
  private String description;
  @XStreamAlias("Key")
  private String key;
  @XStreamAlias("Use")
  private boolean use;
  @XStreamAlias("RestartCountOnFailure")
  private int restartCountOnFailure;
  @XStreamAlias("RestartIntervalOnFailure")
  private int restartIntervalOnFailure;
}
